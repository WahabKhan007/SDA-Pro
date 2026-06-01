package alert.composite;
import java.util.*;
import shared.enums.Severity;

public interface AlertComponent {
    String getId();
    Severity getSeverity();
    void setSeverity(Severity severity);
    String getRepresentativeIp();
    void display(String indent);
    default void add(AlertComponent component) { throw new UnsupportedOperationException("Cannot add child to this component."); }
    default void remove(AlertComponent component) { throw new UnsupportedOperationException("Cannot remove child from this component."); }
    default List<AlertComponent> getChildren() { return Collections.emptyList(); }
}
