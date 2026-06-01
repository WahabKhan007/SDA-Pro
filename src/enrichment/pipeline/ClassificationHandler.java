package enrichment.pipeline;
import alert.composite.AlertComponent;
import shared.enums.Severity;
public class ClassificationHandler extends EnrichmentHandler {
    protected void doHandle(AlertComponent alert) {
        alert.setSeverity(Severity.CRITICAL);
        System.out.println("[Chain] ClassificationHandler: severity upgraded to CRITICAL.");
    }
}
