package ingestion.adapter;
import shared.enums.AlertSourceType;
import shared.model.RawAlert;

// PATTERN: Adapter
// RATIONALE: Converts Splunk-like external data into SDA-Pro RawAlert format.
public class SplunkAdapter implements ExternalAlertAdapter {
    public RawAlert fetchAlert() {
        System.out.println("[Adapter] Fetching alert from Splunk SIEM mock source...");
        return new RawAlert("RAW-SPL-001", AlertSourceType.SPLUNK, "Multiple failed logins from suspicious IP", "185.21.10.5", "10.0.0.15", "admin", "2026-05-29T10:00:00Z");
    }
    public String getSourceName() { return "Splunk SIEM"; }
}
