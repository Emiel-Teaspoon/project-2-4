-- ---------------------
-- Reset the database --
-- ---------------------
DROP DATABASE IF EXISTS eventmap;
CREATE DATABASE IF NOT EXISTS eventmap;
USE eventmap;
SET SQL_SAFE_UPDATES = 0;

-- -----------------
-- Reset the user --
-- -----------------
DROP USER IF EXISTS 'eventmap'@'localhost';
CREATE USER 'eventmap'@'localhost' IDENTIFIED BY 'J9Y1;=&TkbB+';
GRANT ALL PRIVILEGES ON eventmap.* TO 'eventmap'@'localhost';
ALTER USER 'eventmap'@'localhost' IDENTIFIED WITH mysql_native_password BY 'J9Y1;=&TkbB+';
FLUSH PRIVILEGES;

-- -----------------
--  Create tables --
-- -----------------
CREATE TABLE users (
	user_id INT AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    api_key VARCHAR(255) NOT NULL,
    creation_datetime DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(user_id)
);

CREATE TABLE followers (
    user INT NOT NULL,
    follower INT NOT NULL,
    PRIMARY KEY(user, follower),
    FOREIGN KEY(user) REFERENCES users(user_id),
    FOREIGN KEY(follower) REFERENCES users(user_id)
);

CREATE TABLE events (
	event_id INT AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    image VARCHAR(255),
    latitude DECIMAL(8, 6) NOT NULL,
    longitude DECIMAL (9, 6) NOT NULL,
    attendees INT NOT NULL,
    event_start_datetime DATETIME,
    event_end_datetime DATETIME,
    event_owner INT NOT NULL,
    creation_datetime DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(event_id),
    FOREIGN KEY(event_owner) REFERENCES users(user_id)
);

-- ---------------
-- Create rules --
-- ---------------
-- ALTER TABLE users ADD UNIQUE INDEX username_unique (username ASC);
-- ALTER TABLE users ADD UNIQUE INDEX email_unique (email ASC);
-- ALTER TABLE users ADD UNIQUE INDEX api_key_unique (api_key ASC);
-- ALTER TABLE events ADD UNIQUE INDEX title_unique (title ASC);

-- ---------------
--   Add data   --
-- ---------------
INSERT INTO users VALUES 
	(1, 'Bob', 'test', 'valid@email.com', 'apikey', CURRENT_TIMESTAMP),
    (2, 'Bobby', 'test', 'valid@email.nl', 'apikey1', CURRENT_TIMESTAMP),
    (3, 'Bobbington', 'test', 'valid@email.net', 'apikey2', CURRENT_TIMESTAMP);

INSERT INTO followers VALUES
	(1, 2),
    (1, 3),
    (2, 1);

INSERT INTO events VALUES
	(1, 'Descriptive title', 'Concise description', 'img-url', 53.241081, 6.533918, 0, '2019-05-31 08:15:15', '2019-08-31 08:15:15', 1, CURRENT_TIMESTAMP),
    (2, 'Deceptive title', 'Longwinded description', 'img-url', 53.241081, 6.533918, 0, '2019-05-31 08:15:15', '2019-08-31 08:15:15', 2, CURRENT_TIMESTAMP),
    (3, 'Title', 'Desc', 'img', 52.525252, 6.666777, 0, '2019-05-31 08:15:15', '2019-08-31 08:15:15', 3, CURRENT_TIMESTAMP);
    
