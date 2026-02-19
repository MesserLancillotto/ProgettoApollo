INSERT INTO organizations VALUES (
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

