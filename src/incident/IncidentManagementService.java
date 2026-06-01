package incident;
import alert.composite.AlertComponent;
import incident.domain.Incident;
import events.*;
import shared.enums.*;

public class IncidentManagementService {
    public Incident createIncident(AlertComponent alert) {
        Incident incident = new Incident("INC-001", "Suspicious multi-source security incident", alert.getSeverity(), alert);
        EventBusPublisher.getInstance().publish(new DomainEvent(EventType.INCIDENT_CREATED, "Incident created: " + incident.getId(), incident));
        return incident;
    }
}
