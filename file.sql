DROP TABLE users; 
DROP TABLE places; 
DROP TABLE placesData;
DROP TABLE userPermissions;
DROP TABLE closedDays;
DROP TABLE organizations;
DROP TABLE territories;
DROP TABLE events;
DROP TABLE eventsData;
DROP TABLE eventsVoluntaries;
DROP TABLE voluntaryDisponibilities;
DROP TABLE subscriptions;

CREATE TABLE IF NOT EXISTS users 
    (
        userID VARCHAR(32) UNIQUE,
        password VARCHAR(32),
        userName VARCHAR(16),
        userSurname VARCHAR(16),
        city VARCHAR(16),
        birth_dd INT, 
        birth_mm INT, 
        birth_yy INT, 
        userSince INT,
        organization VARCHAR(32),
        role VARCHAR(16),
        changePasswordDue BOOLEAN
    );
CREATE TABLE IF NOT EXISTS places 
    (
        organization VARCHAR(32),
        city VARCHAR(16),
        address VARCHAR(32),
        visitType VARCHAR(32),
        description VARCHAR(512) 
    );
CREATE TABLE IF NOT EXISTS placesData
    (
        city VARCHAR(16),
        address VARCHAR(32),
        visitType VARCHAR(32),
        userID VARCHAR(32)
    );
CREATE TABLE IF NOT EXISTS userPermissions
    (
        userID VARCHAR(32),
        visitType VARCHAR(32)
    );
CREATE TABLE IF NOT EXISTS closedDays
    (
        organization VARCHAR(32),
        closure_start_date INT,
        closure_end_date INT
    );
CREATE TABLE IF NOT EXISTS organizations
    (
        organization VARCHAR(32) PRIMARY KEY,
        maximum_friends INT
    );
CREATE TABLE IF NOT EXISTS territories
    (
        organization VARCHAR(32),
        city VARCHAR(16)
    );
CREATE TABLE IF NOT EXISTS events
    (
        name VARCHAR(32),
        description VARCHAR(256),
        visitType VARCHAR(32),
        organization VARCHAR(32),
        city VARCHAR(16),
        address VARCHAR(32),
        rendezvous VARCHAR(128),
        state VARCHAR(16)
    );
CREATE TABLE IF NOT EXISTS eventsData
    (
        name VARCHAR(32),
        start_date INT,
        end_date INT
    );
CREATE TABLE IF NOT EXISTS eventsVoluntaries
    (
        name VARCHAR(32),
        userID VARCHAR(32),
        date INT
    );
CREATE TABLE IF NOT EXISTS voluntaryDisponibilities
    (
        userID VARCHAR(32),
        start_date INT,
        end_date INT
    );

CREATE TABLE IF NOT EXISTS subscriptions
    (
        userID VARCHAR(32),
        name VARCHAR(32),
        start_date INT
    );INSERT INTO organizations VALUES (
    'San Genesio',
    3
);

INSERT INTO places VALUES (
    'San Genesio',
    'Brescia',
    'Via Castello 9',
    'Teatro',
    'Castello di Brescia'
); 

INSERT INTO places VALUES (
    'San Genesio',
    'Brescia',
    'Via Castello 9',
    'Cinema',
    'Castello di Brescia'
);  

INSERT INTO territories VALUES (
    'San Genesio',
    'Brescia'
);

INSERT INTO users VALUES (
    'Lancillotto.Benacense.99',
    'password',
    'Lancillotto',
    'Benacense',
    'Brescia',
    23,12,1999,
    1771083225,
    'San Genesio',
    'CONFIGURATOR',
    false
);

INSERT INTO users VALUES (
    'Arlecchino.Valcalepio.98',
    'vino&carte', 
    'Arlecchino',
    'Valcalepio',
    'Brescia',
    11,12,1997,
    1771257245,
    'San Genesio',
    'VOLUNTARY',
    false
);

INSERT INTO voluntaryDisponibilities VALUES (
    'Arlecchino.Valcalepio.98',
    10,
    100
);

