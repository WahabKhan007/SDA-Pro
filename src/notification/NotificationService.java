package notification;
import notification.factory.*;
public class NotificationService {
    private NotificationFactory factory = new EnterpriseNotificationFactory();
    public void notifyIncident(String message) {
        factory.createEmailNotifier().send(message);
        factory.createSlackNotifier().send(message);
        factory.createPagerDutyNotifier().send(message);
    }
}
