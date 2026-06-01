package incident.state;
import incident.domain.Incident;

// PATTERN: State
// RATIONALE: New incidents only allow transition to triage.
public class NewState implements IncidentState {
    public void beginTriage(Incident incident) { System.out.println("[State] NewState -> UnderTriageState"); incident.setState(new UnderTriageState()); }
    public void initiateContainment(Incident incident) { System.out.println("[State] Cannot contain before triage."); }
    public void beginEradication(Incident incident) { System.out.println("[State] Cannot eradicate from NewState."); }
    public void beginRecovery(Incident incident) { System.out.println("[State] Cannot recover from NewState."); }
    public void beginPostIncidentReview(Incident incident) { System.out.println("[State] Cannot review from NewState."); }
    public void close(Incident incident) { System.out.println("[State] Cannot close directly from NewState."); }
    public String getName() { return "New"; }
}
