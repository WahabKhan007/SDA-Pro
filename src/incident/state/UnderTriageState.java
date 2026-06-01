package incident.state;
import incident.domain.Incident;

// PATTERN: State
// RATIONALE: Encapsulates state-specific legal transitions for the incident lifecycle.
public class UnderTriageState implements IncidentState {
    public void beginTriage(Incident incident) { System.out.println("[State] Action beginTriage is not allowed in UnderTriageState."); }
    public void initiateContainment(Incident incident) { System.out.println("[State] UnderTriageState -> ContainmentState"); incident.setState(new ContainmentState()); }
    public void beginEradication(Incident incident) { System.out.println("[State] Action beginEradication is not allowed in UnderTriageState."); }
    public void beginRecovery(Incident incident) { System.out.println("[State] Action beginRecovery is not allowed in UnderTriageState."); }
    public void beginPostIncidentReview(Incident incident) { System.out.println("[State] Action beginPostIncidentReview is not allowed in UnderTriageState."); }
    public void close(Incident incident) { System.out.println("[State] Action close is not allowed in UnderTriageState."); }
    public String getName() { return "UnderTriage"; }
}
