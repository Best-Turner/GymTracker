INSERT INTO clients(name, gender, birth_date)
VALUES ('Александ', 'FEMALE', '1990-01-25'),
       ('Петр', 'FEMALE', '2001-05-15'),
       ('Инна', 'MALE', '2005-08-30'),
       ('Михаил', 'FEMALE', '1986-07-21'),
       ('Анна', 'MALE', '1991-05-01');


INSERT INTO coaches (name, specialization, hire_date, is_active)
VALUES ('Денис', 'GENERAL_PHYSICAL_TRAINING', '2020-01-01', true),
       ('Ирина', 'ENDURANCE', '2025-07-07', true),
       ('Юрий', 'STRENGTH_TRAINING', '2019-02-15', true);

INSERT INTO workouts(date, duration, type, client_id, coach_id)
VALUES (current_date, 5220, 'STRENGTH_TRAINING', 1, 3),
       ('2025-06-28', 1980, 'ENDURANCE_TRAINING', 1, 2),
       ('2025-06-26', 1080, 'STRENGTH_TRAINING', 1, 3),
       ('2025-07-02', 3600, 'TACTICAL_TRAINING', 2, 3),
       ('2025-07-01', 5220, ' GENERAL_PHYSICAL_TRAINING', 2, 2),
       ('2025-07-03', 540, 'TECHNICAL_TRAINING', 2, 1),
       ('2025-06-02', 1548, 'GENERAL_PHYSICAL_TRAINING', 3, 1),
       ('2025-06-15', 720, 'TECHNICAL_TRAINING', 3, 1),
       ('2025-06-18', 3708, 'SPECIAL_PHYSICAL_TRAINING', 3, 3),
       ('2025-05-25', 5400, 'ENDURANCE_TRAINING', 4, 3),
       ('2025-05-26', 1692, 'SPEED_STRENGTH_QUALITIES', 4, 1),
       ('2025-05-27', 5148, 'RECOVERY_TRAINING', 5, 1);



INSERT INTO exercises (name, muscle_group)
VALUES ('Упражнения на грудные мышцы', 'CHEST'),
       ('Упражнения на спина', 'BACK'),
       ('Упражнения на плечи', 'SHOULDERS'),
       ('Упражнения на бицепсы', 'BICEPS'),
       ('Упражнения на трицепсы', 'TRICEPS'),
       ('Упражнения на предплечья', 'FOREARMS'),
       ('Упражнения на пресс', 'ABS'),
       ('Упражнения на четырёхглавую мышцу бедра', 'QUADRICEPS'),
       ('Упражнения на заднюю поверхность бедра', 'HAMSTRINGS'),
       ('Упражнения на ягодичные мышцы', 'GLUTES'),
       ('Упражнения на икры', 'CALVES'),
       ('Упражнения на шеи', 'NECK');

INSERT INTO exercise_sets(weight, reps, workout_id, exercise_id)
VALUES (105.00, 12, 1, 1),
       (105.00, 10, 1, 1),
       (100.00, 10, 1, 1),
       (25.00, 20, 4, 2),
       (25.00, 18, 4, 2),
       (25.00, 17, 4, 2),
       (15.00, 17, 2, 4),
       (15.00, 17, 2, 4),
       (15.00, 17, 2, 4),
       (14.00, 17, 2, 4),
       (35.00, 5, 3, 5),
       (35.00, 4, 3, 5),
       (0.00, 1, 5, 12),
       (0.00, 10, 6, 9),
       (0.00, 1, 7, 11),
       (1.00, 15, 8, 2),
       (1.50, 13, 8, 2),
       (1.00, 15, 8, 2),
       (1.00, 10, 8, 2),
       (10.00, 13, 9, 4),
       (10.00, 11, 9, 4),
       (23.00, 23, 9, 8),
       (23.00, 20, 9, 8),
       (20.00, 19, 9, 8),
       (32.00, 25, 10, 11),
       (32.00, 26, 10, 11),
       (0.00, 26, 11, 7),
       (20.00, 26, 11, 1);


INSERT INTO workout_exercise(workout_id, exercise_id)
VALUES (1, 1),
       (2, 4),
       (3, 5),
       (4, 2),
       (5, 12),
       (6, 9),
       (7, 11),
       (8, 2),
       (9, 4),
       (9, 8),
       (10, 11),
       (11, 7),
       (11, 1);
