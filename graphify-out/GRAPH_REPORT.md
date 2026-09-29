# Graph Report - ProgettoApollo  (2026-09-29)

## Corpus Check
- Corpus is ~32,720 words - fits in a single context window. You may not need a graph.

## Summary
- 692 nodes · 1254 edges · 62 communities (30 shown, 31 thin omitted)
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 112 edges (avg confidence: 0.83)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- Configurator Deletion GUIs
- Configurator Management GUI
- Place Entity Model
- Pure Fabrication Engine Core
- Deletion Network Replies
- Socket Networking & Patterns
- Event Entity Model
- Client Facade Operations
- Communication Protocol Types
- Administrative Requests Protocol
- Authenticated Engine Base
- Database Connection & Personal Data
- Client Integration Testing
- Voluntary & Event Engines
- Place Editing Network Protocol
- Event Subscription Engines
- Model Unit Tests
- User Profile Serialization
- Server Engine Declarations
- Communication Type Serialization
- Voluntary Filtering Engine
- Place Modification Engines
- Place Query Requests
- Visit Query Protocol
- Event Instance Model
- Voluntary Query Requests
- User Entity & Roles
- Application Client Bootstrap
- Client Request Interface
- Max Friends Configuration GUI
- Allowed Visit Types GUI
- Visit Types Query Engine
- Base Network Request
- Configurator Onboarding Use Cases
- Availability Configuration Request
- Visitable Places Configuration
- Possible Visits Query Engine
- User Registration Engine
- Beneficiary Onboarding Use Cases
- Volunteer Onboarding Use Cases
- Network Client Protocol
- Availability Engine
- Delete Place Request
- Delete Event Subscription Request
- Delete Visit Type Request
- Edit Visitable Places Request
- Event Query Request
- Subscribed Events Request
- Max Friends Request
- User Creation Request
- Event Subscription Request
- Fat Client Architecture
- Maven Project Definition
- Law of Demeter Rationale
- GRASP Polymorphism Pattern
- GRASP Pure Fabrication Pattern
- Class Diagram Architecture
- Technical Report Document
- Configurator Use Cases Diagram
- Beneficiary Use Cases Diagram
- Volunteer Use Cases Diagram

## God Nodes (most connected - your core abstractions)
1. `AuthenticatedReply` - 73 edges
2. `AuthenticatedEngine` - 59 edges
3. `ComunicationType` - 57 edges
4. `Client` - 51 edges
5. `AuthenticatedRequest` - 44 edges
6. `AuthenticatedUpdateReply` - 30 edges
7. `Place` - 25 edges
8. `RequestGUI` - 19 edges
9. `Event` - 14 edges
10. `ClientTest` - 14 edges

## Surprising Connections (you probably didn't know these)
- `DeletePlaceEngine` --semantically_similar_to--> `DeletePlace Task`  [INFERRED] [semantically similar]
  presentazione/PresentazioneProgettoApollo.pdf → TODO.md
- `GetPersonalDataEngine` --semantically_similar_to--> `GetPersonalData Task`  [INFERRED] [semantically similar]
  presentazione/PresentazioneProgettoApollo.pdf → TODO.md
- `Pattern MVC` --conceptually_related_to--> `Client`  [INFERRED]
  presentazione/scaletta.txt → src/main/java/Client/Client.java
- `Pattern Strategy` --conceptually_related_to--> `EnginePureFabricatiorContext`  [INFERRED]
  presentazione/scaletta.txt → src/main/java/Server/EnginePureFabricatiorContext.java
- `Pattern Facade` --conceptually_related_to--> `ServerAPI`  [INFERRED]
  presentazione/scaletta.txt → src/main/java/Server/ServerAPI.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Architettura e Pattern Progetto Apollo** — presentazione_scaletta_architettura_fat_client, presentazione_scaletta_pattern_strategy, presentazione_scaletta_pattern_facade, presentazione_scaletta_pattern_mvc [INFERRED 0.85]

## Communities (62 total, 31 thin omitted)

### Community 0 - "Configurator Deletion GUIs"
Cohesion: 0.05
Nodes (25): DeletePlaceRequestGUI, JButton, JTextField, Override, DeleteVisitTypeFromPlaceGUI, JButton, JTextField, Override (+17 more)

### Community 1 - "Configurator Management GUI"
Cohesion: 0.07
Nodes (17): FunctionalInterface, org.json.JSONObject, JSONObject, ConfiguratorGUI, GetPersonalDataGUI, JButton, JTextField, Override (+9 more)

