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

