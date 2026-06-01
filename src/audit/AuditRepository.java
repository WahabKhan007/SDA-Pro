package audit;
import java.util.*;
public class AuditRepository {
    private List<String> logs = new ArrayList<>();
    public void save(String log) { logs.add(log); }
    public List<String> getAllLogs() { return Collections.unmodifiableList(logs); }
}
