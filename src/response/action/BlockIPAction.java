package response.action;
import shared.enums.ResponseActionType;
import shared.model.ActionOutcome;

public class BlockIPAction implements ResponseAction {
    public ActionOutcome execute(String target) {
        System.out.println("[Action] Blocking malicious IP: " + target);
        return new ActionOutcome("BlockIPAction", true, "Executed on " + target);
    }
    public ActionOutcome rollback(String target) { return new ActionOutcome("BlockIPAction-Rollback", true, "Rollback completed for " + target); }
    public ResponseActionType getType() { return ResponseActionType.BLOCK_IP; }
}
