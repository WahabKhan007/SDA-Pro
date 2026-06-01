package events;
import shared.enums.EventType;
public interface Subject { void attach(EventType eventType, Observer observer); void detach(EventType eventType, Observer observer); void notifyObservers(DomainEvent event); }
