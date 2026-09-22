CREATE TABLE recipes
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    instructions TEXT         NOT NULL,
    prep_time    INTEGER,
    author_id    BIGINT       NOT NULL,
    category_id  BIGINT,
    created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_recipes_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_recipes_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
);

CREATE INDEX idx_recipes_author_id ON recipes (author_id);
CREATE INDEX idx_recipes_category_id ON recipes (category_id);