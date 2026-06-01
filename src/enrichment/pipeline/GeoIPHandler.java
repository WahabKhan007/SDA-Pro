package enrichment.pipeline;
import alert.composite.AlertComponent;
public class GeoIPHandler extends EnrichmentHandler {
    protected void doHandle(AlertComponent alert) { System.out.println("[Chain] GeoIPHandler: IP " + alert.getRepresentativeIp() + " mapped to high-risk region."); }
}
