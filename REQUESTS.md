- [X] DeletePlaceRequest 
- [X] DeleteVisitTypeFromPlaceRequest
- [X] DeleteVoluntaryRequest 
- [X] EditVisitablePlacesRequest
- [-] GetEventRequest 
- [X] GetMaximumFriendsRequest
- [X] GetPersonalDataRequest 
        configMap.put(
            "GetPlacesRequest", 
            new ActionConfig(
                "Dati posto", 
                "Ottiene le informazioni sul posto specificato ", 
                this::getPlacesRequest));
        configMap.put(
            "GetPossibleVisitsRequest", 
            new ActionConfig(
                "Visite organizzazione", 
                "Ottiene le informazioni sul posto specificato ", 
                this::getPossibleVisitsRequest));
}