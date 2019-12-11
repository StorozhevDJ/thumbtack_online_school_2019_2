DROP DATABASE IF EXISTS ttschool;
CREATE DATABASE `ttschool`; 
USE `ttschool`;

CREATE TABLE school (`id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `year` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX (`name`, `year`)) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `group` (  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `room` VARCHAR(45) NOT NULL,
  FOREIGN KEY (`id`) REFERENCES `school` (id) ON DELETE CASCADE) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE subject (`id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  KEY (`id`)
  #FOREIGN KEY (`id`) REFERENCES `group` (id) ON DELETE CASCADE
  ) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE trainee (`id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `rating` INT NOT NULL,
  KEY firstName(firstName),
  KEY lastName(lastName),
  KEY (`id`)
  #FOREIGN KEY (`id`) REFERENCES `group` (id) ON DELETE CASCADE
  ) ENGINE=INNODB DEFAULT CHARSET=utf8;