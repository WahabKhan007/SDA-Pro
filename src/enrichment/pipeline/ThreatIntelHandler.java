package enrichment.pipeline;
import alert.composite.AlertComponent;
import shared.model.ReputationResult;
import threatintel.proxy.ThreatIntelProxy;

public class ThreatIntelHandler extends EnrichmentHandler {
    private ThreatIntelProxy proxy;
    public ThreatIntelHandler(ThreatIntelProxy proxy) { this.proxy = proxy; }
    protected void doHandle(AlertComponent alert) {
        ReputationResult result = proxy.checkReputation(alert.getRepresentativeIp());
        System.out.println("[Chain] ThreatIntelHandler: " + result);
    }
}
