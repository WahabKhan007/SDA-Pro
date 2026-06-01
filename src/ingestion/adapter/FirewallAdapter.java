package ingestion.adapter;
import shared.enums.AlertSourceType;
import shared.model.RawAlert;

// PATTERN: Adapter
// RATIONALE: Converts Firewall log data into SDA-Pro RawAlert format.
public class FirewallAdapter implements ExternalAlertAdapter {
    public RawAlert fetchAlert() {
        System.out.println("[Adapter] Fetching alert from Firewall mock source...");
        return new RawAlert("RAW-FW-001", AlertSourceType.FIREWALL, "Blocked inbound connection to database server", "45.66.77.88", "10.0.0.20", "unknown", "2026-05-29T10:02:00Z");
    }
    public String getSourceName() { return "Palo Alto Firewall"; }
}
