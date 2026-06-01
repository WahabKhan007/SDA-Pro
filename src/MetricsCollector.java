package events;
// PATTERN: Observer
// RATIONALE: Metrics are updated independently whenever events are published.
public class MetricsCollector implements Observer {
    public void update(DomainEvent event) { System.out.println("[MetricsCollector] Metrics updated for: " + event.getEventType()); }
    public String getObserverId() { return "MetricsCollector"; }
}
