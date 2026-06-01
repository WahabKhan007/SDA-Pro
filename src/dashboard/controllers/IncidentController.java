package dashboard.controllers;
import incident.domain.Incident;
public class IncidentController {
    public void showIncident(Incident incident) { System.out.println("[MVC Controller] Showing incident in dashboard: " + incident); }
}
