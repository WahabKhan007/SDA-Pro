# Response Orchestration API Contract

## Purpose
This contract describes how response actions are selected and executed for an incident.

## Execute Response Plan
`POST /incidents/{incidentId}/respond`

## Request Body
```json
{
  "incidentId": "INC-1001",
  "analystId": "ANALYST-01",
  "analystRole": "SOC_MANAGER",
  "businessContext": "Production server affected"
}
```

## Response Body
```json
{
  "incidentId": "INC-1001",
  "selectedStrategy": "AggressiveContainmentStrategy",
  "actions": [
    {
      "type": "BLOCK_IP",
      "status": "SUCCESS",
      "message": "Malicious IP blocked successfully"
    },
    {
      "type": "ISOLATE_ENDPOINT",
      "status": "SUCCESS",
      "message": "Endpoint isolated successfully"
    }
  ],
  "eventPublished": "RESPONSE_ACTION_EXECUTED"
}
```

## Notes
- Facade coordinates the response workflow.
- Strategy selects response actions based on severity.
- Factory Method creates action objects.
- Decorator adds audit, approval, rollback, and metrics behavior.
- Proxy checks authorization before execution.

## Related Code
- `IncidentResponseFacade`
- `ResponseStrategySelector`
- `ResponseActionFactory`
- `AuditLogDecorator`
- `ApprovalGateDecorator`
- `AuthorizedResponseActionProxy`
