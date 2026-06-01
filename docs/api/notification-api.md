# Notification API Contract

## Purpose
This contract describes notification dispatch to SOC analysts and external channels.

## Endpoint
`POST /notifications/dispatch`

## Request Body
```json
{
  "incidentId": "INC-1001",
  "severity": "CRITICAL",
  "message": "Containment completed for critical incident",
  "channels": ["EMAIL", "SLACK", "PAGERDUTY"]
}
```

## Response Body
```json
{
  "status": "DISPATCHED",
  "sentChannels": ["EMAIL", "SLACK", "PAGERDUTY"]
}
```

## Notes
- Abstract Factory creates a family of related notification channels.
- NotificationDispatcher can be triggered through the event bus.

## Related Code
- `NotificationFactory`
- `EnterpriseNotificationFactory`
- `EmailNotifier`
- `SlackNotifier`
- `PagerDutyNotifier`
