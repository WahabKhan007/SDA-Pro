package response.decorator;
import response.action.ResponseAction;
import shared.model.ActionOutcome;

public class RollbackDecorator extends ResponseActionDecorator {
    public RollbackDecorator(ResponseAction wrappedAction) { super(wrappedAction); }
    public ActionOutcome execute(String target) {
        System.out.println("[Decorator] RollbackDecorator: rollback snapshot saved.");
        return wrappedAction.execute(target);
    }
}
