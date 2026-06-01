package shared.model;

public class ActionOutcome {
    private String actionName;
    private boolean success;
    private String message;

    public ActionOutcome(String actionName, boolean success, String message) {
        this.actionName = actionName;
        this.success = success;
        this.message = message;
    }
    public String getActionName() { return actionName; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    @Override public String toString() { return actionName + " -> " + (success ? "SUCCESS" : "FAILED") + " (" + message + ")"; }
}
