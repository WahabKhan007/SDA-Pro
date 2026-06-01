package ingestion;
import ingestion.adapter.ExternalAlertAdapter;
import ingestion.config.IngestionConfigManager;
import ingestion.normalizer.*;
import shared.enums.EventType;
import shared.model.*;
import events.*;

public class AlertIngestionService {
    private AlertNormalizerFactory factory = new AlertNormalizerFactory();
    private IngestionConfigManager config = IngestionConfigManager.getInstance();

    public CanonicalAlert ingest(ExternalAlertAdapter adapter) {
        RawAlert raw = adapter.fetchAlert();
        if (!config.isSourceEnabled(raw.getSourceType())) throw new IllegalStateException("Source disabled: " + raw.getSourceType());
        AlertNormalizer normalizer = factory.createNormalizer(raw.getSourceType());
        CanonicalAlert alert = normalizer.normalize(raw);
        EventBusPublisher.getInstance().publish(new DomainEvent(EventType.ALERT_INGESTED, "Alert ingested from " + adapter.getSourceName(), alert));
        return alert;
    }
}
