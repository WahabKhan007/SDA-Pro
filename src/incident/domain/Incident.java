package incident.domain;
import alert.composite.AlertComponent;
import incident.state.*;
import shared.enums.Severity;
import shared.model.ActionOutcome;
import java.util.*;

public class Incident {
    private String id;
    private String title;
    private Severity severity;
    private AlertComponent affectedAlert;
    private IncidentState currentState;
    private List<ActionOutcome> actionOutcomes = new ArrayList<>();

    public Incident(String id, String title, Severity severity, AlertComponent affectedAlert) {
        this.id = id;
        this.title = title;
        this.severity = severity;
        this.affectedAlert = affectedAlert;
        this.currentState = new NewState();
        System.out.println("[State] Incident created in NewState.");
    }
    public String getId() { return id; }
    public String getTitle() { return title; }
    public Severity getSeverity() { return severity; }
    public AlertComponent getAffectedAlert() { return affectedAlert; }
    public String getCurrentStateName() { return currentState.getName(); }
    public void setState(IncidentState state) { this.currentState = state; }
    public void beginTriage() { currentState.beginTriage(this); }
    public void initiateContainment() { currentState.initiateContainment(this); }
    public void beginEradication() { currentState.beginEradication(this); }
    public void beginRecovery() { currentState.beginRecovery(this); }
    public void beginPostIncidentReview() { currentState.beginPostIncidentReview(this); }
    public void close() { currentState.close(this); }
    public void recordActionOutcome(ActionOutcome outcome) { actionOutcomes.add(outcome); }
    public List<ActionOutcome> getActionOutcomes() { return actionOutcomes; }
    @Override public String toString() { return "Incident{" + id + ", severity=" + severity + ", state=" + getCurrentStateName() + "}"; }
}
