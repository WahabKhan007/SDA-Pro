package response.decorator;
import response.action.ResponseAction;
import shared.model.ActionOutcome;

public class AuditLogDecorator extends ResponseActionDecorator {
    public AuditLogDecorator(ResponseAction wrappedAction) { super(wrappedAction); }
    public ActionOutcome execute(String target) {
        System.out.println("[Decorator] AuditLogDecorator: pre-action audit log.");
        ActionOutcome outcome = wrappedAction.execute(target);
        System.out.println("[Decorator] AuditLogDecorator: post-action audit log.");
        return outcome;
    }
}
