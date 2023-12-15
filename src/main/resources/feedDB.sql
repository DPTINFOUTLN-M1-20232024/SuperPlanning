-- FEED BUILDING TABLE
INSERT INTO BUILDING (NAME) VALUES
    ('U'),
    ('EVE'),
    ('X');

-- FEED ROOM TABLE
INSERT INTO ROOM (NAME, ID_BUILDING) VALUES
    ('001', 1),
    ('002', 1),
    ('PLACARD', 2),
    ('111', 3),
    ('112', 3);

-- FEED ROOM INSTRUCTOR
INSERT INTO INSTRUCTOR (FIRSTNAME, LASTNAME) VALUES
    ('Emmanuel', 'Bruno'),
    ('Robert', 'Robert'),
    ('Paul', 'Langevin'),
    ('Valérie', 'Gillot');

-- FEED GRADE
INSERT INTO GRADE (NAME) VALUES
    ('Informatique'),
    ('Sciences de l ingénieur'),
    ('Littéraire'),
    ('Biologie');

INSERT INTO STUDENT (FIRSTNAME, LASTNAME, ID_GRADE) VALUES
    ('Adam', 'Chareyre', 1),
    ('Van-Baptiste', 'Nguyen', 1),
    ('Hafsaoui', 'Théo', 1);

INSERT INTO MODULE (NAME, ID_GRADE) VALUES
    ('I131 - Crypto', 1),
    ('I112 - Apprentissage', 1);

INSERT INTO SESSION (BEGIN, FINISH, ID_MODULE, ID_INSTRUCTOR, ID_ROOM, SESSION_TYPE) VALUES
    ('2020-01-01 08:30:00', '2020-01-01 10:30:00', 1, 2, 2, 'CM'),
    ('2020-01-02 13:30:00', '2020-01-02 18:30:00', 1, 2, 2, 'TD');

INSERT INTO SESSION_INSTRUCTOR (ID_SESSION, ID_INSTRUCTOR) VALUES
    (1, 1),
    (2, 2);

INSERT INTO STUDENT_CONNECTION (ID_STUDENT, MAIL, HASH_PASSWORD) VALUES
    (1, 'adam.chareyre.1999@gmail.com', '07123e1f482356c415f684407a3b8723e10b2cbbc0b8fcd6282c49d37c9c1abc'),
    (2, 'vanbaptiste.nguyen@gmail.com', '07123e1f482356c415f684407a3b8723e10b2cbbc0b8fcd6282c49d37c9c1abc'),
    (3, 'theo.hafsaoui@gmail.com', '07123e1f482356c415f684407a3b8723e10b2cbbc0b8fcd6282c49d37c9c1abc');