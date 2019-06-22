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

-- ---------------
--   Add data   --
-- ---------------
INSERT INTO users VALUES 
	(1, 'Bob', 'test', 'valid@email.com', 'apikey', CURRENT_TIMESTAMP),
    (2, 'Bobby', 'test', 'valid@email.nl', 'apikey1', CURRENT_TIMESTAMP),
    (3, 'Bobbington', 'test', 'valid@email.net', 'apikey2', CURRENT_TIMESTAMP),
    (4, 'Jan', 'test', 'valid@email.ru', 'apikey3', CURRENT_TIMESTAMP);

INSERT INTO followers VALUES
	(1, 2),
    (1, 3),
    (2, 1),
    (2, 4),
    (3, 1),
    (3, 4);

INSERT INTO events VALUES
	(1, 'Bobs feest', 'Bobs verjaardagsfeest', 'img-url', 53.232882, 6.540572, 0, '2019-05-31 08:15:15', '2019-08-31 08:15:15', 1, CURRENT_TIMESTAMP),
    (2, 'Bobs feest', 'Vier de verjaardag van bobs kat', 'img-url', 53.232904, 6.540692, 0, '2019-05-31 08:15:15', '2019-08-31 08:15:15', 1, CURRENT_TIMESTAMP),
    (3, 'Bobbys feestje', 'Bobby geeft een feestje', 'img-url', 53.237218, 6.556211, 0, '2019-05-31 08:15:15', '2019-08-31 08:15:15', 2, CURRENT_TIMESTAMP),
    (4, 'Bobbingtons trouwerij', 'Kom naar de trouwerij van de Bobbingtons.', 'img-url', 53.212122, 6.549218, 0, '2019-05-31 08:15:15', '2019-08-31 08:15:15', 1, CURRENT_TIMESTAMP),
    (5, 'Meldrinken bij Jan', 'Iedereen is welkom om melk te komen drinken bij Jan', 'img', 53.195524, 6.598142, 0, '2019-05-31 08:15:15', '2019-08-31 08:15:15', 3, CURRENT_TIMESTAMP);
    
