package enrichment.pipeline;
import alert.composite.AlertComponent;
public class DeduplicationHandler extends EnrichmentHandler {
    protected void doHandle(AlertComponent alert) { System.out.println("[Chain] DeduplicationHandler: no duplicate found for " + alert.getId()); }
}
