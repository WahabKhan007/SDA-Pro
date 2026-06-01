package notification.factory;
public class EmailNotifier implements Notifier { public void send(String message) { System.out.println("[Notification] Email sent: " + message); } }
