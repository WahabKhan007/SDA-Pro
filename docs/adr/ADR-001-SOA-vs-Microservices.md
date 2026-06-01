# ADR-001: SOA vs Microservices vs Modular Monolith

## Status
Accepted

## Context
SDA-Pro contains several major capabilities: alert ingestion, enrichment and correlation, incident management, threat intelligence, response orchestration, notifications, audit logging, and dashboard updates. The semester requirement asks us to demonstrate SOA boundaries, MVC, layered architecture, event-driven communication, and all required design patterns.

A pure monolithic design would be easier to code, but it would hide service boundaries. A full microservices implementation with separate deployments, databases, and message broker configuration would be too heavy for the semester timeline.

## Decision
We selected a **modular SOA-style prototype** implemented in one Java codebase. Each major capability is represented as a separate service package, such as `AlertIngestionService`, `EnrichmentCorrelationService`, `IncidentManagementService`, `ResponseOrchestrationService`, `ThreatIntelService`, `NotificationService`, and `AuditService`.

## Rationale
This decision gives us clear service boundaries without over-engineering the project. It allows us to show SOA through component diagrams, package structure, service responsibilities, and API contracts, while still keeping the code simple enough to explain in viva.

## Consequences
- The design is easier to run and demonstrate as a student project.
- Service boundaries are visible in code and diagrams.
- The prototype can later be converted into real microservices if needed.
- Since this is not a fully distributed deployment, communication is simulated through direct service calls and an in-memory event bus.

## Evidence in Project
- `src/ingestion/AlertIngestionService.java`
- `src/enrichment/EnrichmentCorrelationService.java`
- `src/incident/IncidentManagementService.java`
- `src/response/ResponseOrchestrationService.java`
- `src/notification/NotificationService.java`
- `src/audit/AuditService.java`
- Component diagram in `docs/uml/02-component-diagram.puml`
