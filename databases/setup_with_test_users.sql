DROP TABLE users; 
DROP TABLE places;
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
        description VARCHAR(512),
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
        rendezvous VARCHAR(128)
    );
CREATE TABLE IF NOT EXISTS eventsData
    (
        name VARCHAR(32),
        start_date INT,
        end_date INT,
        state VARCHAR(16)
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
        date INT
    );

    

INSERT INTO USERS VALUES ('Magnificus.Administrator.00', '1234', 'Magnificus', 'Administrator', 'Ginnungagap', 1, 1, 2000, 0, '', 'CONFIGURATOR', 'true');


INSERT INTO users VALUES (
    'Paolo.Malatesta.82',
    'Francesca',
    'Paolo',
    'Malatesta',
    'Ravenna',
    7, 1, 1982,
    1768047600, -- 10/01/2026 12:20
    '',
    'USER',
    false
);

INSERT INTO users VALUES (
    'Francesca.Polenta.89',
    'Paolo',
    'Francesca',
    'Polenta',
    'Rimini',
    19, 8, 1989,
    1768047300, -- 10/01/2026 12:15
    '',
    'USER',
    false
);

INSERT INTO users VALUES (
    'Renzo.Tramaglino.94',
    'Milano',
    'Renzo',
    'Tramaglino',
    'Milano',
    28, 11, 1994,
    1768155600, -- 10/01/2026 18:20
    '',
    'USER',
    false
);

INSERT INTO users VALUES (
    'Lucia.Mondella.97',
    'Monza',
    'Lucia',
    'Mondella',
    'Milano',
    4, 6, 1997,
    1768155900, -- 10/01/2026 18:20
    '',
    'USER',
    false
);