### Community 2 - "Place Entity Model"
Cohesion: 0.08
Nodes (12): Place, GetPlacesReply, SetUserSubscriptionToEventReply, GetPlacesEngine, JSONObject, Connection, PlaceCreator, SetVisitablePlacesEngine (+4 more)

### Community 3 - "Pure Fabrication Engine Core"
Cohesion: 0.07
Nodes (39): DeletePlaceEngine, EngineInterface, EnginePureFabricator, EnginePureFabricatorContext, GetPersonalDataEngine, GetVoluntariesEngine, GRASP Information Expert Pattern, Model-View Separation (Fat Client) (+31 more)

### Community 4 - "Deletion Network Replies"
Cohesion: 0.08
Nodes (11): DeletePlaceReply, DeleteUserSubscriptionToEventReply, DeleteVisitTypeFromPlaceReply, DeleteVoluntaryReply, AuthenticatedUpdateReply, Override, SetDisponibilityReply, SetMaximumFriendsReply (+3 more)

### Community 5 - "Socket Networking & Patterns"
Cohesion: 0.10
Nodes (15): DataInputStream, DataOutputStream, Pattern Facade, Pattern Strategy, ServerSocket, Socket, NetworkClient, ReplyInterface (+7 more)

### Community 6 - "Event Entity Model"
Cohesion: 0.10
Nodes (10): Event, JSONObject, GetEventReply, GetMaximumFriendsReply, GetSubscribedEventsReply, GetMaximumFriendsEngine, Override, EventCreator (+2 more)

### Community 8 - "Communication Protocol Types"
Cohesion: 0.09
Nodes (22): ComunicationType, DELETE_PLACE, DELETE_USER_SUBSCRIPTION_TO_EVENT, DELETE_VISIT, DELETE_VOLUNTARY, EDIT_VISITABLE_PLACES, GET_ALLOWED_VISIT_TYPES, GET_EVENT (+14 more)

### Community 9 - "Administrative Requests Protocol"
Cohesion: 0.14
Nodes (6): DeleteVoluntaryRequest, GetAllowedVisitTypesRequest, GetMaximumFriendsRequest, AuthenticatedRequest, SetClosedDaysRequest, SetNewPasswordRequest

### Community 10 - "Authenticated Engine Base"
Cohesion: 0.17
Nodes (5): AuthenticatedEngine, Connection, JSONObject, SetMaximumFriendsEngine, SetNewPasswordEngine

### Community 11 - "Database Connection & Personal Data"
Cohesion: 0.19
Nodes (5): java.sql.Connection, org.junit.jupiter.api.AfterEach, org.junit.jupiter.api.BeforeEach, GetPersonalDataEngine, GetPersonalDataEngineTest

### Community 13 - "Voluntary & Event Engines"
Cohesion: 0.14
Nodes (4): DeleteVoluntaryEngine, GetEventEngine, SetClosedDaysEngine, SetNewOrganizationEngine

### Community 14 - "Place Editing Network Protocol"
Cohesion: 0.22
Nodes (5): EditVisitablePlacesReply, AuthenticatedReply, JSONObject, NegativeAuthenticatedReply, SetNewUserReply

### Community 15 - "Event Subscription Engines"
Cohesion: 0.17
Nodes (4): DeleteUserSubscriptionToEventEngine, GetSubscribedEventsEngine, JSONArray, SetUserSubscriptionToEventEngine

### Community 16 - "Model Unit Tests"
Cohesion: 0.22
Nodes (4): org.junit.jupiter.api.Test, EventInstanceTest, PlaceTest, UserFactoryTest

### Community 17 - "User Profile Serialization"
Cohesion: 0.23
Nodes (4): JSONObject, User, GetVoluntariesReply, UserTest

### Community 19 - "Communication Type Serialization"
Cohesion: 0.26
Nodes (4): org.junit.jupiter.params.ParameterizedTest, org.junit.jupiter.params.provider.EnumSource, ComunicationTypeStringConverter, ComunicationTypeStringConverterTest

### Community 20 - "Voluntary Filtering Engine"
Cohesion: 0.21
Nodes (5): PreparedStatement, GetVoluntariesEngine, JSONObject, Connection, UserCreator

### Community 21 - "Place Modification Engines"
Cohesion: 0.20
Nodes (3): DeletePlaceEngine, DeleteVisitTypeFromPlaceEngine, EditVisitablePlacesEngine

### Community 22 - "Place Query Requests"
Cohesion: 0.25
Nodes (3): GetPlacesRequest, JSONObject, Override

