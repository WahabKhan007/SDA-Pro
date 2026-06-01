package response.factory;
import response.action.*;
import shared.enums.ResponseActionType;

// PATTERN: Factory Method
// RATIONALE: Creates response action objects based on action type.
public class ResponseActionFactory {
    public ResponseAction createAction(ResponseActionType type) {
        switch (type) {
            case BLOCK_IP: return new BlockIPAction();
            case ISOLATE_ENDPOINT: return new IsolateEndpointAction();
            case DISABLE_USER: return new DisableUserAction();
            case ESCALATE: return new EscalateAction();
            default: throw new IllegalArgumentException("Unsupported action: " + type);
        }
    }
}
