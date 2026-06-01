package incident.state;
import incident.domain.Incident;

// PATTERN: State
// RATIONALE: Encapsulates state-specific legal transitions for the incident lifecycle.
public class RecoveryState implements IncidentState {
    public void beginTriage(Incident incident) { System.out.println("[State] Action beginTriage is not allowed in RecoveryState."); }
    public void initiateContainment(Incident incident) { System.out.println("[State] Action initiateContainment is not allowed in RecoveryState."); }
    public void beginEradication(Incident incident) { System.out.println("[State] Action beginEradication is not allowed in RecoveryState."); }
    public void beginRecovery(Incident incident) { System.out.println("[State] Action beginRecovery is not allowed in RecoveryState."); }
    public void beginPostIncidentReview(Incident incident) { System.out.println("[State] RecoveryState -> PostIncidentReviewState"); incident.setState(new PostIncidentReviewState()); }
    public void close(Incident incident) { System.out.println("[State] Action close is not allowed in RecoveryState."); }
    public String getName() { return "Recovery"; }
}
