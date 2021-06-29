CREATE DATABASE  IF NOT EXISTS `programData` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `programData`;
-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: programData
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
-- Table structure for table `clubSubject`
--

DROP TABLE IF EXISTS `clubSubject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clubSubject` (
  `idclubSubject` int NOT NULL AUTO_INCREMENT,
  `clubSubject` varchar(45) NOT NULL,
  PRIMARY KEY (`idclubSubject`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clubSubject`
--

LOCK TABLES `clubSubject` WRITE;
/*!40000 ALTER TABLE `clubSubject` DISABLE KEYS */;
INSERT INTO `clubSubject` VALUES (1,'Academic'),(2,'Entertainment'),(3,'Social'),(4,'Religious and Spiritual'),(5,'Arts'),(6,'Honor Societies'),(7,'Cultural'),(8,'Service'),(9,'Athletic');
/*!40000 ALTER TABLE `clubSubject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clubs`
--

DROP TABLE IF EXISTS `clubs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clubs` (
  `clubID` int NOT NULL AUTO_INCREMENT,
  `clubName` varchar(45) NOT NULL,
  `fk_clubSubject` int NOT NULL,
  `clubDescription` varchar(100) NOT NULL,
  PRIMARY KEY (`clubID`),
  KEY `fk_clubs_clubSubject_idx` (`fk_clubSubject`),
  CONSTRAINT `fk_clubs_clubSubject` FOREIGN KEY (`fk_clubSubject`) REFERENCES `clubSubject` (`idclubSubject`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clubs`
--

LOCK TABLES `clubs` WRITE;
/*!40000 ALTER TABLE `clubs` DISABLE KEYS */;
INSERT INTO `clubs` VALUES (5,'Physics, Physics, Physics',1,'\"Physics! Physics, physics, physics! Physics! I hope one of you is getting all this down.\"'),(6,'Pre-Med and Nursing Club',1,'\"That wasn’t very clever running around outside, was it?\"'),(7,'Believe In HER.',4,'\"If I believe in one thing… just one thing… I believe in her!\"'),(8,'Freedom for OODkind',8,'\"The circle must be broken so that we can sing.\"'),(9,'Fans of the Doctor',2,'\"He burns at the center of time and he can see the turn of the universe... and he is wonderful.\"'),(10,'Don\'t Blink',7,'Whatever you do, don\'t blink.');
/*!40000 ALTER TABLE `clubs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `displayClubsAndStudents`
--

DROP TABLE IF EXISTS `displayClubsAndStudents`;
/*!50001 DROP VIEW IF EXISTS `displayClubsAndStudents`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `displayClubsAndStudents` AS SELECT 
 1 AS `clubName`,
 1 AS `clubDescription`,
 1 AS `clubSubject`,
 1 AS `studentID`,
 1 AS `studentFirstName`,
 1 AS `studentLastName`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `studentID` int NOT NULL AUTO_INCREMENT,
  `studentFirstName` varchar(45) NOT NULL,
  `studentLastName` varchar(45) NOT NULL,
  `studentBirthdate` date NOT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Rose','Tyler','1998-04-07'),(2,'Donna','Noble','1999-10-22'),(3,'John','Smith','1998-06-03'),(4,'Martha','Jones','1999-02-27'),(5,'Jane','Doe','1997-12-31'),(6,'Bill','Potts','2000-07-13');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentsInClubs`
--

DROP TABLE IF EXISTS `studentsInClubs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentsInClubs` (
  `idstudentsInClubs` int NOT NULL AUTO_INCREMENT,
  `fk_student` int NOT NULL,
  `fk_club` int NOT NULL,
  PRIMARY KEY (`idstudentsInClubs`),
  KEY `fk_studentsInClubs_students_idx` (`fk_student`),
  KEY `fk_studentsInClubs_clubs_idx` (`fk_club`),
  CONSTRAINT `fk_studentsInClubs_clubs` FOREIGN KEY (`fk_club`) REFERENCES `clubs` (`clubID`),
  CONSTRAINT `fk_studentsInClubs_students` FOREIGN KEY (`fk_student`) REFERENCES `students` (`studentID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentsInClubs`
--

LOCK TABLES `studentsInClubs` WRITE;
/*!40000 ALTER TABLE `studentsInClubs` DISABLE KEYS */;
INSERT INTO `studentsInClubs` VALUES (1,1,8),(2,1,9),(3,2,8),(4,2,9),(5,2,7),(6,3,7),(7,3,5),(8,3,6),(9,4,6),(10,4,9),(11,5,6),(12,5,5),(13,5,7),(14,1,10);
/*!40000 ALTER TABLE `studentsInClubs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'programData'
--

--
-- Dumping routines for database 'programData'
--
/*!50003 DROP PROCEDURE IF EXISTS `addNewClub` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewClub`(
IN clubname varchar(45),
IN clubdesc varchar(100),
IN clubsubject varchar(45)
)
BEGIN
insert into clubs(clubName, fk_clubSubject, clubDescription) values(clubname, (select idclubSubject from clubSubject where clubsubject = clubSubject.clubSubject), clubdesc);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addNewClubSubject` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewClubSubject`(
IN clubsubject varchar(45)
)
BEGIN
insert into clubSubject(clubSubject) values(clubsubject);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addNewStudent` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewStudent`(
IN studentFirstName varchar(45),
IN studentLastName varchar(45),
IN studentBirthdate date
)
BEGIN
insert into students(studentFirstName, studentLastName, studentBirthdate) values(studentFirstName, studentLastName, studentBirthdate);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addStudentToClub` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addStudentToClub`(
IN studentFN varchar(45),
IN studentLN varchar(45),
IN club varchar(45)
)
BEGIN
insert into studentsInClubs(fk_student, fk_club) values ((select studentID from students where students.studentFirstName = studentFN and students.studentLastName = studentLN),
(select clubID from clubs where clubs.clubName = club));
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `whichClubsAreAStudentIn` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `whichClubsAreAStudentIn`(
IN firstname varchar(45),
IN lastname varchar(45)
)
BEGIN
select d.clubName, d.clubDescription, d.clubSubject from displayClubsAndStudents d where d.studentFirstName = firstname and d.studentLastName = lastname;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `whichStudentsAreInAClub` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `whichStudentsAreInAClub`(
IN clubName varchar(45)
)
BEGIN
select d.studentFirstName, d.studentLastName from displayClubsAndStudents d
where d.clubName = clubName;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `whoWhichClubs` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `whoWhichClubs`(
IN firstname varchar(45),
IN lastname varchar(45)
)
BEGIN
select students.studentFirstName, students.studentLastName, b.clubName, b.clubDescription, b.clubSubject
 from students inner join (select a.clubName, a.clubDescription, a.clubSubject, studentsInClubs.fk_student from studentsInClubs inner join (select clubs.clubID, clubs.clubName, clubs.clubDescription, clubSubject.clubSubject
 from clubs inner join clubSubject where clubs.fk_clubSubject = clubSubject.idclubSubject) a where a.clubID = studentsInClubs.fk_club) b
where b.fk_student = students.studentID and students.studentFirstName = firstname and students.studentLastName = lastname order by students.studentLastName;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `displayClubsAndStudents`
--

/*!50001 DROP VIEW IF EXISTS `displayClubsAndStudents`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `displayClubsAndStudents` AS select `three`.`clubName` AS `clubName`,`three`.`clubDescription` AS `clubDescription`,`cs`.`clubSubject` AS `clubSubject`,`three`.`studentID` AS `studentID`,`three`.`studentFirstName` AS `studentFirstName`,`three`.`studentLastName` AS `studentLastName` from (`clubSubject` `cs` join (select `c`.`clubName` AS `clubName`,`c`.`clubDescription` AS `clubDescription`,`c`.`fk_clubSubject` AS `fk_clubSubject`,`two`.`studentID` AS `studentID`,`two`.`studentFirstName` AS `studentFirstName`,`two`.`studentLastName` AS `studentLastName` from (`clubs` `c` join (select `sic`.`fk_club` AS `fk_club`,`s`.`studentID` AS `studentID`,`s`.`studentFirstName` AS `studentFirstName`,`s`.`studentLastName` AS `studentLastName` from (`studentsInClubs` `sic` join `students` `s`) where (`s`.`studentID` = `sic`.`fk_student`)) `two`) where (`two`.`fk_club` = `c`.`clubID`)) `three`) where (`three`.`fk_clubSubject` = `cs`.`idclubSubject`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-29 15:40:02
CREATE DATABASE  IF NOT EXISTS `userData` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `userData`;
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
  `fk_user` varchar(45) NOT NULL,
  `userCreatedBy` varchar(45) NOT NULL,
  `fk_userRole` varchar(45) NOT NULL,
  PRIMARY KEY (`idaccountCreation`),
  KEY `fk_userCreated_idx` (`fk_user`),
  CONSTRAINT `fk_userCreated` FOREIGN KEY (`fk_user`) REFERENCES `userTable` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountCreation`
--

LOCK TABLES `accountCreation` WRITE;
/*!40000 ALTER TABLE `accountCreation` DISABLE KEYS */;
INSERT INTO `accountCreation` VALUES (6,'2021-06-29 14:52:42','Ioic+vWsDiyVWm7JG32FVQ==','b8wt02Aw0RAd6aWP1W+WLA==','4');
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
  `userID` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `userDeletedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`idaccountDeleted`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountDeleted`
--

LOCK TABLES `accountDeleted` WRITE;
/*!40000 ALTER TABLE `accountDeleted` DISABLE KEYS */;
INSERT INTO `accountDeleted` VALUES (1,'K037','kali','app2'),(2,'KBH1','kalihale1','app2'),(3,'k301','kali','app2'),(4,'K037','kalihale','app2'),(5,'K032','luvmydog','app2');
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
  `fk_userID` varchar(45) NOT NULL,
  `loginDateTime` datetime NOT NULL,
  `loginFrom` varchar(45) NOT NULL,
  PRIMARY KEY (`idlogins`),
  KEY `fk_userLogin_idx` (`fk_userID`),
  CONSTRAINT `fk_userLogin` FOREIGN KEY (`fk_userID`) REFERENCES `userTable` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logins`
--

LOCK TABLES `logins` WRITE;
/*!40000 ALTER TABLE `logins` DISABLE KEYS */;
INSERT INTO `logins` VALUES (15,'Ioic+vWsDiyVWm7JG32FVQ==','2021-06-29 14:52:57','app1');
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
  `fk_userPWChanged` varchar(45) NOT NULL,
  `oldPassword` varchar(45) NOT NULL,
  `changeDate` datetime NOT NULL,
  `pwChangedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`idpasswordChange`),
  KEY `fk_pwuser_idx` (`fk_userPWChanged`),
  CONSTRAINT `fk_pwuser` FOREIGN KEY (`fk_userPWChanged`) REFERENCES `userTable` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `fk_userID` varchar(45) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userRoles`
--

LOCK TABLES `userRoles` WRITE;
/*!40000 ALTER TABLE `userRoles` DISABLE KEYS */;
INSERT INTO `userRoles` VALUES (1,'manager'),(2,'estimator'),(3,'office staff'),(4,'RKZdYT6dAN6XGKzIyNEUoQ=='),(5,'RXGhth8TC1+eCqIvyC+d5w=='),(6,'HZpjlAoC0JDYbRmcRbVSlg==');
/*!40000 ALTER TABLE `userRoles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userTable`
--

DROP TABLE IF EXISTS `userTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userTable` (
  `userID` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `userPassword` varchar(45) NOT NULL,
  `userEmail` varchar(45) NOT NULL,
  `fk_userRole` int NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `updatedBy` varchar(45) NOT NULL,
  `loggedIn` tinyint NOT NULL,
  `loginAttempts` int NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `userID_UNIQUE` (`userID`),
  UNIQUE KEY `userEmail_UNIQUE` (`userEmail`),
  KEY `fk_userRole_idx` (`fk_userRole`),
  CONSTRAINT `fk_userRole` FOREIGN KEY (`fk_userRole`) REFERENCES `userRoles` (`iduserRoles`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userTable`
--

LOCK TABLES `userTable` WRITE;
/*!40000 ALTER TABLE `userTable` DISABLE KEYS */;
INSERT INTO `userTable` VALUES ('Ioic+vWsDiyVWm7JG32FVQ==','iV1Mr2abGMIQ7jclGHHT+w==','Q6RxPz0RP+1VvzX50RUzPA==','bFffyq4Vmq9+LOY0iRfIS+rPQba8H/p/a1Sc8am8W+A=',4,'b8wt02Aw0RAd6aWP1W+WLA==','none',1,0);
/*!40000 ALTER TABLE `userTable` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `userTable_AFTER_INSERT` AFTER INSERT ON `userTable` FOR EACH ROW BEGIN
insert into accountCreation(creationDateTime, fk_user, userCreatedBy, fk_userRole) values(now(), NEW.userID, NEW.createdBy, NEW.fk_userRole);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `userTable_AFTER_UPDATE` AFTER UPDATE ON `userTable` FOR EACH ROW BEGIN
IF OLD.loggedIn != NEW.loggedIn AND NEW.loggedIn = 1 THEN
	INSERT INTO logins(fk_userID, loginDateTime, loginFrom) values(OLD.userID, now(), 'app1');
END IF;
IF OLD.userPassword != NEW.userPassword THEN
	INSERT INTO passwordChange(fk_userPWChanged, oldPassword, changeDate, pwChangedBy) values (OLD.userID, OLD.userPassword, now(), OLD.userID);
END IF;
IF OLD.username != NEW.username THEN
	INSERT INTO usernameChange(fk_usernameChanged, oldUsername, dateTimeChanged, usernameChangedBy) values(OLD.userID, OLD.username, now(), OLD.userID);
END IF;
IF OLD.fk_userRole != NEW.fk_userRole THEN
	INSERT INTO roleUpdates(fk_userID, fk_previousRole, fk_newRole, roleChangeDate) values(OLD.userID, OLD.fk_userRole, NEW.fk_userRole, now());
END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `userTable_AFTER_DELETE` AFTER DELETE ON `userTable` FOR EACH ROW BEGIN
	INSERT INTO accountDeleted(userID, username, userDeletedBy) values(OLD.userID, OLD.username, 'app2');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `usernameChange`
--

DROP TABLE IF EXISTS `usernameChange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usernameChange` (
  `idusernameChange` int NOT NULL AUTO_INCREMENT,
  `fk_usernameChanged` varchar(45) NOT NULL,
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

--
-- Dumping events for database 'userData'
--

--
-- Dumping routines for database 'userData'
--
/*!50003 DROP FUNCTION IF EXISTS `changePassword` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `changePassword`(
un VARCHAR(45),
oldpass VARCHAR(45),
newpass VARCHAR(45)
) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
IF((SELECT userPassword FROM userTable WHERE username = un) = oldpass AND (SELECT loggedIn FROM userTable WHERE username = un) = 0)
THEN UPDATE userTable SET userPassword = newpass WHERE username = un;
RETURN true;
ELSE
RETURN false;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `login`(
un VARCHAR(45),
pass VARCHAR(45)
) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
DECLARE loginNums INT;
DECLARE storedPass TEXT;
DECLARE lIn INT;

SELECT loginAttempts INTO loginNums FROM userTable WHERE username = un;
SELECT userPassword INTO storedPass FROM userTable WHERE username = un;
SELECT loggedIn INTO lIn FROM userTable WHERE username = un;

IF(storedPass = pass && lIn = 0 && loginNums < 3)
THEN UPDATE userTable SET loggedIn = 1 WHERE username = un;
UPDATE userTable SET loginAttempts = 0;
RETURN true;

ELSE
UPDATE userTable SET loginAttempts = loginNums + 1 WHERE username = un;
RETURN false;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `lockedOut` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `lockedOut`()
BEGIN
SELECT userID, username FROM userTable WHERE loginAttempts >= 3;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `loggedInNum` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `loggedInNum`()
BEGIN
SELECT count(username) FROM userTable WHERE loggedIn = 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `loggedInUsers` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `loggedInUsers`()
BEGIN
SELECT userID, username FROM userTable WHERE loggedIn = 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `logout` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `logout`(
IN un VARCHAR(45)
)
BEGIN
UPDATE userTable SET loggedIn = 0 WHERE username = un;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `register` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `register`(
IN id VARCHAR(45),
IN un VARCHAR(45),
IN pass VARCHAR(45),
IN email VARCHAR(45),
IN uRole VARCHAR(45),
IN createdBy VARCHAR(45)
)
BEGIN
INSERT INTO userTable(userID, username, userPassword, userEmail, fk_userRole, createdBy, updatedBy, loggedIn, loginAttempts)
 VALUES(id, un, pass, email, (SELECT iduserRoles FROM userRoles WHERE userRoleName = urole), createdBy, "none", 0, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `registeredNum` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `registeredNum`()
BEGIN
SELECT count(userID) from userTable;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `usersRegistered` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usersRegistered`()
BEGIN
SELECT count(userID) from userTable;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-29 15:40:02
