package alert.composite;
import shared.enums.Severity;
import shared.model.CanonicalAlert;

// PATTERN: Composite
// RATIONALE: Leaf object representing a single alert through the AlertComponent interface.
public class SingleAlert implements AlertComponent {
    private CanonicalAlert alert;
    public SingleAlert(CanonicalAlert alert) { this.alert = alert; }
    public CanonicalAlert getAlert() { return alert; }
    public String getId() { return alert.getAlertId(); }
    public Severity getSeverity() { return alert.getSeverity(); }
    public void setSeverity(Severity severity) { alert.setSeverity(severity); }
    public String getRepresentativeIp() { return alert.getSourceIp(); }
    public void display(String indent) { System.out.println(indent + "SingleAlert: " + alert); }
}
