package notification.factory;
public class PagerDutyNotifier implements Notifier { public void send(String message) { System.out.println("[Notification] PagerDuty sent: " + message); } }