INSERT INTO userPermissions VALUES (
    'Arlecchino.Valcalepio.98',
    'Cinema'
);

INSERT INTO placesData VALUES (
    'Brescia',
    'Via Castello 9',
    'Cinema',
    'Arlecchino.Valcalepio.98'
);

INSERT INTO users VALUES (
    'Balanzone.Pignoletto.83',
    'carnevale',
    'Balanzone',
    'Pignoletto',
    'Brescia',
    13, 14, 1983,
    1771257245,
    'San Genesio',
    'VOLUNTARY',
    false
);

INSERT INTO voluntaryDisponibilities VALUES (
    'Balanzone.Pignoletto.83',
    10,
    100
);

INSERT INTO userPermissions VALUES (
    'Balanzone.Pignoletto.83',
    'Teatro'
);

INSERT INTO placesData VALUES (
    'Brescia',
    'Via Castello 9',
    'Teatro',
    'Balanzone.Pignoletto.83'
);

INSERT INTO events VALUES (
    'Cinema in castello',
    'Proiezione dei grandi classici nel cinema all aperto',
    'Cinema',
    'San Genesio',
    'Brescia',
    'Via Castello 9',
    'Ingresso principale sotto il leone di San Marco',
    'CONFIRMED'
);

INSERT INTO events VALUES (
    'Teatro in castello',
    'Rappresentazione teatrale dello spettacolo "La rosa bianca" da parte della compagnia "Profumo di Cielo"',
    'Teatro',
    'San Genesio',
    'Brescia',
    'Via Castello 9',
    'Ingresso principale sotto il leone di San Marco',
    'CONFIRMED'
);

INSERT INTO eventsVoluntaries VALUES (
    'Cinema in castello',
    'Arlecchino.Valcalepio.98',
    10
);

INSERT INTO eventsVoluntaries VALUES (
    'Cinema in castello',
    'Arlecchino.Valcalepio.98',
    30
);

INSERT INTO eventsVoluntaries VALUES (
    'Cinema in castello',
    'Arlecchino.Valcalepio.98',
    50
);

INSERT INTO eventsVoluntaries VALUES (
    'Teatro in castello',
    'Balanzone.Pignoletto.83',
    20
);

INSERT INTO eventsVoluntaries VALUES (
    'Teatro in castello',
    'Balanzone.Pignoletto.83',
    40
);

INSERT INTO eventsVoluntaries VALUES (
    'Teatro in castello',
    'Balanzone.Pignoletto.83',
    60
);

INSERT INTO eventsData VALUES (
    'Cinema in castello',
    10, 
    20
);

INSERT INTO eventsData VALUES (
    'Cinema in castello',
    30, 
    40
);

INSERT INTO eventsData VALUES (
    'Cinema in castello',
    50, 
    60
);

INSERT INTO eventsData VALUES (
    'Teatro in castello',
    20, 
    30
);

INSERT INTO eventsData VALUES (
    'Teatro in castello',
    40, 
    50
);

INSERT INTO eventsData VALUES (
    'Teatro in castello',
    60, 
    70
);

INSERT INTO users VALUES (
    'Michele.Monteclarense.89',
    'campagna',
    'Michele',
    'Monteclarense',
    'Brescia',
    23,12,1989,
    1771083225,
    '',
    'USER',
    false
);

INSERT INTO users VALUES (
    'Stefania.Arilicense.71',
    'macchina',
    'Stefania',
    'Arilicense',
    'Brescia',
    23,12,1971,
    1771083225,
    '',
    'USER',
    false
);

SELECT * FROM users; 
SELECT * FROM places; 
SELECT * FROM placesData;
SELECT * FROM userPermissions;
SELECT * FROM closedDays;
SELECT * FROM organizations;
SELECT * FROM territories;
SELECT * FROM events;
SELECT * FROM eventsData;
SELECT * FROM eventsVoluntaries;
SELECT * FROM voluntaryDisponibilities;
SELECT * FROM subscriptions;