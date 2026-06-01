package alert.composite;
import java.util.*;
import shared.enums.Severity;

// PATTERN: Composite
// RATIONALE: Groups related alerts into an incident cluster while preserving uniform AlertComponent usage.
public class IncidentCluster implements AlertComponent {
    private String clusterId;
    private List<AlertComponent> children = new ArrayList<>();
    public IncidentCluster(String clusterId) { this.clusterId = clusterId; }
    public String getId() { return clusterId; }
    public Severity getSeverity() {
        Severity max = Severity.LOW;
        for (AlertComponent child : children) if (child.getSeverity().ordinal() > max.ordinal()) max = child.getSeverity();
        return max;
    }
    public void setSeverity(Severity severity) { for (AlertComponent child : children) child.setSeverity(severity); }
    public String getRepresentativeIp() { return children.isEmpty() ? "0.0.0.0" : children.get(0).getRepresentativeIp(); }
    public void add(AlertComponent component) { children.add(component); }
    public void remove(AlertComponent component) { children.remove(component); }
    public List<AlertComponent> getChildren() { return children; }
    public void display(String indent) { System.out.println(indent + "IncidentCluster: " + clusterId + " children=" + children.size()); }
}
