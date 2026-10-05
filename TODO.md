# Configurator

```mermaid
kanban
  todo[Todo]

  halfway[Halfway]

  done[Done]
    DeletePlace 
    DeleteVisitTypeFromPlace
    DeleteVoluntary 
    EditVisitablePlaces
    GetMaximumFriends
    GetPersonalData 
    GetEvent
    GetPlaces
    GetPossibleVisits
    SetVisitablePlaces
    SetMaximumFriends
    SetNewPassword
    SetClosedDays
```

# Voluntary

```mermaid
kanban
  todo[Todo]
        GetAllowedVisitTypes
        GetEvent
        GetPersonalData
        GetPlaces
        GetPossibleVisits
        SetNewPassword 
  halfway[Halfway]

  done[Done]
```

# User

```mermaid
kanban
  todo[Todo]
    DeleteUserSubscriptionToEvent
    GetEvent
    GetMaximumFriends
    GetPersonalData
    GetSubscribedEvents
    SetUserSubscriptionToEvent
  halfway[Halfway]

  done[Done]
```