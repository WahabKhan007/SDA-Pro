package ingestion.normalizer;
import shared.enums.AlertSourceType;
import shared.model.*;
public interface AlertNormalizer { CanonicalAlert normalize(RawAlert rawAlert); boolean supports(AlertSourceType type); }
