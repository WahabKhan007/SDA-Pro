package enrichment.pipeline;
import alert.composite.AlertComponent;
public class AssetContextHandler extends EnrichmentHandler {
    protected void doHandle(AlertComponent alert) { System.out.println("[Chain] AssetContextHandler: destination asset marked as HIGH criticality."); }
}
