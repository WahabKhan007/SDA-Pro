package enrichment.pipeline;
import alert.composite.AlertComponent;

// PATTERN: Chain of Responsibility
// RATIONALE: Each handler performs one enrichment step and forwards the alert to the next handler.
public abstract class EnrichmentHandler {
    private EnrichmentHandler nextHandler;
    public EnrichmentHandler setNext(EnrichmentHandler nextHandler) { this.nextHandler = nextHandler; return nextHandler; }
    public final void handle(AlertComponent alert) {
        doHandle(alert);
        if (nextHandler != null) nextHandler.handle(alert);
    }
    protected abstract void doHandle(AlertComponent alert);
}
