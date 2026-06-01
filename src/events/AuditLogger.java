package events;
// PATTERN: Observer
// RATIONALE: Audit logging reacts automatically when important domain events occur.
public class AuditLogger implements Observer {
    public void update(DomainEvent event) { System.out.println("[AuditLogger] Audit record saved: " + event); }
    public String getObserverId() { return "AuditLogger"; }
}
