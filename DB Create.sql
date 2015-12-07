CREATE DATABASE  IF NOT EXISTS `_445team2`;
USE `_445team2`;

-- DROP TABLE IF EXISTS `Agenda`;
CREATE TABLE `Agenda` (
  `agendaID` int(11) NOT NULL,
  `title` varchar(45) NOT NULL,
  `fkUser` int(11) NOT NULL,
  PRIMARY KEY (`agendaID`,`fkUser`),
  KEY `userID_idx` (`fkUser`)
);


-- DROP TABLE IF EXISTS `Task`;
CREATE TABLE `Task` (
  `taskID` int(11) NOT NULL,
  `title` varchar(45) NOT NULL,
  `timestamp` date NOT NULL,
  `completed` int(11) NOT NULL,
  `difficulty` varchar(45) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `timeCompleted` date DEFAULT NULL,
  `notes` longtext,
  `agenda_agendaID` int(11) NOT NULL,
  PRIMARY KEY (`taskID`),
  KEY `agendaID_idx` (`agenda_agendaID`)
);


-- DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `UserID` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`UserID`)
);