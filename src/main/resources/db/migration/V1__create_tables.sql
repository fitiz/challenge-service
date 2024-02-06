CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--- challenge.sql
CREATE TABLE challenge
(
    id          UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    start_date  TIMESTAMP    NOT NULL,
    finish_date TIMESTAMP    NOT NULL,
    location_id INT          NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_challenge_id ON challenge (id);
CREATE INDEX idx_challenge_dates ON challenge (start_date, finish_date);

--- player.sql
CREATE TABLE participant
(
    user_id      UUID         NOT NULL PRIMARY KEY,
    username     VARCHAR(255) NOT NULL,
    challenge_id UUID         NULL     DEFAULT NULL,
    location_id  INT          NOT NULL,
    points       INT          NOT NULL DEFAULT 0,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (challenge_id) REFERENCES challenge (id)
);

CREATE INDEX idx_player_user_id ON participant (user_id);
CREATE INDEX idx_player_challenge_id ON participant (challenge_id);

--- leaderboard.sql
CREATE TABLE leaderboard
(
    id             UUID      NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    challenge_id   UUID      NOT NULL,
    participant_id UUID      NOT NULL,
    steps          INTEGER   NOT NULL DEFAULT 0,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (challenge_id) REFERENCES challenge (id),
    FOREIGN KEY (participant_id) REFERENCES participant (user_id)
);

CREATE INDEX idx_leaderboard_id ON leaderboard (id);
CREATE INDEX idx_leaderboard_challenge_id ON leaderboard (challenge_id);
CREATE INDEX idx_leaderboard_participant_id ON leaderboard (participant_id);