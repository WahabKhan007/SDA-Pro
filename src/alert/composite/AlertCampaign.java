package alert.composite;
import java.util.*;
import shared.enums.Severity;

// PATTERN: Composite
// RATIONALE: Composite object groups alerts and lets the system treat a campaign like a single alert component.
public class AlertCampaign implements AlertComponent {
    private String campaignId;
    private String campaignName;
    private List<AlertComponent> children = new ArrayList<>();
    public AlertCampaign(String campaignId, String campaignName) { this.campaignId = campaignId; this.campaignName = campaignName; }
    public String getId() { return campaignId; }
    public Severity getSeverity() {
        Severity max = Severity.LOW;
        for (AlertComponent child : children) if (child.getSeverity().ordinal() > max.ordinal()) max = child.getSeverity();
        return max;
    }
    public void setSeverity(Severity severity) { for (AlertComponent child : children) child.setSeverity(severity); }
    public String getRepresentativeIp() { return children.isEmpty() ? "0.0.0.0" : children.get(0).getRepresentativeIp(); }
    public void add(AlertComponent component) { children.add(component); System.out.println("[Composite] Added alert to campaign: " + campaignName); }
    public void remove(AlertComponent component) { children.remove(component); }
    public List<AlertComponent> getChildren() { return children; }
    public void display(String indent) {
        System.out.println(indent + "AlertCampaign: " + campaignName + " severity=" + getSeverity());
        for (AlertComponent child : children) child.display(indent + "  ");
    }
}
