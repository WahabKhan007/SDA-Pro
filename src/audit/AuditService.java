package audit;
import events.DomainEvent;
public class AuditService {
    private AuditRepository repository = new AuditRepository();
    public void logEvent(DomainEvent event) { repository.save(event.toString()); }
    public void printLogs() { for (String log : repository.getAllLogs()) System.out.println("[AuditService] " + log); }
}
