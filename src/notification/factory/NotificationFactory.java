package notification.factory;
public interface NotificationFactory { Notifier createEmailNotifier(); Notifier createSlackNotifier(); Notifier createPagerDutyNotifier(); }
