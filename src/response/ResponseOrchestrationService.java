package response;
import incident.domain.Incident;
import response.facade.IncidentResponseFacade;

public class ResponseOrchestrationService {
    private IncidentResponseFacade facade = new IncidentResponseFacade();
    public void respond(Incident incident) { facade.assessAndRespond(incident); }
}
