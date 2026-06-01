package incident.state;
import incident.domain.Incident;

// PATTERN: State
// RATIONALE: Encapsulates state-specific legal transitions for the incident lifecycle.
public class EradicationState implements IncidentState {
    public void beginTriage(Incident incident) { System.out.println("[State] Action beginTriage is not allowed in EradicationState."); }
    public void initiateContainment(Incident incident) { System.out.println("[State] Action initiateContainment is not allowed in EradicationState."); }
    public void beginEradication(Incident incident) { System.out.println("[State] Action beginEradication is not allowed in EradicationState."); }
    public void beginRecovery(Incident incident) { System.out.println("[State] EradicationState -> RecoveryState"); incident.setState(new RecoveryState()); }
    public void beginPostIncidentReview(Incident incident) { System.out.println("[State] Action beginPostIncidentReview is not allowed in EradicationState."); }
    public void close(Incident incident) { System.out.println("[State] Action close is not allowed in EradicationState."); }
    public String getName() { return "Eradication"; }
}
