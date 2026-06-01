package ingestion.adapter;
import shared.model.RawAlert;
public interface ExternalAlertAdapter { RawAlert fetchAlert(); String getSourceName(); }
