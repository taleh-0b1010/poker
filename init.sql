CREATE TABLE IF NOT EXISTS game_result (
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    game_result VARCHAR NOT NULL,
    hand VARCHAR NOT NULL,
    bet VARCHAR NOT NULL
    );