package notification.factory;

// PATTERN: Abstract Factory
// RATIONALE: Creates a family of enterprise notification channels.
public class EnterpriseNotificationFactory implements NotificationFactory {
    public Notifier createEmailNotifier() { return new EmailNotifier(); }
    public Notifier createSlackNotifier() { return new SlackNotifier(); }
    public Notifier createPagerDutyNotifier() { return new PagerDutyNotifier(); }
}
