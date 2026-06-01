# SDA-Pro — Security Incident Response & Threat Mitigation Platform

SDA-Pro is a semester project prototype for **Software Design and Architecture**. It simulates a simplified SOC platform that receives security alerts, normalizes and enriches them, creates incidents, executes response actions, updates the dashboard, and stores audit events.

## Main End-to-End Flow

```text
Mock Splunk/Firewall Alert
→ Adapter converts external format into RawAlert
→ Factory Method selects AlertNormalizer
→ RawAlert becomes CanonicalAlert
→ Composite groups alerts into AlertCampaign
→ Chain of Responsibility enriches alert
→ Proxy + Adapter perform threat-intel lookup
→ Incident is created
→ State Pattern controls incident lifecycle
→ Strategy selects response actions
→ Factory creates actions
→ Decorator adds audit/approval/rollback/metrics
→ Proxy checks authorization
→ Facade orchestrates response
→ Observer/EventBus updates dashboard, audit, notification, and metrics
```

## How to Run

### Linux / macOS / Git Bash

```bash
javac -d out $(find src -name "*.java")
java -cp out MainDemo
```

### Windows PowerShell

```powershell
Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName } > sources.txt
javac -d out @sources.txt
java -cp out MainDemo
```


## Web GUI Dashboard Added

A browser-based GUI has been added through `src/WebDashboardServer.java`. The original console demo remains available in `MainDemo.java`; no original backend package, document, UML, ADR, or API file was removed.

### Run the Web GUI

#### Linux / macOS / Git Bash

```bash
javac -d out $(find src -name "*.java")
java -cp out WebDashboardServer
```

Optional: use `java -cp out WebDashboardServer 8080` if you specifically want port 8080.

Then open this URL in your browser:

```text
http://localhost:9090
```

#### Windows PowerShell

```powershell
Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName } > sources.txt
javac -d out @sources.txt
java -cp out WebDashboardServer
```

Optional: use `java -cp out WebDashboardServer 8080` if you specifically want port 8080.

Then open this URL in your browser:

```text
http://localhost:9090
```

### GUI Features

- Runs the Java backend workflow from the browser.
- Shows normalized alerts from Splunk and Firewall mock adapters.
- Shows campaign severity using the Composite pattern.
- Shows incident summary and final lifecycle state.
- Shows executed response actions from Strategy, Factory, Decorator, Proxy, and Facade logic.
- Shows backend console logs inside the GUI for teacher demonstration.

## Design Patterns Used

| Pattern | Main Classes | Purpose |
|---|---|---|
| Singleton | IngestionConfigManager, ThreatIntelCache, EventBusPublisher | Shared config, cache, and event bus |
| Factory Method | AlertNormalizerFactory, ResponseActionFactory | Create normalizers and response actions |
| Abstract Factory | NotificationFactory, EnterpriseNotificationFactory | Create related notification channels |
| Composite | AlertComponent, SingleAlert, AlertCampaign, IncidentCluster | Treat single and grouped alerts uniformly |
| Facade | IncidentResponseFacade | Simplify response orchestration |
| Adapter | SplunkAdapter, FirewallAdapter, VirusTotalAdapter, MISPAdapter | Convert external formats into internal contracts |
| Decorator | AuditLogDecorator, ApprovalGateDecorator, RollbackDecorator, MetricsDecorator | Add behavior to response actions dynamically |
| Proxy | CachingThreatIntelProxy, AuthorizedResponseActionProxy | Cache intel lookups and control action access |
| State | IncidentState and lifecycle state classes | Control incident lifecycle behavior |
| Chain of Responsibility | EnrichmentHandler and handlers | Process alerts through enrichment stages |
| Observer | EventBusPublisher, DashboardUpdater, AuditLogger, NotificationDispatcher, MetricsCollector | Notify modules about domain events |
| Strategy | ResponseStrategy and concrete strategies | Select response algorithm by severity/context |

## Architecture Styles

- **SOA:** represented through separate service packages.
- **MVC:** represented through dashboard controllers, models, and views.
- **Layered Architecture:** controller/service/domain/repository-style separation.
- **Event-Driven Architecture:** in-memory EventBusPublisher and Observer subscribers.

## Documentation

- `docs/adr/` — Architecture Decision Records.
- `docs/api/` — API contracts and event schemas.
- `docs/uml/` — PlantUML source files for class, component, and sequence diagrams.
- `screenshots/` — output and diagram screenshots.

## Submission Notes

This prototype uses mock adapters and in-memory storage to keep the project easy to run and explain.
