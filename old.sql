DROP TABLE users;
DROP TABLE allowedVisits;
DROP TABLE organizations;
DROP TABLE events;
DROP TABLE eventsVoluntaries;
DROP TABLE closedDays;

CREATE TABLE IF NOT EXISTS users (userName VARCHAR(32), cityOfResidence VARCHAR(32), birthYear INT, userID VARCHAR(64) UNIQUE, userPassword VARCHAR(64), role VARCHAR(16), organization VARCHAR(32), changePasswordDue BOOLEAN);

INSERT INTO users VALUES ('Arlecchino Valcalepio', 'Bergamo', 1981, 'VOLUNTARY.Arlecchino.Valcalepio.81', 'michelas', 'VOLUNTARY', 'San Genesio', FALSE);
INSERT INTO users VALUES ('Balanzone Pignoletto', 'Bologna', 1983, 'VOLUNTARY.Balanzone.Pignoletto.83', 'pettola', 'VOLUNTARY', 'San Genesio', FALSE);
INSERT INTO users VALUES ('Pantalone Malvasia', 'Venezia', 1985, 'VOLUNTARY.Pantalone.Malvasia.85', 'schei', 'VOLUNTARY', 'San Genesio' , FALSE);
INSERT INTO users VALUES ('Carlo Goldoni', 'Venezia', 1997, 'CONFIGURATOR.Carlo.Goldoni.97', 'samuele', 'CONFIGURATOR', 'San Genesio', FALSE);

INSERT INTO users VALUES ('Pulcinella Palummo', 'Napoli', 1995, 'VOLUNTARY.Pulcinella.Palummo.95', 'lmZ2lbgE7hB5ZQrU', 'VOLUNTARY', 'Santa Cecilia', TRUE);
INSERT INTO users VALUES ('Antonio Vivaldi', 'Venezia', 1978, 'CONFIGURATOR.Antonio.Vivaldi.78', 'rosso', 'CONFIGURATOR', 'Santa Cecilia', TRUE);

INSERT INTO users VALUES ('Michelangelo Merisi', 'Milano', 1971, 'CONFIGURATOR.Michelangelo.Merisi.71', 'maddalena', 'CONFIGURATOR', '', FALSE);

CREATE TABLE IF NOT EXISTS allowedVisits (userID VARCHAR(64), visitType VARCHAR(32));

INSERT INTO allowedVisits VALUES ('VOLUNTARY.Arlecchino.Valcalepio.81', 'Cinema');
INSERT INTO allowedVisits VALUES ('VOLUNTARY.Arlecchino.Valcalepio.81', 'Visita guidata');
INSERT INTO allowedVisits VALUES ('VOLUNTARY.Balanzone.Pignoletto.83', 'Cinema');

CREATE TABLE IF NOT EXISTS organizations (organization VARCHAR(32), territory VARCHAR(64));

INSERT INTO organizations VALUES ('San Genesio', 'Bergamo');
INSERT INTO organizations VALUES ('San Genesio', 'Bologna');
INSERT INTO organizations VALUES ('San Genesio', 'Venezia');
INSERT INTO organizations VALUES ('Santa Cecilia', 'Brescia');
INSERT INTO organizations VALUES ('Santa Cecilia', 'Verona');
INSERT INTO organizations VALUES ('Santa Cecilia', 'Napoli');

CREATE TABLE IF NOT EXISTS events (eventName VARCHAR(64) UNIQUE, eventDescription VARCHAR(512), city VARCHAR(16), address VARCHAR(64), meetingPoint VARCHAR(64), startDate INT, endDate INT, organization VARCHAR(32), minUsers INT, maxUsers INT,  maxFriends INT, visitType VARCHAR(32), state VARCHAR(16), ticketPrice FLOAT, voluntariesCanSubmit BOOLEAN, usersCanSubmit BOOLEAN);

INSERT INTO events VALUES ('Cinema in Castello: la corazzata Potiomkin', 'Terna di serate per ammirare i grandi capolavori cinematografici del Novecento, ore 21:00', 'Bergamo', 'Castello di Bergamo', 'Entrata del castello', 1770411600, 1770584400, 'San Genesio', 50, 500, 4, 'Cinema', 'PROPOSED', 0.0, TRUE, TRUE);
INSERT INTO events VALUES ('Cinema in Castello: il deserto rosso', 'Terna di serate per ammirare i grandi capolavori cinematografici del Novecento, ore 21:00', 'Bologna', 'Castello di Bologna', 'Entrata del castello',1761944400, 1762117200, 'San Genesio', 50, 500, 4, 'Cinema', 'PROPOSED', 0.0, TRUE, TRUE);
INSERT INTO events VALUES ('Le quattro stagioni al parco', 'Ascoltate le quattro stagioni di Vivaldi al parco di Verona', 'Verona', 'Parco di Verona', 'Entrata del parco', 1, 2, 'Santa Cecilia', 25, 75, 3, 'Concerto', 'PROPOSED', 0.0, TRUE, TRUE);

CREATE TABLE IF NOT EXISTS daysOfWeek (eventName VARCHAR(64), weekDay VARCHAR(3), startHour INT, duration INT);

INSERT INTO daysOfWeek VALUES ('Cinema in Castello: la corazzata Potiomkin', 'ven', 2100, 80);
INSERT INTO daysOfWeek VALUES ('Cinema in Castello: la corazzata Potiomkin', 'sab', 2100, 80);
INSERT INTO daysOfWeek VALUES ('Cinema in Castello: la corazzata Potiomkin', 'dom', 2100, 80);

INSERT INTO daysOfWeek VALUES ('Cinema in Castello: il deserto rosso', 'ven', 2100, 80);
INSERT INTO daysOfWeek VALUES ('Cinema in Castello: il deserto rosso', 'sab', 2100, 80);
INSERT INTO daysOfWeek VALUES ('Cinema in Castello: il deserto rosso', 'dom', 2100, 80);

INSERT INTO daysOfWeek VALUES ('Le quattro stagioni al parco', 'ven', 2100, 80);
INSERT INTO daysOfWeek VALUES ('Le quattro stagioni al parco', 'sab', 2100, 80);
INSERT INTO daysOfWeek VALUES ('Le quattro stagioni al parco', 'dom', 2100, 80);

CREATE TABLE IF NOT EXISTS eventsVoluntaries (eventName VARCHAR(64), userID VARCHAR(64), time INT);

INSERT INTO eventsVoluntaries VALUES ('Cinema in Castello: la corazzata Potiomkin', 'VOLUNTARY.Arlecchino.Valcalepio.81', 1770411600);
INSERT INTO eventsVoluntaries VALUES ('Cinema in Castello: il deserto rosso', 'VOLUNTARY.Balanzone.Pignoletto.83', 1761944400);

CREATE TABLE IF NOT EXISTS closedDays (startDay INT, endDay INT, organization VARCHAR(32));

INSERT INTO closedDays VALUES (1766534340, 1767743940, 'San Genesio');
