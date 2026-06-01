# SDA-Pro Event Schema

## Purpose
This document describes domain events used in the event-driven architecture.

## Common Event Structure
```json
{
  "eventId": "EVT-1001",
  "eventType": "INCIDENT_CREATED",
  "timestamp": "2026-05-29T10:35:00Z",
  "message": "Critical incident created",
  "payload": {
    "incidentId": "INC-1001",
    "severity": "CRITICAL"
  }
}
```

## Event Types

### ALERT_INGESTED
Published when an external alert is received and normalized.

Subscribers:
- DashboardUpdater
- AuditLogger
- MetricsCollector

### ALERT_ENRICHED
Published after the enrichment pipeline finishes.

Subscribers:
- DashboardUpdater
- AuditLogger

### INCIDENT_CREATED
Published when a new incident is created.

Subscribers:
- DashboardUpdater
- AuditLogger
- NotificationDispatcher
- MetricsCollector

### INCIDENT_STATE_CHANGED
Published when incident lifecycle state changes.

Subscribers:
- DashboardUpdater
- AuditLogger
- MetricsCollector

### RESPONSE_ACTION_EXECUTED
Published after response orchestration executes an action.

Subscribers:
- DashboardUpdater
- AuditLogger
- NotificationDispatcher
- MetricsCollector

## Related Code
- `DomainEvent`
- `EventBusPublisher`
- `Observer`
- `DashboardUpdater`
- `AuditLogger`
- `NotificationDispatcher`
- `MetricsCollector`
