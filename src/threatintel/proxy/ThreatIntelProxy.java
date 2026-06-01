package threatintel.proxy;
import shared.model.ReputationResult;
public interface ThreatIntelProxy { ReputationResult checkReputation(String indicator); }
