# Incident Management API Contract

## Purpose
This contract describes incident lifecycle operations.

## Create Incident
`POST /incidents`

### Request Body
```json
{
  "alertGroupId": "CAMPAIGN-APT-001",
  "title": "Suspicious login campaign",
  "severity": "CRITICAL"
}
```

### Response Body
```json
{
  "incidentId": "INC-1001",
  "state": "NEW",
  "severity": "CRITICAL",
  "message": "Incident created in NewState"
}
```

## Begin Triage
`POST /incidents/{incidentId}/triage`

### Response Body
```json
{
  "incidentId": "INC-1001",
  "previousState": "NEW",
  "currentState": "UNDER_TRIAGE"
}
```

## Start Containment
`POST /incidents/{incidentId}/containment`

## Start Eradication
`POST /incidents/{incidentId}/eradication`

## Start Recovery
`POST /incidents/{incidentId}/recovery`

## Close Incident
`POST /incidents/{incidentId}/close`

## Notes
- State Pattern controls legal transitions.
- Closed incidents reject further lifecycle actions.

## Related Code
- `Incident`
- `IncidentState`
- `NewState`
- `UnderTriageState`
- `ContainmentState`
- `ClosedState`
