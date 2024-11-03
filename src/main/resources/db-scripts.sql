CREATE DATABASE `india_db`;

CREATE TABLE `india_db`.users (
  `userId` int NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
);

INSERT INTO india_db.users(city, name) VALUES('Bangalore', 'User-1');
INSERT INTO india_db.users(city, name) VALUES('Delhi', 'User-2');




CREATE DATABASE `china_db`;

CREATE TABLE `china_db`.users (
  `userId` int NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
);

INSERT INTO china_db.users(city, name) VALUES('Wuhan', 'User-3');
INSERT INTO china_db.users(city, name) VALUES('Beijing', 'User-4');
