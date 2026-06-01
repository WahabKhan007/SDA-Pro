package shared.model;
import shared.enums.AlertSourceType;

public class RawAlert {
    private String id;
    private AlertSourceType sourceType;
    private String rawMessage;
    private String sourceIp;
    private String destinationIp;
    private String username;
    private String timestamp;

    public RawAlert(String id, AlertSourceType sourceType, String rawMessage, String sourceIp, String destinationIp, String username, String timestamp) {
        this.id = id;
        this.sourceType = sourceType;
        this.rawMessage = rawMessage;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.username = username;
        this.timestamp = timestamp;
    }
    public String getId() { return id; }
    public AlertSourceType getSourceType() { return sourceType; }
    public String getRawMessage() { return rawMessage; }
    public String getSourceIp() { return sourceIp; }
    public String getDestinationIp() { return destinationIp; }
    public String getUsername() { return username; }
    public String getTimestamp() { return timestamp; }
    @Override public String toString() { return "RawAlert{" + sourceType + ", src=" + sourceIp + ", msg='" + rawMessage + "'}"; }
}
