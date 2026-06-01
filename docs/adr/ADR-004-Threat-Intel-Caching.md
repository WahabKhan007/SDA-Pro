# ADR-004: Threat Intelligence Caching Strategy

## Status
Accepted

## Context
Threat intelligence providers such as VirusTotal and MISP may be slow, rate-limited, or expensive to query repeatedly. The same IP address or domain may appear in multiple alerts, so repeated lookups should be avoided.

## Decision
We selected a **Proxy-based caching strategy**. Threat intelligence requests go through `CachingThreatIntelProxy`. The proxy checks `ThreatIntelCache` before calling the real provider adapter.

## Rationale
This design naturally applies the Proxy Pattern. The client does not call VirusTotal or MISP directly. Instead, it calls the proxy, which controls access, checks cache, and then delegates to the real provider only when required.

## Consequences
- Repeated IP reputation checks are faster.
- The system avoids unnecessary calls to external providers.
- The proxy can later be extended with rate limiting and access control.
- Cache is in-memory for the prototype, but can be replaced with Redis in production.

## Evidence in Project
- `src/threatintel/proxy/CachingThreatIntelProxy.java`
- `src/threatintel/cache/ThreatIntelCache.java`
- `src/threatintel/adapter/VirusTotalAdapter.java`
- `src/threatintel/adapter/MISPAdapter.java`
