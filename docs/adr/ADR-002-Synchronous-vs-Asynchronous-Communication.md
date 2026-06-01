# ADR-002: Synchronous vs Asynchronous Communication

## Status
Accepted

## Context
SDA-Pro has two types of communication. Some operations need an immediate result, such as normalizing an alert or selecting a response strategy. Other operations should notify multiple modules without tightly coupling them, such as dashboard updates, audit logging, notifications, and metrics collection.

## Decision
We selected a **hybrid communication approach**.

- Synchronous calls are used for direct request/response operations.
- Asynchronous event-style communication is used for domain events through `EventBusPublisher`.

## Rationale
Synchronous calls keep the main business flow simple and easy to follow. Event-driven communication is better for side effects where the publisher should not know all receivers. For example, when an incident is created, the dashboard, audit logger, notification dispatcher, and metrics collector should all be updated without the incident service depending on each one directly.

## Consequences
- The core flow remains easy to debug and present.
- Event subscribers are loosely coupled.
- New subscribers can be added without changing publisher code.
- In the prototype, the event bus is in-memory. In a real deployment, it can be replaced by RabbitMQ or Kafka.

## Evidence in Project
- `src/events/EventBusPublisher.java`
- `src/events/DashboardUpdater.java`
- `src/events/AuditLogger.java`
- `src/events/NotificationDispatcher.java`
- `src/events/MetricsCollector.java`
- Event schema in `docs/api/event-schema.md`
