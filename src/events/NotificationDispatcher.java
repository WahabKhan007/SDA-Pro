package events;
// PATTERN: Observer
// RATIONALE: Notifications are dispatched when relevant events are published.
public class NotificationDispatcher implements Observer {
    public void update(DomainEvent event) { System.out.println("[NotificationDispatcher] Notification sent for event: " + event.getEventType()); }
    public String getObserverId() { return "NotificationDispatcher"; }
}
