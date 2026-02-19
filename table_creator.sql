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