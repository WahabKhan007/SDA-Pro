package notification.factory;
public class SlackNotifier implements Notifier { public void send(String message) { System.out.println("[Notification] Slack sent: " + message); } }
