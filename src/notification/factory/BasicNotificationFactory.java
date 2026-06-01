package notification.factory;

// PATTERN: Abstract Factory
// RATIONALE: Creates a basic family of notification channels for small deployments.
public class BasicNotificationFactory implements NotificationFactory {
    public Notifier createEmailNotifier() { return new EmailNotifier(); }
    public Notifier createSlackNotifier() { return new SlackNotifier(); }
    public Notifier createPagerDutyNotifier() { return new EmailNotifier(); }
}
