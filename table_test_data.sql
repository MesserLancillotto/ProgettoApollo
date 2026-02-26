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
