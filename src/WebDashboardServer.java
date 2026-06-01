import alert.composite.*;
import dashboard.controllers.DashboardController;
import dashboard.models.DashboardModel;
import dashboard.views.DashboardView;
import enrichment.EnrichmentCorrelationService;
import events.*;
import incident.IncidentManagementService;
import incident.domain.Incident;
import ingestion.AlertIngestionService;
import ingestion.adapter.FirewallAdapter;
import ingestion.adapter.SplunkAdapter;
import response.ResponseOrchestrationService;
import shared.enums.EventType;
import shared.model.ActionOutcome;
import shared.model.CanonicalAlert;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WebDashboardServer {
    private static final int DEFAULT_PORT = 9090;

    public static void main(String[] args) throws IOException {
        int port = resolvePort(args);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", WebDashboardServer::handleHome);
        server.createContext("/api/health", WebDashboardServer::handleHealth);
        server.createContext("/api/run-demo", WebDashboardServer::handleRunDemo);
        server.setExecutor(null);
        server.start();

        System.out.println("=================================================");
        System.out.println("SDA-Pro Web started successfully");
        System.out.println("Open in browser: http://localhost:" + port);
        System.out.println("=================================================");
    }


    private static int resolvePort(String[] args) {
        if (args != null && args.length > 0) {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
                return DEFAULT_PORT;
            }
        }
        return DEFAULT_PORT;
    }

    private static void handleHome(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "text/plain", "Method not allowed");
            return;
        }
        send(exchange, 200, "text/html", htmlPage());
    }

    private static void handleHealth(HttpExchange exchange) throws IOException {
        send(exchange, 200, "application/json", "{\"status\":\"UP\",\"service\":\"SDA-Pro Web GUI\"}");
    }

    private static void handleRunDemo(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "application/json", "{\"error\":\"Method not allowed\"}");
            return;
        }

        DemoResult result = runBackendDemo();
        send(exchange, 200, "application/json", result.toJson());
    }

    private static DemoResult runBackendDemo() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        DemoResult result = new DemoResult();

        try {
            System.setOut(capture);

            EventBusPublisher bus = EventBusPublisher.getInstance();
            bus.attach(EventType.ALERT_INGESTED, new DashboardUpdater());
            bus.attach(EventType.ALERT_ENRICHED, new DashboardUpdater());
            bus.attach(EventType.INCIDENT_CREATED, new DashboardUpdater());
            bus.attach(EventType.INCIDENT_CREATED, new AuditLogger());
            bus.attach(EventType.RESPONSE_ACTION_EXECUTED, new DashboardUpdater());
            bus.attach(EventType.RESPONSE_ACTION_EXECUTED, new AuditLogger());
            bus.attach(EventType.RESPONSE_ACTION_EXECUTED, new NotificationDispatcher());
            bus.attach(EventType.RESPONSE_ACTION_EXECUTED, new MetricsCollector());

            DashboardController dashboard = new DashboardController(new DashboardModel(), new DashboardView());
            dashboard.addDashboardMessage("System started from Web GUI. Waiting for security alerts...");

            AlertIngestionService ingestionService = new AlertIngestionService();
            CanonicalAlert splunkAlert = ingestionService.ingest(new SplunkAdapter());
            CanonicalAlert firewallAlert = ingestionService.ingest(new FirewallAdapter());
            result.alerts.add(splunkAlert);
            result.alerts.add(firewallAlert);

            AlertCampaign campaign = new AlertCampaign("CMP-001", "Possible Credential Attack Campaign");
            campaign.add(new SingleAlert(splunkAlert));
            campaign.add(new SingleAlert(firewallAlert));

            EnrichmentCorrelationService enrichmentService = new EnrichmentCorrelationService();
            AlertComponent enrichedCampaign = enrichmentService.enrich(campaign);

            IncidentManagementService incidentService = new IncidentManagementService();
            Incident incident = incidentService.createIncident(enrichedCampaign);
            incident.beginTriage();
            incident.initiateContainment();

            ResponseOrchestrationService responseService = new ResponseOrchestrationService();
            responseService.respond(incident);
            incident.beginRecovery();
            incident.beginPostIncidentReview();
            incident.close();

            dashboard.addDashboardMessage("Incident " + incident.getId() + " completed with state: " + incident.getCurrentStateName());
            dashboard.showDashboard();

            result.campaignId = campaign.getId();
            result.campaignName = "Possible Credential Attack Campaign";
            result.campaignSeverity = campaign.getSeverity().toString();
            result.incidentId = incident.getId();
            result.incidentTitle = incident.getTitle();
            result.incidentSeverity = incident.getSeverity().toString();
            result.incidentState = incident.getCurrentStateName();
            result.actions.addAll(incident.getActionOutcomes());
            result.generatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception ex) {
            result.error = ex.getClass().getSimpleName() + ": " + ex.getMessage();
        } finally {
            capture.flush();
            System.setOut(originalOut);
            result.logs = buffer.toString(StandardCharsets.UTF_8);
        }
        return result;
    }

    private static void send(HttpExchange exchange, int code, String contentType, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType + "; charset=utf-8");
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        exchange.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static String htmlPage() {
        return """
<!DOCTYPE html>
<html lang=\"en\">
<head>
    <meta charset=\"UTF-8\" />
    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />
    <title>SDA-Pro Security Incident Response & Threat Mitigation Platform</title>
    <style>
        :root {
            --bg: #07111f;
            --panel: #0e1f33;
            --panel2: #112a43;
            --text: #edf6ff;
            --muted: #9fb4c9;
            --line: rgba(255,255,255,0.12);
            --accent: #38bdf8;
            --good: #22c55e;
            --warn: #f59e0b;
            --danger: #ef4444;
        }
        * { box-sizing: border-box; }
        body { margin: 0; font-family: Arial, Helvetica, sans-serif; color: var(--text); background: radial-gradient(circle at top left, #12395f 0, var(--bg) 42%); }
        header { padding: 34px 6vw 22px; border-bottom: 1px solid var(--line); }
        .badge { display: inline-block; padding: 7px 12px; border: 1px solid rgba(56,189,248,0.45); border-radius: 999px; color: #b8ecff; background: rgba(56,189,248,0.08); font-size: 13px; }
        h1 { margin: 16px 0 8px; font-size: clamp(30px, 4vw, 52px); line-height: 1.05; letter-spacing: -1px; }
        p { color: var(--muted); line-height: 1.6; }
        main { padding: 28px 6vw 50px; }
        .toolbar { display: flex; align-items: center; justify-content: space-between; gap: 16px; flex-wrap: wrap; margin-bottom: 22px; }
        button { border: 0; color: #05111e; background: linear-gradient(135deg, #7dd3fc, #22d3ee); border-radius: 14px; padding: 13px 18px; font-weight: 700; cursor: pointer; box-shadow: 0 14px 30px rgba(34,211,238,0.2); }
        button:disabled { opacity: 0.6; cursor: not-allowed; }
        .grid { display: grid; grid-template-columns: repeat(4, minmax(0, 1fr)); gap: 16px; }
        .card { background: linear-gradient(180deg, rgba(17,42,67,0.95), rgba(14,31,51,0.95)); border: 1px solid var(--line); border-radius: 22px; padding: 18px; box-shadow: 0 20px 55px rgba(0,0,0,0.22); }
        .card h3 { margin: 0 0 8px; color: #dff7ff; font-size: 15px; }
        .metric { font-size: 30px; font-weight: 800; margin-top: 8px; }
        .section { margin-top: 18px; }
        table { width: 100%; border-collapse: collapse; overflow: hidden; border-radius: 16px; }
        th, td { padding: 13px 12px; text-align: left; border-bottom: 1px solid var(--line); font-size: 14px; }
        th { color: #b8ecff; font-weight: 700; background: rgba(56,189,248,0.08); }
        .two { display: grid; grid-template-columns: 1.05fr 0.95fr; gap: 16px; }
        .pill { padding: 6px 10px; border-radius: 999px; font-weight: 700; font-size: 12px; display: inline-block; }
        .high, .critical { background: rgba(239,68,68,0.18); color: #fecaca; }
        .medium { background: rgba(245,158,11,0.18); color: #fde68a; }
        .low { background: rgba(34,197,94,0.18); color: #bbf7d0; }
        .success { color: #bbf7d0; font-weight: 700; }
        pre { white-space: pre-wrap; max-height: 420px; overflow: auto; background: #06101c; color: #d7ecff; padding: 16px; border-radius: 16px; border: 1px solid var(--line); font-size: 13px; line-height: 1.55; }
        .flow { display: grid; grid-template-columns: repeat(6, 1fr); gap: 10px; }
        .step { padding: 13px; background: rgba(255,255,255,0.06); border: 1px solid var(--line); border-radius: 16px; color: #dbeafe; font-size: 13px; }
        .error { color: #fecaca; background: rgba(239,68,68,0.12); border: 1px solid rgba(239,68,68,0.35); padding: 12px; border-radius: 14px; display: none; }
        footer { padding: 24px 6vw; color: var(--muted); border-top: 1px solid var(--line); }
        @media (max-width: 980px) { .grid { grid-template-columns: repeat(2, 1fr); } .two { grid-template-columns: 1fr; } .flow { grid-template-columns: repeat(2, 1fr); } }
        @media (max-width: 560px) { .grid { grid-template-columns: 1fr; } header, main, footer { padding-left: 18px; padding-right: 18px; } }
    </style>
</head>
<body>
    <header>
        <span class=\"badge\">Software Design & Architecture Project</span>
        <h1>SDA-Pro Security Incident Response & Threat Mitigation Platform</h1>
        <p>This project was developed by Abdul Wahab Khan, Rohail Abbas, and Ziafat Hussain. Through teamwork and collaboration, we successfully completed all the required tasks and achieved the objectives of the project.</p>
    </header>

    <main>
        <div class=\"toolbar\">
            <div>
                <h2 style=\"margin:0\">Live Demo Control</h2>
                <p style=\"margin:4px 0 0\">Click the button to execute the backend workflow and update the GUI.</p>
            </div>
            <button id=\"runBtn\">Run Security Incident Demo</button>
        </div>

        <div id=\"errorBox\" class=\"error\"></div>

        <div class=\"grid\">
            <div class=\"card\"><h3>Total Alerts</h3><div class=\"metric\" id=\"alertCount\">0</div><p>Normalized alerts from mock external sources.</p></div>
            <div class=\"card\"><h3>Campaign Severity</h3><div class=\"metric\" id=\"campaignSeverity\">-</div><p>Composite pattern groups alerts into one campaign.</p></div>
            <div class=\"card\"><h3>Incident State</h3><div class=\"metric\" id=\"incidentState\">-</div><p>State pattern controls the incident lifecycle.</p></div>
            <div class=\"card\"><h3>Actions Executed</h3><div class=\"metric\" id=\"actionCount\">0</div><p>Strategy, Factory, Decorator, Proxy, and Facade working together.</p></div>
        </div>

        <div class=\"section card\">
            <h2>Architecture Flow</h2>
            <div class=\"flow\">
                <div class=\"step\">Adapter<br>External Alerts</div>
                <div class=\"step\">Factory Method<br>Normalize</div>
                <div class=\"step\">Composite<br>Campaign</div>
                <div class=\"step\">Chain + Proxy<br>Enrichment</div>
                <div class=\"step\">State<br>Incident Lifecycle</div>
                <div class=\"step\">Facade<br>Response</div>
            </div>
        </div>

        <div class=\"section two\">
            <div class=\"card\">
                <h2>Normalized Alerts</h2>
                <table>
                    <thead><tr><th>ID</th><th>Source</th><th>Source IP</th><th>User</th><th>Severity</th></tr></thead>
                    <tbody id=\"alertsTable\"><tr><td colspan=\"5\">No data yet. Run the demo.</td></tr></tbody>
                </table>
            </div>
            <div class=\"card\">
                <h2>Incident Summary</h2>
                <table>
                    <tbody>
                        <tr><th>Incident ID</th><td id=\"incidentId\">-</td></tr>
                        <tr><th>Title</th><td id=\"incidentTitle\">-</td></tr>
                        <tr><th>Severity</th><td id=\"incidentSeverity\">-</td></tr>
                        <tr><th>Final State</th><td id=\"incidentFinalState\">-</td></tr>
                        <tr><th>Campaign</th><td id=\"campaignName\">-</td></tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class=\"section card\">
            <h2>Response Actions</h2>
            <table>
                <thead><tr><th>Action</th><th>Status</th><th>Message</th></tr></thead>
                <tbody id=\"actionsTable\"><tr><td colspan=\"3\">No response actions yet.</td></tr></tbody>
            </table>
        </div>

        <div class=\"section card\">
            <h2>Backend Console Log</h2>
            <pre id=\"logs\">Console output will appear here.</pre>
        </div>
    </main>

    <footer>SDA-Pro Security Incident Response & Threat Mitigation Platform.</footer>

    <script>
        const runBtn = document.getElementById('runBtn');
        const errorBox = document.getElementById('errorBox');

        function severityBadge(value) {
            const cls = String(value || '').toLowerCase();
            return `<span class=\"pill ${cls}\">${value || '-'}</span>`;
        }

        runBtn.addEventListener('click', async () => {
            runBtn.disabled = true;
            runBtn.textContent = 'Running Backend...';
            errorBox.style.display = 'none';
            try {
                const response = await fetch('/api/run-demo');
                const data = await response.json();
                if (data.error) {
                    errorBox.textContent = data.error;
                    errorBox.style.display = 'block';
                }

                document.getElementById('alertCount').textContent = data.alerts.length;
                document.getElementById('campaignSeverity').innerHTML = severityBadge(data.campaignSeverity);
                document.getElementById('incidentState').textContent = data.incidentState || '-';
                document.getElementById('actionCount').textContent = data.actions.length;
                document.getElementById('incidentId').textContent = data.incidentId || '-';
                document.getElementById('incidentTitle').textContent = data.incidentTitle || '-';
                document.getElementById('incidentSeverity').innerHTML = severityBadge(data.incidentSeverity);
                document.getElementById('incidentFinalState').textContent = data.incidentState || '-';
                document.getElementById('campaignName').textContent = `${data.campaignId || '-'} — ${data.campaignName || '-'}`;
                document.getElementById('logs').textContent = data.logs || 'No logs returned.';

                document.getElementById('alertsTable').innerHTML = data.alerts.map(a => `
                    <tr><td>${a.alertId}</td><td>${a.sourceType}</td><td>${a.sourceIp}</td><td>${a.username}</td><td>${severityBadge(a.severity)}</td></tr>
                `).join('');

                document.getElementById('actionsTable').innerHTML = data.actions.length ? data.actions.map(a => `
                    <tr><td>${a.actionName}</td><td class=\"success\">${a.success ? 'SUCCESS' : 'FAILED'}</td><td>${a.message}</td></tr>
                `).join('') : '<tr><td colspan=\"3\">No actions returned.</td></tr>';
            } catch (err) {
                errorBox.textContent = 'Unable to run demo: ' + err.message;
                errorBox.style.display = 'block';
            } finally {
                runBtn.disabled = false;
                runBtn.textContent = 'Run Security Incident Demo';
            }
        });
    </script>
</body>
</html>
""";
    }

    private static String jsonEscape(String value) {
        if (value == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 32) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        return sb.toString();
    }

    private static class DemoResult {
        List<CanonicalAlert> alerts = new ArrayList<>();
        List<ActionOutcome> actions = new ArrayList<>();
        String campaignId = "";
        String campaignName = "";
        String campaignSeverity = "";
        String incidentId = "";
        String incidentTitle = "";
        String incidentSeverity = "";
        String incidentState = "";
        String generatedAt = "";
        String logs = "";
        String error = "";

        String toJson() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"generatedAt\":\"").append(jsonEscape(generatedAt)).append("\",");
            sb.append("\"campaignId\":\"").append(jsonEscape(campaignId)).append("\",");
            sb.append("\"campaignName\":\"").append(jsonEscape(campaignName)).append("\",");
            sb.append("\"campaignSeverity\":\"").append(jsonEscape(campaignSeverity)).append("\",");
            sb.append("\"incidentId\":\"").append(jsonEscape(incidentId)).append("\",");
            sb.append("\"incidentTitle\":\"").append(jsonEscape(incidentTitle)).append("\",");
            sb.append("\"incidentSeverity\":\"").append(jsonEscape(incidentSeverity)).append("\",");
            sb.append("\"incidentState\":\"").append(jsonEscape(incidentState)).append("\",");
            sb.append("\"error\":\"").append(jsonEscape(error)).append("\",");
            sb.append("\"alerts\":[");
            for (int i = 0; i < alerts.size(); i++) {
                CanonicalAlert a = alerts.get(i);
                if (i > 0) sb.append(",");
                sb.append("{");
                sb.append("\"alertId\":\"").append(jsonEscape(a.getAlertId())).append("\",");
                sb.append("\"sourceType\":\"").append(jsonEscape(String.valueOf(a.getSourceType()))).append("\",");
                sb.append("\"sourceIp\":\"").append(jsonEscape(a.getSourceIp())).append("\",");
                sb.append("\"destinationIp\":\"").append(jsonEscape(a.getDestinationIp())).append("\",");
                sb.append("\"username\":\"").append(jsonEscape(a.getUsername())).append("\",");
                sb.append("\"severity\":\"").append(jsonEscape(String.valueOf(a.getSeverity()))).append("\",");
                sb.append("\"description\":\"").append(jsonEscape(a.getDescription())).append("\",");
                sb.append("\"timestamp\":\"").append(jsonEscape(a.getTimestamp())).append("\"");
                sb.append("}");
            }
            sb.append("],");
            sb.append("\"actions\":[");
            for (int i = 0; i < actions.size(); i++) {
                ActionOutcome action = actions.get(i);
                if (i > 0) sb.append(",");
                sb.append("{");
                sb.append("\"actionName\":\"").append(jsonEscape(action.getActionName())).append("\",");
                sb.append("\"success\":").append(action.isSuccess()).append(",");
                sb.append("\"message\":\"").append(jsonEscape(action.getMessage())).append("\"");
                sb.append("}");
            }
            sb.append("],");
            sb.append("\"logs\":\"").append(jsonEscape(logs)).append("\"");
            sb.append("}");
            return sb.toString();
        }
    }
}
