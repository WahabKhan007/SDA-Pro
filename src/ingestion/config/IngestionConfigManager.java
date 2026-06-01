package ingestion.config;
import java.util.*;
import shared.enums.AlertSourceType;

// PATTERN: Singleton
// RATIONALE: One shared configuration manager controls enabled alert sources.
public class IngestionConfigManager {
    private static IngestionConfigManager instance;
    private Set<AlertSourceType> enabledSources = new HashSet<>();

    private IngestionConfigManager() {
        enabledSources.add(AlertSourceType.SPLUNK);
        enabledSources.add(AlertSourceType.FIREWALL);
    }
    public static synchronized IngestionConfigManager getInstance() {
        if (instance == null) instance = new IngestionConfigManager();
        return instance;
    }
    public boolean isSourceEnabled(AlertSourceType type) { return enabledSources.contains(type); }
    public void enableSource(AlertSourceType type) { enabledSources.add(type); }
    public void disableSource(AlertSourceType type) { enabledSources.remove(type); }
}
