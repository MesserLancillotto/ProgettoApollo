CREATE TABLE IF NOT EXISTS users 
    (
        userID VARCHAR(32), 
        userName VARCHAR(16),
        userSurname VARCHAR(16),
        city VARCHAR(16),
        birth_dd INT, 
        birth_mm INT, 
        birth_yy INT, 
        userSince INT,
        organization VARCHAR(32),
        role VARCHAR(8),
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
        visitType VARCHAR(16),
        userID VARCHAR(32)
    );
CREATE TABLE IF NOT EXISTS userPermissions
    (
        userID VARCHAR(32),
        visitType VARCHAR(16)
    );
CREATE TABLE IF NOT EXISTS closedDays
    (
        organization VARCHAR(32),
        closure_start_date, INT,
        closure_end_date, INT
    );
CREATE TABLE IF NOT EXISTS organizations
    (
        organization VARCHAR(32),
        maximum_friends INT
    );
CREATE TABLE IF NOT EXISTS territories
    (
        organization VARCHAR(32),
        city INT
    );
CREATE TABLE IF NOT EXISTS events
    (
        name VARCHAR(32),
        description VARCHAR(256),
        type VARCHAR(16),
        organization VARCHAR(32),
        city VARCHAR(16),
        address VARCHAR(32),
        rendezvous VARCHAR(32),
        state VARCHAR(16)
    );
CREATE TABLE IF NOT EXISTS eventsData
    (
        name VARCHAR(32),
        start INT,
        end INT
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
        start INT,
        end INT
    )