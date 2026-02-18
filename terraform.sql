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
        type VARCHAR(32),
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
    'Bergamo',
    11,12,1997,
    1771257245,
    'San Genesio',
    'VOLUNTARY',
    false
);

INSERT INTO userPermissions VALUES (
    'Arlecchino.Valcalepio.98',
    'Cinema'
);

INSERT INTO users VALUES (
    'Balanzone.Pignoletto.83',
    'carnevale',
    'Balanzone',
    'Pignoletto',
    'Bergamo',
    13, 14, 1983,
    1771257245,
    'San Genesio',
    'VOLUNTARY',
    false
);

INSERT INTO userPermissions VALUES (
    'Balanzone.Pignoletto.83',
    'Teatro'
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
    'Rappresentazioni teatrali della compagnia Profumo di Cielo',
    'Teatro',
    'San Genesio',
    'Brescia',
    'Via Castello 9',
    'Ingresso principale sotto il leone di San Marco',
    'CONFIRMED'
);

INSERT INTO eventsData VALUES (
    'Cinema in castello',
    10, 
    20
);

INSERT INTO eventsData VALUES (
    'Teatro in castello',
    10, 
    20
);

