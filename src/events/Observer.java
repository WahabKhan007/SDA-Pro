package events;
public interface Observer { void update(DomainEvent event); String getObserverId(); }
