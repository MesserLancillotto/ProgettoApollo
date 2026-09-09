# Graph Report - ProgettoApollo  (2026-09-09)

## Corpus Check
- Corpus is ~42,077 words - fits in a single context window. You may not need a graph.

## Summary
- 807 nodes · 1678 edges · 55 communities (24 shown, 29 thin omitted)
- Extraction: 88% EXTRACTED · 12% INFERRED · 0% AMBIGUOUS · INFERRED: 199 edges (avg confidence: 0.81)
- Token cost: 1,500 input · 500 output

## Community Hubs (Navigation)
- Configurator Role & Views
- Configurator Role & Views
- Voluntary Management
- Database Place Domain
- Configurator Role & Views
- Configurator Role & Views
- Configurator Role & Views
- Beneficiary Booking & Controllers
- Voluntary Management
- Server Architecture & Strategy
- Database Place Domain
- Voluntary Management
- Protocol Reply Layer
- Database Event Domain
- Module Usermodel
- Database Event Domain
- Protocol Reply Layer
- User Authentication & Login
- Voluntary Management
- Protocol Request Layer
- Configurator Role & Views
- Protocol Request Layer
- Configurator Role & Views
- User Authentication & Login
- Configurator Role & Views
- Protocol Reply Layer
- Protocol Reply Layer
- Voluntary Management
- Protocol Reply Layer
- Protocol Request Layer
- Protocol Request Layer
- Server Engine Handlers
- Server Engine Handlers
- Server Engine Handlers
- Client Network Dispatcher
- Protocol Reply Layer
- Protocol Request Layer
- Protocol Request Layer
- Protocol Request Layer
- Protocol Request Layer
- Protocol Request Layer
- Protocol Request Layer
- Protocol Request Layer
- Module Client
- Module Progetto
- Module Demetra
- Module Polymorphism
- Module Fabrication
- Module Umlspecifico
- Module Report
- Configurator Role & Views
- Module App
- Voluntary Management

## God Nodes (most connected - your core abstractions)
1. `AuthenticatedReply` - 73 edges
2. `ConfiguratorView` - 59 edges
3. `AuthenticatedEngine` - 58 edges
4. `ComunicationType` - 57 edges
5. `Client` - 47 edges
6. `AuthenticatedRequest` - 44 edges
7. `AuthenticatedUpdateReply` - 30 edges
8. `UserModel` - 30 edges
9. `BeneficiaryView` - 29 edges
10. `VoluntaryView` - 25 edges

## Surprising Connections (you probably didn't know these)
- `Pattern MVC` --conceptually_related_to--> `Client`  [INFERRED]
  presentazione/scaletta.txt → src/main/java/Client/Client.java
- `Pattern Strategy` --conceptually_related_to--> `EnginePureFabricatiorContext`  [INFERRED]
  presentazione/scaletta.txt → src/main/java/Server/EnginePureFabricatiorContext.java
- `Pattern Facade` --conceptually_related_to--> `ServerAPI`  [INFERRED]
  presentazione/scaletta.txt → src/main/java/Server/ServerAPI.java
- `H2 Relational Database` --references--> `UserCreator`  [INFERRED]
  README.md → src/main/java/Server/Engine/Helper/UserCreator.java
- `UML Architettura Generale` --conceptually_related_to--> `Architettura Fat Client`  [INFERRED]
  presentazione/UMLGenerico.png → presentazione/scaletta.txt

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Architettura e Pattern Progetto Apollo** — presentazione_scaletta_architettura_fat_client, presentazione_scaletta_pattern_strategy, presentazione_scaletta_pattern_facade, presentazione_scaletta_pattern_mvc [INFERRED 0.85]

## Communities (55 total, 29 thin omitted)

### Community 0 - "Configurator Role & Views"
Cohesion: 0.08
Nodes (12): java.awt.event.ActionListener, JComboBox, JFrame, JTextArea, ConfiguratorController, ConfiguratorView, CardLayout, JButton (+4 more)

