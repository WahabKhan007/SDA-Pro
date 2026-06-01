package events;
import java.util.*;
import shared.enums.EventType;

// PATTERN: Singleton + Observer
// RATIONALE: One event bus publishes domain events to multiple independent subscribers.
public class EventBusPublisher implements Subject {
    private static EventBusPublisher instance;
    private Map<EventType, List<Observer>> observers = new HashMap<>();
    private EventBusPublisher() {}
    public static synchronized EventBusPublisher getInstance() {
        if (instance == null) instance = new EventBusPublisher();
        return instance;
    }
    public void attach(EventType eventType, Observer observer) { observers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(observer); }
    public void detach(EventType eventType, Observer observer) { if (observers.containsKey(eventType)) observers.get(eventType).remove(observer); }
    public void publish(DomainEvent event) {
        System.out.println("[Observer] Publishing event -> " + event);
        notifyObservers(event);
    }
    public void notifyObservers(DomainEvent event) {
        for (Observer observer : observers.getOrDefault(event.getEventType(), Collections.emptyList())) observer.update(event);
    }
}
