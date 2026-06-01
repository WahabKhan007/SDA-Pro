package incident.state;
import incident.domain.Incident;

// PATTERN: State
// RATIONALE: Encapsulates state-specific legal transitions for the incident lifecycle.
public class PostIncidentReviewState implements IncidentState {
    public void beginTriage(Incident incident) { System.out.println("[State] Action beginTriage is not allowed in PostIncidentReviewState."); }
    public void initiateContainment(Incident incident) { System.out.println("[State] Action initiateContainment is not allowed in PostIncidentReviewState."); }
    public void beginEradication(Incident incident) { System.out.println("[State] Action beginEradication is not allowed in PostIncidentReviewState."); }
    public void beginRecovery(Incident incident) { System.out.println("[State] Action beginRecovery is not allowed in PostIncidentReviewState."); }
    public void beginPostIncidentReview(Incident incident) { System.out.println("[State] Action beginPostIncidentReview is not allowed in PostIncidentReviewState."); }
    public void close(Incident incident) { System.out.println("[State] PostIncidentReviewState -> ClosedState"); incident.setState(new ClosedState()); }
    public String getName() { return "PostIncidentReview"; }
}
