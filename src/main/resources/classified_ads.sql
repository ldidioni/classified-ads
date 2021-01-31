CREATE DATABASE classified_ads;
USE classified_ads;

DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS ads;
DROP TABLE IF EXISTS photos;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS categories;

CREATE TABLE users (
    id INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE,
    password_hash CHAR(60),
    phone_number VARCHAR(255),
    email_address VARCHAR(255) NOT NULL,
    rating FLOAT(3, 2) DEFAULT 0,
    ratings_nb INT(6) DEFAULT 0,
    PRIMARY KEY (id)
);

INSERT INTO users (username, password_hash, phone_number, email_address) VALUES
    ('admin', '$2y$10$ylvbwPly0hb9E1DTENI6Qe3/NcTasAL66mG.KpfgbIfjrugC5dqiq', '+41 76 777 07 07', 'admin@example.com'),
    ('boss', '$2y$10$D.IdbWY0LC6jiVpWL9d57uqT.AxAMx7mpybhm4xtdkYHf33X7Pbju', '+41 76 333 03 03', 'boss@example.com'),
    ('greg', '$2y$10$coBmsD7zmWQxjI69q0QDeODXxUbkkCueYTvUEYKN5riJFgXTHh5zm', '+41 76 222 02 02', 'greg@example.com'),
    ('mike', '$2y$10$nT7AKqZ1vW6aFUayVly6.esIY8wmzCPOi9nAufJs0aHid9sTRKGi2', '+41 76 111 01 01', 'mike@example.com'),
    ('bob', '$2y$10$tPnH5J5LQ/94.sVtPUdDz.VCGFci5Q6F3RgSxo02e4/HmG7tGeHya', '+41 76 444 04 04', 'bob@example.com');

CREATE TABLE roles (
    id int(11) NOT NULL auto_increment,
	role VARCHAR(255),
    PRIMARY KEY (id)
);

INSERT INTO roles (role) VALUES
     ('USER'),
     ('ADMIN');

CREATE TABLE user_roles (
    id int(11) NOT NULL auto_increment,
	user_id int(11) NOT NULL,
	role_id int(11) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id)
    ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT FK_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO user_roles (user_id, role_id) VALUES
     (1, 1),
     (1, 2),
     (2, 1),
     (2, 2),
     (3, 1),
     (4, 1),
     (5, 1);

CREATE TABLE categories (
    id int(11) NOT NULL auto_increment,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO categories (name) VALUES
    ('Véhicules'),
    ('Biens immobiliers'),
    ('Jeux pour enfants');

CREATE TABLE ads (
    id int(11) NOT NULL auto_increment,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    asked_price FLOAT(10, 2),
    seller_id int(11) NOT NULL,
    category_id int(11) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_adseller FOREIGN KEY (seller_id) REFERENCES users(id)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_adcategory FOREIGN KEY (category_id) REFERENCES categories(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO ads (title, description, asked_price, seller_id, category_id) VALUES
    ('Vends BMW serie 3', 'Je vends ma voiture chérie de la marque à l\'hélice.', 32000, 3, 1),
    ('Vends Alfa Giulia', 'Je vends mon pure sang italien aux 500 chevaux.', 58000, 4, 1),
    ('Vends duplex vue lac Léman', 'Je vends mon appartement en duplex avec vue spectaculaire.', 1450000, 4, 2),
    ('Vends chalet de montagne', 'Je vends mon chalet familial situé au pied des pistes.', 825000, 5, 2);

CREATE TABLE photos (
    id int(11) NOT NULL auto_increment,
	ad_id int(11) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_photoad FOREIGN KEY (ad_id) REFERENCES ads(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE tags (
    id int(11) NOT NULL auto_increment,
	ad_id int(11) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_tagad FOREIGN KEY (ad_id) REFERENCES ads(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);