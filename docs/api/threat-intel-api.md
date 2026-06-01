# Threat Intelligence API Contract

## Purpose
This contract describes reputation lookup for IPs, domains, hashes, or other indicators.

## Endpoint
`POST /threat-intel/reputation`

## Request Body
```json
{
  "indicator": "185.21.10.5",
  "indicatorType": "IP_ADDRESS",
  "provider": "VIRUSTOTAL"
}
```

## Response Body
```json
{
  "indicator": "185.21.10.5",
  "verdict": "MALICIOUS",
  "score": 92,
  "provider": "VIRUSTOTAL",
  "cacheStatus": "MISS"
}
```

## Notes
- Adapter converts provider-specific responses into internal `ReputationResult`.
- Proxy checks cache before calling the provider.
- Cache is in-memory for the prototype and Redis-ready for production.

## Related Code
- `ThreatIntelProvider`
- `VirusTotalAdapter`
- `MISPAdapter`
- `CachingThreatIntelProxy`
- `ThreatIntelCache`
