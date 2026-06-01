package response.action;
import shared.enums.ResponseActionType;
import shared.model.ActionOutcome;

public class EscalateAction implements ResponseAction {
    public ActionOutcome execute(String target) {
        System.out.println("[Action] Escalating incident to Tier-3 analyst: " + target);
        return new ActionOutcome("EscalateAction", true, "Executed on " + target);
    }
    public ActionOutcome rollback(String target) { return new ActionOutcome("EscalateAction-Rollback", true, "Rollback completed for " + target); }
    public ResponseActionType getType() { return ResponseActionType.ESCALATE; }
}
