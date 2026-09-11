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

INSERT INTO users VALUES (
    'sa',
    '1',
    'Lancillotto',
    'Benacense',
    'Desenzano',
    23, 12, 1999,
    1767776400, -- 07/01/2026 9:00
    'San Genesio',
    'CONFIGURATOR',
    false
);

INSERT INTO users VALUES (
    'Lancillotto.Benacense.99',
    'Altachiara',
    'Lancillotto',
    'Benacense',
    'Desenzano',
    23, 12, 1999,
    1767776400, -- 07/01/2026 9:00
    'San Genesio',
    'CONFIGURATOR',
    false
);

INSERT INTO users VALUES (
    'Parsifal.Silvano.00',
    'Longino',
    'Parsifal',
    'Silvano',
    'Lonato',
    24, 1, 2000,
    1767777000, -- 07/01/2026 9:10
    'San Genesio',
    'CONFIGURATOR',
    false
);

INSERT INTO users VALUES (
    'Arlecchino.Valcalepio.89',
    'vino&carte',
    'Arlecchino',
    'Valcalepio',
    'Desenzano',
    15, 10, 1989,
    1767778200, -- 07/01/2026 9:30
    'San Genesio',
    'VOLUNTARY',
    false
);

INSERT INTO users VALUES (
    'Balanzone.Pignoletto.92',
    'Michelas',
    'Balanzone',
    'Pignoletto',
    'Lonato',
    3, 3, 1992,
    1767779100, -- 07/01/2026 9:45
    'San Genesio',
    'VOLUNTARY',
    false
);

INSERT INTO users VALUES (
    'Colombina.Lison.98',
    'carnevale',
    'Colombina',
    'Lison',
    'Desenzano',
    22, 7, 1998,
    1767780000, -- 07/01/2026 10:00
    'San Genesio',
    'VOLUNTARY',
    false
);

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

-- generati da Gemini (i dati di test non sto a scriverli tutti a mano)

