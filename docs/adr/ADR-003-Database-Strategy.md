# ADR-003: Database Strategy

## Status
Accepted

## Context
A real SOC platform needs persistent storage for incidents, alerts, audit logs, user sessions, and threat intelligence cache. The project guide recommends PostgreSQL for relational records and Redis for caching. However, the semester project focuses mainly on design patterns and architecture, not database administration.

## Decision
For the prototype, we selected **in-memory repositories and in-memory cache**. The design still documents the intended production database choices:

- PostgreSQL for incident records, normalized alerts, and audit logs.
- Redis for threat intelligence cache, sessions, and rate-limit counters.

## Rationale
Using in-memory storage keeps the prototype simple and runnable on any student machine. It also avoids setup issues during the demo. The architecture diagrams and API contracts still show how the system would connect to real databases in a production-ready version.

## Consequences
- The demo runs without external database setup.
- Data is not persisted after program termination.
- The architecture remains extendable because repository/service boundaries are separated.
- A future implementation can replace in-memory repositories with PostgreSQL repositories.

## Evidence in Project
- `src/audit/AuditRepository.java`
- `src/threatintel/cache/ThreatIntelCache.java`
- Component diagram includes PostgreSQL/Redis as production extension points.
