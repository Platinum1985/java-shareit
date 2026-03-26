CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS itemRequests (
    id SERIAL PRIMARY KEY,
    description TEXT,
    requestorId INT,
    created TIMESTAMP,
    FOREIGN KEY (requestorId) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    available BOOLEAN,
    ownerId INT,
    itemRequestId INT,
    FOREIGN KEY (ownerId) REFERENCES users(id),
    FOREIGN KEY (itemRequestId) REFERENCES itemRequests(id)
);


CREATE TABLE IF NOT EXISTS bookings (
    id SERIAL PRIMARY KEY,
    start_time TIMESTAMP WITHOUT TIME ZONE,
    end_time TIMESTAMP WITHOUT TIME ZONE,
    itemId INT,
    userId INT,
    status VARCHAR(255),
    FOREIGN KEY (itemId) REFERENCES items(id),
    FOREIGN KEY (userId) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    text TEXT,
    itemId INT,
    authorName VARCHAR(255),
    FOREIGN KEY (itemId) REFERENCES items(id),
    created TIMESTAMP WITHOUT TIME ZONE
);