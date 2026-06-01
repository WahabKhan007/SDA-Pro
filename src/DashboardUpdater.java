package events;
// PATTERN: Observer
// RATIONALE: Dashboard receives real-time updates without tight coupling to publishers.
public class DashboardUpdater implements Observer {
    public void update(DomainEvent event) { System.out.println("[DashboardUpdater] Dashboard updated: " + event.getMessage()); }
    public String getObserverId() { return "DashboardUpdater"; }
}
