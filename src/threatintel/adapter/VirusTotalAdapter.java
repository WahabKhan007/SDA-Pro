package threatintel.adapter;
import shared.model.ReputationResult;

// PATTERN: Adapter
// RATIONALE: Converts VirusTotal-like response into SDA-Pro ReputationResult.
public class VirusTotalAdapter implements ThreatIntelProvider {
    public ReputationResult checkReputation(String indicator) {
        System.out.println("[Adapter] VirusTotalAdapter queried for: " + indicator);
        if (indicator.startsWith("185") || indicator.startsWith("45")) return new ReputationResult(indicator, 90, "MALICIOUS");
        return new ReputationResult(indicator, 10, "CLEAN");
    }
}
