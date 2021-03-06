DROP DATABASE IF EXISTS `concert`;
CREATE DATABASE `concert`;
USE `concert`;


CREATE TABLE users (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `login` VARCHAR(45) NOT NULL,
    `firstName` VARCHAR(45) NOT NULL,
    `lastName` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    UNIQUE KEY (`login`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE sessions (
    `login` VARCHAR(45) NOT NULL UNIQUE KEY,
    `token` VARCHAR(45) NOT NULL KEY
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE songs (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `songName` VARCHAR(45) NOT NULL,
    `singer` VARCHAR(45) NOT NULL,
    `length` INT NOT NULL,
    `userLogin` VARCHAR(45) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE comments (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `songId` INT NOT NULL,
    `comment` VARCHAR(45) NOT NULL,
    `author` INT NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE ratings (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `songId` INT NOT NULL,
    `rating` INT NOT NULL,
    `user` VARCHAR(45) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE composer (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `songId` INT NOT NULL,
    `composer` VARCHAR(45) NOT NULL,
    UNIQUE KEY (`songId`, `composer`),
    FOREIGN KEY (`songId`) REFERENCES `songs`(id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE author (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `author` VARCHAR(45) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;