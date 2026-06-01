package ingestion.normalizer;
import shared.enums.*;
import shared.model.*;

public class CrowdStrikeNormalizer implements AlertNormalizer {
    public CanonicalAlert normalize(RawAlert rawAlert) {
        System.out.println("[Factory Method] CrowdStrikeNormalizer converted raw alert to CanonicalAlert.");
        return new CanonicalAlert("ALERT-CS-001", rawAlert.getSourceType(), rawAlert.getSourceIp(), rawAlert.getDestinationIp(), rawAlert.getUsername(), Severity.HIGH, rawAlert.getRawMessage(), rawAlert.getTimestamp());
    }
    public boolean supports(AlertSourceType type) { return type == AlertSourceType.CROWDSTRIKE; }
}
