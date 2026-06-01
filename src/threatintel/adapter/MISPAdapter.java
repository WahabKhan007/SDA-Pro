package threatintel.adapter;
import shared.model.ReputationResult;

// PATTERN: Adapter
// RATIONALE: Converts MISP-like response into SDA-Pro ReputationResult.
public class MISPAdapter implements ThreatIntelProvider {
    public ReputationResult checkReputation(String indicator) {
        System.out.println("[Adapter] MISPAdapter queried for: " + indicator);
        if (indicator.contains("77")) return new ReputationResult(indicator, 80, "MALICIOUS");
        return new ReputationResult(indicator, 30, "UNKNOWN");
    }
}
