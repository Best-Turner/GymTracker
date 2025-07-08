ALTER TABLE workouts
    ADD COLUMN client_id BIGINT
        REFERENCES clients (id) ON DELETE CASCADE
            ON UPDATE CASCADE NOT NULL;


ALTER TABLE workouts
    ADD COLUMN coach_id INTEGER REFERENCES coaches (id) NULL;

CREATE TABLE workout_exercise
(
    workout_id  BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    FOREIGN KEY (workout_id) REFERENCES workouts (id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercises (id) ON DELETE CASCADE
);


ALTER TABLE exercise_sets
    ADD COLUMN workout_id BIGINT
        REFERENCES workouts (id)
            ON DELETE CASCADE
        NOT NULL;


ALTER TABLE exercise_sets
    ADD COLUMN exercise_id BIGINT REFERENCES exercises (id) NULL;


