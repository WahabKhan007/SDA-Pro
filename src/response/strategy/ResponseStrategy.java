package response.strategy;
import incident.domain.Incident;
import java.util.*;
import shared.enums.ResponseActionType;
public interface ResponseStrategy { List<ResponseActionType> determineActions(Incident incident); String getName(); }
