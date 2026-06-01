package response.proxy;
import response.action.ResponseAction;
import shared.model.ActionOutcome;
public interface ResponseActionProxy { ActionOutcome execute(ResponseAction action, String target, String role); }
