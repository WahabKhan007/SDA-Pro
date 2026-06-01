# ADR-005: Real-Time Push Strategy

## Status
Accepted

## Context
SOC analysts need fast updates when new alerts arrive, incidents are created, or response actions are executed. The requirement mentions real-time dashboard updates using Observer/Event-Driven architecture.

Possible options include polling, Server-Sent Events, WebSockets, or a message broker.

## Decision
For the prototype, we selected an **Observer-based in-memory event bus**. The `EventBusPublisher` publishes events to subscribed observers such as DashboardUpdater, AuditLogger, NotificationDispatcher, and MetricsCollector.

For a production version, this can be mapped to WebSockets/SSE for dashboard push and RabbitMQ/Kafka for service-level event distribution.

## Rationale
Observer is simple, clear, and directly required by the project. It shows real-time behavior without needing external infrastructure. It also proves loose coupling because publishers do not directly call concrete dashboard or audit classes.

## Consequences
- Dashboard updates are visible immediately in console output.
- Event-driven architecture is easy to demonstrate.
- No external message broker is required for the prototype.
- Real deployment can replace the in-memory event bus with RabbitMQ/Kafka and WebSockets.

## Evidence in Project
- `src/events/EventBusPublisher.java`
- `src/events/DashboardUpdater.java`
- `src/events/AuditLogger.java`
- `src/events/NotificationDispatcher.java`
- `src/events/MetricsCollector.java`
- Sequence diagrams in `docs/uml/03-sequence-alert-ingestion.puml` and `docs/uml/04-sequence-response-orchestration.puml`
