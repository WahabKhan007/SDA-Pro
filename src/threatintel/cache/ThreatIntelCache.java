package threatintel.cache;
import java.util.*;
import shared.model.ReputationResult;

// PATTERN: Singleton
// RATIONALE: Threat intelligence reputation cache is shared across the platform.
public class ThreatIntelCache {
    private static ThreatIntelCache instance;
    private Map<String, ReputationResult> cache = new HashMap<>();
    private ThreatIntelCache() {}
    public static synchronized ThreatIntelCache getInstance() {
        if (instance == null) instance = new ThreatIntelCache();
        return instance;
    }
    public boolean contains(String indicator) { return cache.containsKey(indicator); }
    public ReputationResult get(String indicator) { return cache.get(indicator); }
    public void put(String indicator, ReputationResult result) { cache.put(indicator, result); }
}
