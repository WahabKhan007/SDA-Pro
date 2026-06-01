package response.action;
import shared.enums.ResponseActionType;
import shared.model.ActionOutcome;

public class DisableUserAction implements ResponseAction {
    public ActionOutcome execute(String target) {
        System.out.println("[Action] Disabling user account: " + target);
        return new ActionOutcome("DisableUserAction", true, "Executed on " + target);
    }
    public ActionOutcome rollback(String target) { return new ActionOutcome("DisableUserAction-Rollback", true, "Rollback completed for " + target); }
    public ResponseActionType getType() { return ResponseActionType.DISABLE_USER; }
}
