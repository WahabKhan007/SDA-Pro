package response.action;
import shared.enums.ResponseActionType;
import shared.model.ActionOutcome;

public class IsolateEndpointAction implements ResponseAction {
    public ActionOutcome execute(String target) {
        System.out.println("[Action] Isolating endpoint: " + target);
        return new ActionOutcome("IsolateEndpointAction", true, "Executed on " + target);
    }
    public ActionOutcome rollback(String target) { return new ActionOutcome("IsolateEndpointAction-Rollback", true, "Rollback completed for " + target); }
    public ResponseActionType getType() { return ResponseActionType.ISOLATE_ENDPOINT; }
}
