package response.decorator;
import response.action.ResponseAction;
import shared.model.ActionOutcome;

public class MetricsDecorator extends ResponseActionDecorator {
    public MetricsDecorator(ResponseAction wrappedAction) { super(wrappedAction); }
    public ActionOutcome execute(String target) {
        long start = System.currentTimeMillis();
        ActionOutcome outcome = wrappedAction.execute(target);
        System.out.println("[Decorator] MetricsDecorator: action took " + (System.currentTimeMillis() - start) + " ms.");
        return outcome;
    }
}
