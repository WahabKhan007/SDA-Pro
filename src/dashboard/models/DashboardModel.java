package dashboard.models;
import java.util.*;
public class DashboardModel {
    private List<String> messages = new ArrayList<>();
    public void addMessage(String message) { messages.add(message); }
    public List<String> getMessages() { return messages; }
}