-- 1. Dante e Beatrice
INSERT INTO users VALUES ('Dante.Alighieri.85', 'VitaNova', 'Dante', 'Alighieri', 'Firenze', 12, 5, 1985, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Beatrice.Portinari.88', 'GuidaDivina', 'Beatrice', 'Portinari', 'Firenze', 19, 6, 1988, 1775000000, '', 'USER', false);

-- 2. Petrarca e Laura
INSERT INTO users VALUES ('Francesco.Petrarca.81', 'Canzoniere', 'Francesco', 'Petrarca', 'Arezzo', 20, 7, 1981, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Laura.DeNoves.83', 'Valchiusa', 'Laura', 'DeNoves', 'Avignone', 4, 6, 1983, 1775000000, '', 'USER', false);

-- 3. Orlando e Angelica
INSERT INTO users VALUES ('Orlando.DArlono.90', 'FolliaDAmore', 'Orlando', 'DArlono', 'Parigi', 15, 8, 1990, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Angelica.DelCatai.93', 'Medoro', 'Angelica', 'DelCatai', 'Catai', 10, 3, 1993, 1775000000, '', 'USER', false);

-- 4. Tancredi e Clorinda
INSERT INTO users VALUES ('Tancredi.DAltavilla.86', 'DuelloTragico', 'Tancredi', 'DAltavilla', 'Bari', 18, 3, 1986, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Clorinda.Guerriera.88', 'BattesimoInMorte', 'Clorinda', 'Guerriera', 'Gerusalemme', 25, 11, 1988, 1775000000, '', 'USER', false);

-- 5. Boccaccio e Fiammetta
INSERT INTO users VALUES ('Giovanni.Boccaccio.82', 'Decameron', 'Giovanni', 'Boccaccio', 'Certaldo', 16, 6, 1982, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Maria.DAquino.84', 'ElegiaFiammetta', 'Maria', 'DAquino', 'Napoli', 12, 10, 1984, 1775000000, '', 'USER', false);

-- 6. Jacopo e Teresa
INSERT INTO users VALUES ('Jacopo.Ortis.94', 'ColliEuganei', 'Jacopo', 'Ortis', 'Venezia', 11, 11, 1994, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Teresa.Rabbia.97', 'OrazioRivoira', 'Teresa', 'Rabbia', 'Padova', 22, 4, 1997, 1775000000, '', 'USER', false);

-- 7. Rinaldo e Armida
INSERT INTO users VALUES ('Rinaldo.DEste.89', 'GiardinoIncantato', 'Rinaldo', 'DEste', 'Ferrara', 1, 9, 1989, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Armida.Siren.91', 'SpecchioMagico', 'Armida', 'Siren', 'Damasco', 14, 2, 1991, 1775000000, '', 'USER', false);

-- 8. Piramo e Tisbe
INSERT INTO users VALUES ('Piramo.Babilonese.00', 'GelsiRossi', 'Piramo', 'Babilonese', 'Babilonia', 5, 5, 2000, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Tisbe.Babilonese.02', 'FessuraNelMuro', 'Tisbe', 'Babilonese', 'Babilonia', 17, 9, 2002, 1775000000, '', 'USER', false);

-- 9. Guiscardo e Ghismunda
INSERT INTO users VALUES ('Guiscardo.Valletto.98', 'CuoreNelCalice', 'Guiscardo', 'Valletto', 'Salerno', 3, 1, 1998, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Ghismunda.DiSalerno.01', 'PrincipeTancredi', 'Ghismunda', 'DiSalerno', 'Salerno', 19, 8, 2001, 1775000000, '', 'USER', false);

-- 10. Ruggiero e Bradamante
INSERT INTO users VALUES ('Ruggiero.DiRisa.92', 'Ippogrifo', 'Ruggiero', 'DiRisa', 'ReggioCalabria', 24, 12, 1992, 1775000000, '', 'USER', false);
INSERT INTO users VALUES ('Bradamante.DiChiaramonte.95', 'LanciaDOro', 'Bradamante', 'DiChiaramonte', 'Dordogna', 30, 5, 1995, 1775000000, '', 'USER', false);

INSERT INTO places VALUES (
    'San Genesio',
    'Desenzano',
    'Via Castello 63',
    'Cinema',
    'Cinema temporaneo all''aperto',
    'Arlecchino.Valcalepio.89'
);

INSERT INTO places VALUES (
    'San Genesio',
    'Desenzano',
    'Via Castello 63',
    'Teatro',
    'Rappresentazioni teatrali all''aperto',
    'Colombina.Lison.98'
);

INSERT INTO places VALUES (
    'San Genesio',
    'Lonato',
    'Via Rocca 2',
    'Teatro',
    'Rappresentazioni teatrali all''aperto',
    'Balanzone.Pignoletto.92'
);

INSERT INTO userPermissions VALUES (
    'Arlecchino.Valcalepio.89',
    'Cinema'
);

INSERT INTO userPermissions VALUES (
    'Colombina.Lison.98',
    'Cinema' 
);

INSERT INTO userPermissions VALUES (
    'Colombina.Lison.98',
    'Teatro' 
);

INSERT INTO userPermissions VALUES (
    'Balanzone.Pignoletto.92',
    'Teatro' 
);

INSERT INTO closedDays VALUES (
    'San Genesio',
    1769904000, -- 01/02/2026 00:00
    1770422400 -- 07/02/2026 00:00
);

INSERT INTO organizations VALUES (
    'San Genesio',
    3
);

INSERT INTO territories VALUES (
    'San Genesio',
    'Desenzano'
);

INSERT INTO territories VALUES (
    'San Genesio',
    'Lonato'
);

INSERT INTO events VALUES (
    'Cinema in castello',
    'Rassegna cinematografica nel castello di Desenzano.',
    'Cinema',
    'San Genesio',
    'Desenzano',
    'Via Castello 63',
    'Ingresso principale sull''ex ponte levatoio.'
);

INSERT INTO events VALUES (
    'Teatro in rocca',
    'Rassegna teatrale nella rocca di Lonato.',
    'Teatro',
    'San Genesio',
    'Lonato',
    'Via Rocca 2',
    'Ingresso principale davanti al parcheggio.'
);

INSERT INTO eventsData VALUES (
    'Cinema in castello',
    1772830800, -- 06/03/2022 21:00
    1772838000, -- 06/03/2022 23:00
    'CONFIRMED'
);

INSERT INTO eventsData VALUES (
    'Cinema in castello',
    1772917200, -- 07/03/2022 21:00
    1772924400, -- 07/03/2022 23:00
    'CONFIRMED'
);

INSERT INTO eventsData VALUES (
    'Cinema in castello',
    1773000000, -- 08/03/2022 20:00
    1773007200, -- 08/03/2022 22:00
    'DELETED'
);

INSERT INTO eventsData VALUES (
    'Teatro in rocca',
    1772391600, -- 01/03/2022 19:00
    1772398800, -- 01/03/2022 21:00
    'PROPOSED'
);

INSERT INTO eventsData VALUES (
    'Teatro in rocca',
    1772996400, -- 08/03/2022 19:00
    1773003600, -- 08/03/2022 21:00
    'PROPOSED'
);

INSERT INTO eventsData VALUES (
    'Teatro in rocca',
    1773601200, -- 15/03/2022 19:00
    1773608400, -- 15/03/2022 21:00
    'PROPOSED'
);

INSERT INTO eventsData VALUES (
    'Teatro in rocca',
    1774206000, -- 22/03/2022 19:00
    1774213200, -- 22/03/2022 21:00
    'PROPOSED'
);

INSERT INTO eventsData VALUES (
    'Teatro in rocca',
    1774551600, -- 26/03/2022 19:00
    1774558800, -- 26/03/2022 21:00
    'PROPOSED'
);

INSERT INTO eventsVoluntaries VALUES (
    'Cinema in castello',
    'Arlecchino.Valcalepio.89',
    1772830800 -- 06/03/2022 21:00
);

INSERT INTO eventsVoluntaries VALUES (
    'Cinema in castello',
    'Colombina.Lison.98',
    1772830800 -- 06/03/2022 21:00    
);

INSERT INTO eventsVoluntaries VALUES (
    'Cinema in castello',
    'Arlecchino.Valcalepio.89',
    1772917200 -- 07/03/2022 21:00 
);

INSERT INTO subscriptions VALUES (
    'Paolo.Malatesta.82',
    'Cinema in castello',
    1772917200 -- 07/03/2022 21:00 
);

INSERT INTO subscriptions VALUES (
    'Francesca.Polenta.89',
    'Cinema in castello',
    1772917200 -- 07/03/2022 21:00 
);

INSERT INTO subscriptions VALUES (
    'Renzo.Tramaglino.94',
    'Cinema in castello',
    1772830800 -- 06/03/2022 21:00    
);

INSERT INTO subscriptions VALUES (
    'Lucia.Mondella.97',
    'Cinema in castello',
    1772830800 -- 06/03/2022 21:00
);
