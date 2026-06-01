package events;
import shared.enums.EventType;

public class DomainEvent {
    private EventType eventType;
    private String message;
    private Object payload;

    public DomainEvent(EventType eventType, String message, Object payload) {
        this.eventType = eventType;
        this.message = message;
        this.payload = payload;
    }
    public EventType getEventType() { return eventType; }
    public String getMessage() { return message; }
    public Object getPayload() { return payload; }
    @Override public String toString() { return eventType + ": " + message; }
}
