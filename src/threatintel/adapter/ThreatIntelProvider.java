package threatintel.adapter;
import shared.model.ReputationResult;
public interface ThreatIntelProvider { ReputationResult checkReputation(String indicator); }
