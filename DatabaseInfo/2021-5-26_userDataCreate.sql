-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: userData
-- ------------------------------------------------------
-- Server version	8.0.25-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accountCreation`
--

DROP TABLE IF EXISTS `accountCreation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accountCreation` (
  `idaccountCreation` int NOT NULL AUTO_INCREMENT,
  `creationDateTime` datetime NOT NULL,
  `fk_user` varchar(4) NOT NULL,
  `userCreatedBy` varchar(45) NOT NULL,
  `fk_userRole` varchar(45) NOT NULL,
  PRIMARY KEY (`idaccountCreation`),
  KEY `fk_userCreated_idx` (`fk_user`),
  CONSTRAINT `fk_userCreated` FOREIGN KEY (`fk_user`) REFERENCES `userTable` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountCreation`
--

LOCK TABLES `accountCreation` WRITE;
/*!40000 ALTER TABLE `accountCreation` DISABLE KEYS */;
/*!40000 ALTER TABLE `accountCreation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accountDeleted`
--

DROP TABLE IF EXISTS `accountDeleted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accountDeleted` (
  `idaccountDeleted` int NOT NULL AUTO_INCREMENT,
  `userID` varchar(4) NOT NULL,
  `username` varchar(45) NOT NULL,
  `userDeletedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`idaccountDeleted`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountDeleted`
--

LOCK TABLES `accountDeleted` WRITE;
/*!40000 ALTER TABLE `accountDeleted` DISABLE KEYS */;
INSERT INTO `accountDeleted` VALUES (1,'K037','kali','app2');
/*!40000 ALTER TABLE `accountDeleted` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logins`
--

DROP TABLE IF EXISTS `logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logins` (
  `idlogins` int NOT NULL AUTO_INCREMENT,
  `fk_userID` varchar(4) NOT NULL,
  `loginDateTime` datetime NOT NULL,
  `loginFrom` varchar(45) NOT NULL,
  PRIMARY KEY (`idlogins`),
  KEY `fk_userLogin_idx` (`fk_userID`),
  CONSTRAINT `fk_userLogin` FOREIGN KEY (`fk_userID`) REFERENCES `userTable` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logins`
--

LOCK TABLES `logins` WRITE;
/*!40000 ALTER TABLE `logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passwordChange`
--

DROP TABLE IF EXISTS `passwordChange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passwordChange` (
  `idpasswordChange` int NOT NULL AUTO_INCREMENT,
  `fk_userPWChanged` varchar(4) NOT NULL,
  `oldPassword` varchar(45) NOT NULL,
  `changeDate` datetime NOT NULL,
  `pwChangedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`idpasswordChange`),
  KEY `fk_pwuser_idx` (`fk_userPWChanged`),
  CONSTRAINT `fk_pwuser` FOREIGN KEY (`fk_userPWChanged`) REFERENCES `userTable` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passwordChange`
--

LOCK TABLES `passwordChange` WRITE;
/*!40000 ALTER TABLE `passwordChange` DISABLE KEYS */;
/*!40000 ALTER TABLE `passwordChange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roleUpdates`
--

DROP TABLE IF EXISTS `roleUpdates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roleUpdates` (
  `idroleUpdates` int NOT NULL AUTO_INCREMENT,
  `fk_userID` varchar(4) NOT NULL,
  `fk_previousRole` int NOT NULL,
  `fk_newRole` int NOT NULL,
  `roleChangeDate` datetime NOT NULL,
  PRIMARY KEY (`idroleUpdates`),
  KEY `fk_oldRole_idx` (`fk_previousRole`),
  KEY `fk_newRole_idx` (`fk_newRole`),
  KEY `fk_userID_idx` (`fk_userID`),
  CONSTRAINT `fk_newRole` FOREIGN KEY (`fk_newRole`) REFERENCES `userRoles` (`iduserRoles`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_oldRole` FOREIGN KEY (`fk_previousRole`) REFERENCES `userRoles` (`iduserRoles`),
  CONSTRAINT `fk_userID` FOREIGN KEY (`fk_userID`) REFERENCES `userTable` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roleUpdates`
--

LOCK TABLES `roleUpdates` WRITE;
/*!40000 ALTER TABLE `roleUpdates` DISABLE KEYS */;
/*!40000 ALTER TABLE `roleUpdates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userRoles`
--

DROP TABLE IF EXISTS `userRoles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userRoles` (
  `iduserRoles` int NOT NULL AUTO_INCREMENT,
  `userRoleName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`iduserRoles`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userRoles`
--

LOCK TABLES `userRoles` WRITE;
/*!40000 ALTER TABLE `userRoles` DISABLE KEYS */;
INSERT INTO `userRoles` VALUES (1,'manager');
/*!40000 ALTER TABLE `userRoles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userTable`
--

DROP TABLE IF EXISTS `userTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userTable` (
  `userID` varchar(4) NOT NULL,
  `username` varchar(45) NOT NULL,
  `fullName` varchar(45) NOT NULL,
  `userPassword` varchar(45) NOT NULL,
  `fk_userRole` int DEFAULT NULL,
  `createdBy` varchar(45) NOT NULL,
  `updatedBy` varchar(45) NOT NULL,
  `loggedIn` tinyint NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_userRole_idx` (`fk_userRole`),
  CONSTRAINT `fk_userRole` FOREIGN KEY (`fk_userRole`) REFERENCES `userRoles` (`iduserRoles`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userTable`
--

LOCK TABLES `userTable` WRITE;
/*!40000 ALTER TABLE `userTable` DISABLE KEYS */;
/*!40000 ALTER TABLE `userTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usernameChange`
--

DROP TABLE IF EXISTS `usernameChange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usernameChange` (
  `idusernameChange` int NOT NULL AUTO_INCREMENT,
  `fk_usernameChanged` varchar(4) NOT NULL,
  `oldUsername` varchar(45) NOT NULL,
  `dateTimeChanged` datetime NOT NULL,
  `usernameChangedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`idusernameChange`),
  KEY `fk_usernameChange_idx` (`fk_usernameChanged`),
  CONSTRAINT `fk_usernameChange` FOREIGN KEY (`fk_usernameChanged`) REFERENCES `userTable` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usernameChange`
--

LOCK TABLES `usernameChange` WRITE;
/*!40000 ALTER TABLE `usernameChange` DISABLE KEYS */;
/*!40000 ALTER TABLE `usernameChange` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-26 19:01:04