### Community 1 - "Configurator Role & Views"
Cohesion: 0.05
Nodes (13): Pattern MVC, Client, JSONArray, JSONObject, DataInputStream, DataOutputStream, ServerSocket, Socket (+5 more)

### Community 2 - "Voluntary Management"
Cohesion: 0.06
Nodes (17): JList, org.json.JSONArray, ChangePswdView, JButton, JPasswordField, FunctionController, FunctionPasswordController, Override (+9 more)

### Community 3 - "Database Place Domain"
Cohesion: 0.06
Nodes (16): java.sql.Connection, org.junit.jupiter.api.AfterEach, org.junit.jupiter.api.BeforeEach, org.junit.jupiter.api.Test, org.junit.jupiter.params.ParameterizedTest, org.junit.jupiter.params.provider.EnumSource, ComunicationTypeStringConverter, EventInstance (+8 more)

### Community 4 - "Configurator Role & Views"
Cohesion: 0.08
Nodes (12): ConfiguratorModel, FirstAccessView, ActionListener, JButton, JPasswordField, JTextField, FunctionConfCompleteRegistrationController, DefaultListModel (+4 more)

### Community 5 - "Configurator Role & Views"
Cohesion: 0.08
Nodes (15): PreparedStatement, JSONObject, User, UserRole, CONFIGURATOR, USER, VOLUNTARY, GetPersonalDataReply (+7 more)

### Community 6 - "Configurator Role & Views"
Cohesion: 0.10
Nodes (11): DeleteUserSubscriptionToEventEngine, GetAllowedVisitTypesEngine, GetEventEngine, GetSubscribedEventsEngine, AuthenticatedEngine, Connection, JSONObject, SetClosedDaysEngine (+3 more)

### Community 7 - "Beneficiary Booking & Controllers"
Cohesion: 0.13
Nodes (7): BeneficiaryController, BeneficiaryView, BookingConfirmListener, EventSelectionData, CardLayout, JButton, JPanel

### Community 8 - "Voluntary Management"
Cohesion: 0.09
Nodes (8): Place, GetPlacesReply, SetUserSubscriptionToEventReply, GetPlacesEngine, JSONObject, Connection, PlaceCreator, SetVisitablePlacesEngine

### Community 9 - "Server Architecture & Strategy"
Cohesion: 0.09
Nodes (15): Pattern Facade, Pattern Strategy, ReplyInterface, JSONObject, Request, Engine, JSONObject, EngineInterface (+7 more)

### Community 10 - "Database Place Domain"
Cohesion: 0.11
Nodes (7): DeletePlaceReply, DeleteUserSubscriptionToEventReply, AuthenticatedUpdateReply, Override, SetMaximumFriendsReply, SetVisitablePlacesReply, DeletePlaceEngine

### Community 11 - "Voluntary Management"
Cohesion: 0.09
Nodes (22): ComunicationType, DELETE_PLACE, DELETE_USER_SUBSCRIPTION_TO_EVENT, DELETE_VISIT, DELETE_VOLUNTARY, EDIT_VISITABLE_PLACES, GET_ALLOWED_VISIT_TYPES, GET_EVENT (+14 more)

### Community 12 - "Protocol Reply Layer"
Cohesion: 0.15
Nodes (7): EditVisitablePlacesReply, GetAllowedVisitTypesReply, AuthenticatedReply, JSONObject, NegativeAuthenticatedReply, SetNewUserReply, EditVisitablePlacesEngine

### Community 13 - "Database Event Domain"
Cohesion: 0.12
Nodes (7): DeleteVisitTypeFromPlaceRequest, EditVisitablePlacesRequest, GetEventRequest, AuthenticatedRequest, SetClosedDaysRequest, SetNewPasswordRequest, SetNewUserRequest

### Community 15 - "Database Event Domain"
Cohesion: 0.20
Nodes (6): Event, JSONObject, GetEventReply, GetSubscribedEventsReply, EventCreator, Connection

### Community 16 - "Protocol Reply Layer"
Cohesion: 0.17
Nodes (4): DeleteVisitTypeFromPlaceReply, SetClosedDaysReply, DeleteVisitTypeFromPlaceEngine, DateIntervalCalculator

