package ingestion.normalizer;
import shared.enums.*;
import shared.model.*;

public class FirewallNormalizer implements AlertNormalizer {
    public CanonicalAlert normalize(RawAlert rawAlert) {
        System.out.println("[Factory Method] FirewallNormalizer converted raw alert to CanonicalAlert.");
        return new CanonicalAlert("ALERT-FW-001", rawAlert.getSourceType(), rawAlert.getSourceIp(), rawAlert.getDestinationIp(), rawAlert.getUsername(), Severity.MEDIUM, rawAlert.getRawMessage(), rawAlert.getTimestamp());
    }
    public boolean supports(AlertSourceType type) { return type == AlertSourceType.FIREWALL; }
}
