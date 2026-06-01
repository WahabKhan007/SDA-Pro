package response.strategy;
import incident.domain.Incident;
import shared.enums.Severity;

public class ResponseStrategySelector {
    public ResponseStrategy selectStrategy(Incident incident) {
        Severity severity = incident.getSeverity();
        if (severity == Severity.CRITICAL) return new AggressiveContainmentStrategy();
        if (severity == Severity.HIGH) return new BalancedStrategy();
        if (severity == Severity.MEDIUM) return new ConservativeStrategy();
        return new WatchAndWaitStrategy();
    }
}
