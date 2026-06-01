package shared.model;
import shared.enums.AlertSourceType;
import shared.enums.Severity;

public class CanonicalAlert {
    private String alertId;
    private AlertSourceType sourceType;
    private String sourceIp;
    private String destinationIp;
    private String username;
    private Severity severity;
    private String description;
    private String timestamp;

    public CanonicalAlert(String alertId, AlertSourceType sourceType, String sourceIp, String destinationIp, String username, Severity severity, String description, String timestamp) {
        this.alertId = alertId;
        this.sourceType = sourceType;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.username = username;
        this.severity = severity;
        this.description = description;
        this.timestamp = timestamp;
    }
    public String getAlertId() { return alertId; }
    public AlertSourceType getSourceType() { return sourceType; }
    public String getSourceIp() { return sourceIp; }
    public String getDestinationIp() { return destinationIp; }
    public String getUsername() { return username; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTimestamp() { return timestamp; }
    @Override public String toString() { return "CanonicalAlert{" + alertId + ", " + sourceType + ", severity=" + severity + ", src=" + sourceIp + "}"; }
}
