CREATE TABLE IF NOT EXISTS coaches
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(50)  NOT NULL,
    specialization VARCHAR(128) NOT NULL,
    hire_date      DATE         NOT NULL,
    is_active      BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS workouts
(
    id       BIGSERIAL PRIMARY KEY,
    date     DATE         NOT NULL,
    duration INTERVAL     NOT NULL,
    type     VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS exercises
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128) NOT NULL,
    muscleGroup VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS exercise_sets
(
    id     BIGSERIAL PRIMARY KEY,
    weight NUMERIC(5, 2),
    reps   INTEGER NOT NULL
);
