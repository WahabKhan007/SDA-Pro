package response.action;
import shared.enums.ResponseActionType;
import shared.model.ActionOutcome;

public interface ResponseAction {
    ActionOutcome execute(String target);
    ActionOutcome rollback(String target);
    ResponseActionType getType();
}
