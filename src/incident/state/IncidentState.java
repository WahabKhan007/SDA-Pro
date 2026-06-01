package incident.state;
import incident.domain.Incident;

public interface IncidentState {
    void beginTriage(Incident incident);
    void initiateContainment(Incident incident);
    void beginEradication(Incident incident);
    void beginRecovery(Incident incident);
    void beginPostIncidentReview(Incident incident);
    void close(Incident incident);
    String getName();
}
