package enrichment;
import alert.composite.AlertComponent;
import enrichment.pipeline.*;
import threatintel.adapter.VirusTotalAdapter;
import threatintel.proxy.CachingThreatIntelProxy;
import events.*;
import shared.enums.EventType;

public class EnrichmentCorrelationService {
    public AlertComponent enrich(AlertComponent alert) {
        EnrichmentHandler h1 = new DeduplicationHandler();
        EnrichmentHandler h2 = new GeoIPHandler();
        EnrichmentHandler h3 = new ThreatIntelHandler(new CachingThreatIntelProxy(new VirusTotalAdapter()));
        EnrichmentHandler h4 = new AssetContextHandler();
        EnrichmentHandler h5 = new ClassificationHandler();
        h1.setNext(h2).setNext(h3).setNext(h4).setNext(h5);
        System.out.println("[Chain] Starting enrichment pipeline...");
        h1.handle(alert);
        EventBusPublisher.getInstance().publish(new DomainEvent(EventType.ALERT_ENRICHED, "Alert enrichment completed", alert));
        return alert;
    }
}
