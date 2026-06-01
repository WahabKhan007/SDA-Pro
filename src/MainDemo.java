import alert.composite.*;
import dashboard.controllers.*;
import dashboard.models.DashboardModel;
import dashboard.views.DashboardView;
import enrichment.EnrichmentCorrelationService;
import events.*;
import incident.IncidentManagementService;
import incident.domain.Incident;
import ingestion.AlertIngestionService;
import ingestion.adapter.*;
import response.ResponseOrchestrationService;
import shared.enums.*;
import shared.model.CanonicalAlert;

public class MainDemo {
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("SDA-Pro Security Incident Response & Threat Mitigation Platform ");
        System.out.println("==================================================================");

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
        dashboard.addDashboardMessage("System started. Waiting for security alerts...");

        System.out.println("\n--- STEP 1: Alert Ingestion from 2 Sources ---");
        AlertIngestionService ingestionService = new AlertIngestionService();
        CanonicalAlert splunkAlert = ingestionService.ingest(new SplunkAdapter());
        CanonicalAlert firewallAlert = ingestionService.ingest(new FirewallAdapter());

        System.out.println("\n--- STEP 2: Composite Grouping ---");
        AlertCampaign campaign = new AlertCampaign("CMP-001", "Possible Credential Attack Campaign");
        campaign.add(new SingleAlert(splunkAlert));
        campaign.add(new SingleAlert(firewallAlert));
        campaign.display("  ");

        System.out.println("\n--- STEP 3: Enrichment Pipeline ---");
        EnrichmentCorrelationService enrichmentService = new EnrichmentCorrelationService();
        AlertComponent enrichedCampaign = enrichmentService.enrich(campaign);

        System.out.println("\n--- STEP 4: Incident Creation and State Lifecycle ---");
        IncidentManagementService incidentService = new IncidentManagementService();
        Incident incident = incidentService.createIncident(enrichedCampaign);
        incident.beginTriage();
        incident.initiateContainment();

        System.out.println("\n--- STEP 5: Response Orchestration ---");
        ResponseOrchestrationService responseService = new ResponseOrchestrationService();
        responseService.respond(incident);
        incident.beginRecovery();
        incident.beginPostIncidentReview();
        incident.close();

        System.out.println("\n--- STEP 6: MVC Dashboard Output ---");
        dashboard.addDashboardMessage("Incident " + incident.getId() + " completed with state: " + incident.getCurrentStateName());
        dashboard.showDashboard();

        System.out.println("\n--- STEP 7: Proxy Cache Verification ---");
        enrichmentService.enrich(campaign);

        System.out.println("\nDemo completed successfully.");
    }
}
