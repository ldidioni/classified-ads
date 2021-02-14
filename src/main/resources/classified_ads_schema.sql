CREATE DATABASE classified_ads;
USE classified_ads;

DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS ads;
DROP TABLE IF EXISTS photos;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS ads_tags;

CREATE TABLE users (
    id INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE,
    password_hash CHAR(60),
    phone_number VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    rating FLOAT(3, 2) DEFAULT 0,
    ratings_nb INT(6) DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE roles (
    id int(11) NOT NULL auto_increment,
	name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE users_roles (
    id int(11) NOT NULL auto_increment,
	user_id int(11) NOT NULL,
	role_id int(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE categories (
    id int(11) NOT NULL auto_increment,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ads (
    id int(11) NOT NULL auto_increment,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    asked_price FLOAT(10, 2),
    seller_id int(11) NOT NULL,
    category_id int(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE photos (
    id int(11) NOT NULL auto_increment,
	ad_id int(11) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ad_id) REFERENCES ads(id) ON DELETE CASCADE
);

CREATE TABLE tags (
    id int(11) NOT NULL auto_increment,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ads_tags (
    id int(11) NOT NULL auto_increment,
    ad_id int(11) NOT NULL,
    tag_id int(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ad_id) REFERENCES ads(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);