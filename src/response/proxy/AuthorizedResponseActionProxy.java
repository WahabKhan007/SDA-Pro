package response.proxy;
import response.action.ResponseAction;
import shared.model.ActionOutcome;

// PATTERN: Proxy
// RATIONALE: Controls access to response actions by checking authorization before execution.
public class AuthorizedResponseActionProxy implements ResponseActionProxy {
    public ActionOutcome execute(ResponseAction action, String target, String role) {
        System.out.println("[Proxy] Checking authorization for role: " + role);
        if (!"SOC_MANAGER".equals(role) && !"TIER3_ANALYST".equals(role)) {
            return new ActionOutcome("Unauthorized", false, "User role is not allowed to execute response action.");
        }
        return action.execute(target);
    }
}
