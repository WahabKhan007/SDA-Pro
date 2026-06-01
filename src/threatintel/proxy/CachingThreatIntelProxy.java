package threatintel.proxy;
import shared.model.ReputationResult;
import threatintel.adapter.ThreatIntelProvider;
import threatintel.cache.ThreatIntelCache;

// PATTERN: Proxy
// RATIONALE: Controls access to threat intel provider by adding caching before external lookup.
public class CachingThreatIntelProxy implements ThreatIntelProxy {
    private ThreatIntelProvider realProvider;
    private ThreatIntelCache cache = ThreatIntelCache.getInstance();
    public CachingThreatIntelProxy(ThreatIntelProvider realProvider) { this.realProvider = realProvider; }
    public ReputationResult checkReputation(String indicator) {
        if (cache.contains(indicator)) {
            System.out.println("[Proxy] Cache hit for indicator: " + indicator);
            return cache.get(indicator);
        }
        System.out.println("[Proxy] Cache miss. Calling real threat intel provider...");
        ReputationResult result = realProvider.checkReputation(indicator);
        cache.put(indicator, result);
        return result;
    }
}
