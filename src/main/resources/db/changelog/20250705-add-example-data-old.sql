-- after work
INSERT INTO EVENTS.EVENTS (title, location, start_date, end_date, description, type)
VALUES ('Afterwork Treffen', 'Terrasse',
        '2024-07-10 18:00:00', '2024-07-10 21:00:00',
        'Gemütlicher Ausklang.', 'AFTERWORK');

-- meet-up
INSERT INTO EVENTS.EVENTS (title, location, start_date, end_date, description, type)
VALUES ('JavaScript Meetup', 'Großer Konfi',
        '2024-08-01 17:00:00', '2024-08-01 20:00:00',
        'Meetup für JavaScript-Enthusiasten.', 'MEETUP');

-- conference (several dayx)
INSERT INTO EVENTS.EVENTS (title, location, start_date, end_date, description, type)
VALUES ('Java Konferenz', 'Messehallen Hamburg',
        '2024-09-15 09:00:00', '2024-09-17 18:00:00',
        'Dreitägige Konferenz mit internationalen Rednern.', 'CONFERENCE');

-- festivity (several days)
INSERT INTO EVENTS.EVENTS (title, location, start_date, end_date, description, type)
VALUES ('Sommerfest', 'Garten C',
        '2024-07-20 12:00:00', '2024-07-21 22:00:00',
        'Sommerfest für alle Mitarbeitenden.', 'FESTIVITY');

-- users
INSERT INTO EVENTS.USERS (email, first_name, last_name)
VALUES ('max.mustermann@datadrivers.de', 'Max', 'Mustermann');

INSERT INTO EVENTS.USERS (email, first_name, last_name)
VALUES ('erika.gabler@datadrivers.de', 'Erika', 'Gabler');

INSERT INTO EVENTS.USERS (email, first_name, last_name)
VALUES ('Paul.Panzer@datadrivers.de', 'Paul', 'Panzer');

INSERT INTO EVENTS.USERS (email, first_name, last_name)
VALUES ('homer.simpson@datadrivers.de', 'Homer', 'Simpson');

INSERT INTO EVENTS.USERS (email, first_name, last_name)
VALUES ('willy.wellensittich@datadrivers.de', 'Willie', 'Wellensittich');

INSERT INTO EVENTS.USERS (email, first_name, last_name)
VALUES ('ursula.spatz@datadrivers.de', 'Ursula', 'Spatz');

-- Event-Teilnehmende
INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (1, 1);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (1, 2);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (1, 3);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (1, 4);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (1, 5);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (1, 6);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (2, 4);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (2, 5);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (2, 6);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (3, 1);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (3, 3);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (3, 5);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (4, 2);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (4, 4);

INSERT INTO EVENTS.EVENT_PARTICIPANTS (EVENT_ID, USER_ID)
VALUES (4, 6);
