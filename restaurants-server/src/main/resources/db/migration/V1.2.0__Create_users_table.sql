CREATE TABLE IF NOT EXISTS users
(
    id            uuid PRIMARY KEY,
    login         text NOT NULL UNIQUE,
    password      text NOT NULL,
    first_name    text, -- опционально
    last_name     text, -- опционально
    role          text NOT NULL DEFAULT 'USER', -- важно для авторизации!
    created_at    timestamp DEFAULT now(),
    enabled       boolean DEFAULT true -- важно для Spring Security
);

-- Создаем индексы для быстрого поиска
CREATE UNIQUE INDEX IF NOT EXISTS users_login_idx ON users (login);