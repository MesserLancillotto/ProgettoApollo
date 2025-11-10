CREATE TABLE IF NOT EXISTS users (userName VARCHAR(32), cityOfResidence VARCHAR(32), birthYear INT, userID VARCHAR(64) UNIQUE, userPassword VARCHAR(64), role VARCHAR(16), organization VARCHAR(32), changePasswordDue BOOLEAN);
CREATE TABLE IF NOT EXISTS allowedVisits (userID VARCHAR(64), visitType VARCHAR(32));
CREATE TABLE IF NOT EXISTS organizations (organization VARCHAR(32), territory VARCHAR(64));
CREATE TABLE IF NOT EXISTS events (eventName VARCHAR(64) UNIQUE, eventDescription VARCHAR(512), city VARCHAR(16), address VARCHAR(64), meetingPoint VARCHAR(64), startDate INT, endDate INT, organization VARCHAR(32), minUsers INT, maxUsers INT,  maxFriends INT, visitType VARCHAR(32), state VARCHAR(16), ticketPrice FLOAT, voluntariesCanSubmit BOOLEAN, usersCanSubmit BOOLEAN);
CREATE TABLE IF NOT EXISTS daysOfWeek (eventName VARCHAR(64), weekDay VARCHAR(3), startHour INT, duration INT);
CREATE TABLE IF NOT EXISTS eventsVoluntaries (eventName VARCHAR(64), userID VARCHAR(64), time INT);
CREATE TABLE IF NOT EXISTS eventsUsers (eventName VARCHAR(64), userID VARCHAR(64), time INT);
CREATE TABLE IF NOT EXISTS closedDays (startDay INT, endDay INT, organization VARCHAR(32));

