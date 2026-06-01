package response.strategy;
import incident.domain.Incident;
import java.util.*;
import shared.enums.ResponseActionType;

// PATTERN: Strategy
// RATIONALE: This response algorithm can be selected at runtime based on incident context.
public class AggressiveContainmentStrategy implements ResponseStrategy {
    public List<ResponseActionType> determineActions(Incident incident) {
        System.out.println("[Strategy] Aggressive Containment selected for " + incident.getSeverity() + " incident.");
        return Arrays.asList(ResponseActionType.BLOCK_IP, ResponseActionType.ISOLATE_ENDPOINT);
    }
    public String getName() { return "Aggressive Containment"; }
}
