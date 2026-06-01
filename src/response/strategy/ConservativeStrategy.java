package response.strategy;
import incident.domain.Incident;
import java.util.*;
import shared.enums.ResponseActionType;

// PATTERN: Strategy
// RATIONALE: This response algorithm can be selected at runtime based on incident context.
public class ConservativeStrategy implements ResponseStrategy {
    public List<ResponseActionType> determineActions(Incident incident) {
        System.out.println("[Strategy] Conservative Response selected for " + incident.getSeverity() + " incident.");
        return Arrays.asList(ResponseActionType.ESCALATE);
    }
    public String getName() { return "Conservative Response"; }
}
