package response.decorator;
import response.action.ResponseAction;
import shared.model.ActionOutcome;

public class ApprovalGateDecorator extends ResponseActionDecorator {
    public ApprovalGateDecorator(ResponseAction wrappedAction) { super(wrappedAction); }
    public ActionOutcome execute(String target) {
        System.out.println("[Decorator] ApprovalGateDecorator: approval granted.");
        return wrappedAction.execute(target);
    }
}