### Community 23 - "Visit Query Protocol"
Cohesion: 0.18
Nodes (4): GetPossibleVisitsReply, JSONObject, GetPossibleVisitsRequest, SetNewOrganizationRequest

### Community 24 - "Event Instance Model"
Cohesion: 0.25
Nodes (4): EventInstance, JSONObject, JSONArray, JsonListConverter

### Community 25 - "Voluntary Query Requests"
Cohesion: 0.20
Nodes (3): GetVoluntariesRequest, JSONObject, Override

### Community 26 - "User Entity & Roles"
Cohesion: 0.24
Nodes (5): UserRole, CONFIGURATOR, USER, VOLUNTARY, GetPersonalDataReply

### Community 27 - "Application Client Bootstrap"
Cohesion: 0.28
Nodes (3): JFrame, Main, BeforeEach

### Community 29 - "Max Friends Configuration GUI"
Cohesion: 0.29
Nodes (4): GetMaximumFriendsGUI, JButton, JTextField, Override

### Community 30 - "Allowed Visit Types GUI"
Cohesion: 0.29
Nodes (4): GetAllowedVisitTypesGUI, JButton, JTextField, Override

### Community 33 - "Configurator Onboarding Use Cases"
Cohesion: 0.33
Nodes (6): 1. Completa profilo e cambia password, Configuratore, 3. Fissa numero massimo iscritti, 2. Inserisce ambito territoriale, 4. Introduce luoghi destinati alle visite, Primo accesso configuratore

### Community 38 - "Beneficiary Onboarding Use Cases"
Cohesion: 0.80
Nodes (4): Creare credenziali, Fruitore, Primo accesso, Sistema

### Community 39 - "Volunteer Onboarding Use Cases"
Cohesion: 0.80
Nodes (5): Volontario Primo Accesso, Cambio password, Primo accesso, Sistema, Volontario

## Knowledge Gaps
- **57 isolated node(s):** `com.tuo:progetto`, `DELETE_PLACE`, `DELETE_USER_SUBSCRIPTION_TO_EVENT`, `DELETE_VISIT`, `DELETE_VOLUNTARY` (+52 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 205 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **31 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `AuthenticatedReply` connect `Place Editing Network Protocol` to `Configurator Management GUI`, `Place Entity Model`, `Deletion Network Replies`, `Socket Networking & Patterns`, `Event Entity Model`, `Possible Visits Query Engine`, `User Registration Engine`, `Authenticated Engine Base`, `Database Connection & Personal Data`, `Voluntary & Event Engines`, `Event Subscription Engines`, `User Profile Serialization`, `Server Engine Declarations`, `Place Modification Engines`, `Visit Query Protocol`, `User Entity & Roles`, `Visit Types Query Engine`?**
  _High betweenness centrality (0.315) - this node is a cross-community bridge._
- **Why does `Client` connect `Client Facade Operations` to `Base Network Request`, `Configurator Deletion GUIs`, `Place Entity Model`, `Configurator Management GUI`, `Client Integration Testing`, `Place Query Requests`, `User Entity & Roles`, `Application Client Bootstrap`, `Client Request Interface`, `Max Friends Configuration GUI`, `Allowed Visit Types GUI`?**
  _High betweenness centrality (0.163) - this node is a cross-community bridge._
- **Why does `ComunicationType` connect `Communication Protocol Types` to `Socket Networking & Patterns`, `Administrative Requests Protocol`, `Communication Type Serialization`, `Place Query Requests`, `Visit Query Protocol`, `Voluntary Query Requests`, `Client Request Interface`, `Base Network Request`, `Availability Configuration Request`, `Visitable Places Configuration`, `Network Client Protocol`, `Delete Place Request`, `Delete Event Subscription Request`, `Delete Visit Type Request`, `Edit Visitable Places Request`, `Event Query Request`, `Subscribed Events Request`, `Max Friends Request`, `User Creation Request`, `Event Subscription Request`?**
  _High betweenness centrality (0.128) - this node is a cross-community bridge._
- **What connects `com.tuo:progetto`, `DELETE_PLACE`, `DELETE_USER_SUBSCRIPTION_TO_EVENT` to the rest of the system?**
  _57 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Configurator Deletion GUIs` be split into smaller, more focused modules?**
  _Cohesion score 0.05319148936170213 - nodes in this community are weakly interconnected._
- **Should `Configurator Management GUI` be split into smaller, more focused modules?**
  _Cohesion score 0.0730804810360777 - nodes in this community are weakly interconnected._
- **Should `Place Entity Model` be split into smaller, more focused modules?**
  _Cohesion score 0.07665505226480836 - nodes in this community are weakly interconnected._