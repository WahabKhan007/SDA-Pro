package incident.state;
import incident.domain.Incident;

// PATTERN: State
// RATIONALE: Encapsulates state-specific legal transitions for the incident lifecycle.
public class ContainmentState implements IncidentState {
    public void beginTriage(Incident incident) { System.out.println("[State] Action beginTriage is not allowed in ContainmentState."); }
    public void initiateContainment(Incident incident) { System.out.println("[State] Action initiateContainment is not allowed in ContainmentState."); }
    public void beginEradication(Incident incident) { System.out.println("[State] ContainmentState -> EradicationState"); incident.setState(new EradicationState()); }
    public void beginRecovery(Incident incident) { System.out.println("[State] Action beginRecovery is not allowed in ContainmentState."); }
    public void beginPostIncidentReview(Incident incident) { System.out.println("[State] Action beginPostIncidentReview is not allowed in ContainmentState."); }
    public void close(Incident incident) { System.out.println("[State] Action close is not allowed in ContainmentState."); }
    public String getName() { return "Containment"; }
}