### Community 17 - "User Authentication & Login"
Cohesion: 0.16
Nodes (3): UserLogin, JSONObject, UserLoginModel

### Community 18 - "Voluntary Management"
Cohesion: 0.15
Nodes (5): H2 Relational Database, DeletePlaceRequest, DeleteVoluntaryRequest, GetSubscribedEventsRequest, UserCreator

### Community 19 - "Protocol Request Layer"
Cohesion: 0.20
Nodes (3): GetVoluntariesRequest, JSONObject, Override

### Community 20 - "Configurator Role & Views"
Cohesion: 0.31
Nodes (3): org.json.JSONObject, JSONObject, UserFactory

### Community 21 - "Protocol Request Layer"
Cohesion: 0.22
Nodes (3): GetPlacesRequest, JSONObject, Override

### Community 23 - "User Authentication & Login"
Cohesion: 0.33
Nodes (6): JButton, JLabel, JPanel, JPasswordField, JTextField, UserLoginView

### Community 24 - "Configurator Role & Views"
Cohesion: 0.22
Nodes (5): BeneficiaryModel, UserType, BENEFICIARY, CONFIGURATOR, VOLUNTARY

### Community 25 - "Protocol Reply Layer"
Cohesion: 0.29
Nodes (3): GetMaximumFriendsReply, GetMaximumFriendsEngine, Override

## Knowledge Gaps
- **40 isolated node(s):** `com.tuo:progetto`, `DELETE_PLACE`, `DELETE_USER_SUBSCRIPTION_TO_EVENT`, `DELETE_VISIT`, `DELETE_VOLUNTARY` (+35 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 187 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **29 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `AuthenticatedReply` connect `Protocol Reply Layer` to `Server Engine Handlers`, `Server Engine Handlers`, `Protocol Reply Layer`, `Database Place Domain`, `Configurator Role & Views`, `Configurator Role & Views`, `Voluntary Management`, `Server Architecture & Strategy`, `Database Place Domain`, `Database Event Domain`, `Protocol Reply Layer`, `Configurator Role & Views`, `Protocol Reply Layer`, `Protocol Reply Layer`, `Voluntary Management`, `Protocol Reply Layer`, `Server Engine Handlers`?**
  _High betweenness centrality (0.282) - this node is a cross-community bridge._
- **Why does `Client` connect `Configurator Role & Views` to `Configurator Role & Views`, `Voluntary Management`, `Configurator Role & Views`, `Configurator Role & Views`, `Beneficiary Booking & Controllers`, `Voluntary Management`, `Server Architecture & Strategy`, `Configurator Role & Views`?**
  _High betweenness centrality (0.265) - this node is a cross-community bridge._
- **Why does `ComunicationType` connect `Voluntary Management` to `Configurator Role & Views`, `Client Network Dispatcher`, `Protocol Reply Layer`, `Database Place Domain`, `Protocol Request Layer`, `Protocol Request Layer`, `Protocol Request Layer`, `Protocol Request Layer`, `Server Architecture & Strategy`, `Protocol Request Layer`, `Protocol Request Layer`, `Protocol Request Layer`, `Database Event Domain`, `Voluntary Management`, `Protocol Request Layer`, `Protocol Request Layer`, `Protocol Request Layer`, `Protocol Request Layer`?**
  _High betweenness centrality (0.119) - this node is a cross-community bridge._
- **What connects `com.tuo:progetto`, `DELETE_PLACE`, `DELETE_USER_SUBSCRIPTION_TO_EVENT` to the rest of the system?**
  _40 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Configurator Role & Views` be split into smaller, more focused modules?**
  _Cohesion score 0.08133971291866028 - nodes in this community are weakly interconnected._
- **Should `Configurator Role & Views` be split into smaller, more focused modules?**
  _Cohesion score 0.054563492063492064 - nodes in this community are weakly interconnected._
- **Should `Voluntary Management` be split into smaller, more focused modules?**
  _Cohesion score 0.06393442622950819 - nodes in this community are weakly interconnected._