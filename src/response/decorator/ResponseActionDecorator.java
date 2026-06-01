package response.decorator;
import response.action.ResponseAction;
import shared.enums.ResponseActionType;
import shared.model.ActionOutcome;

// PATTERN: Decorator
// RATIONALE: Base wrapper for adding behavior around response actions dynamically.
public abstract class ResponseActionDecorator implements ResponseAction {
    protected ResponseAction wrappedAction;
    public ResponseActionDecorator(ResponseAction wrappedAction) { this.wrappedAction = wrappedAction; }
    public ResponseActionType getType() { return wrappedAction.getType(); }
    public ActionOutcome rollback(String target) { return wrappedAction.rollback(target); }
}
