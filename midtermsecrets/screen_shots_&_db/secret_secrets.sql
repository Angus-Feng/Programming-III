-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: db-java-spring.clu3uonzsfi7.us-east-1.rds.amazonaws.com    Database: secret
-- ------------------------------------------------------
-- Server version	8.0.23

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `secrets`
--

DROP TABLE IF EXISTS `secrets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secrets` (
  `id` bigint NOT NULL,
  `body` varchar(1000) DEFAULT NULL,
  `createddt` datetime DEFAULT NULL,
  `expirydt` datetime DEFAULT NULL,
  `has_been_read` bit(1) NOT NULL,
  `secret_to_read` varchar(255) DEFAULT NULL,
  `secret_to_update` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secrets`
--

LOCK TABLES `secrets` WRITE;
/*!40000 ALTER TABLE `secrets` DISABLE KEYS */;
INSERT INTO `secrets` VALUES (1,'aaaaaaaaaaaaaaaaaaaaa','2021-12-08 11:15:51','2021-12-10 11:15:00',_binary '','w4XAsIHL0A','8QGpy1tQeM'),(2,'bbbbbbbbbbbbbb','2021-12-08 11:24:05','2021-12-09 11:24:00',_binary '\0','qro0861lVf','gzktO4QKab'),(13,'fafdafdafdasf','2021-12-08 14:06:03','2021-12-08 14:10:00',_binary '\0','zsSQKyMfhl','v54sZeUBTd'),(4,'dddd','2021-12-08 11:29:51','2021-12-11 11:29:00',_binary '\0','OZYUPyOujx','LqbCL8hupR'),(5,'aaaafbdsaf','2021-12-08 11:31:21','2021-12-25 11:31:00',_binary '\0','N79y7H2CUG','p8UFlnhdqu'),(8,'aaaaaa','2021-12-08 11:39:21','2021-12-08 18:08:00',_binary '','uWCVcSBoAO','MPyyiN7xmN'),(12,'bafdafdsafa','2021-12-08 14:01:38','2021-12-18 14:01:00',_binary '\0','Mlrr90powQ','VB2HWQGQy4');
/*!40000 ALTER TABLE `secrets` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-08 14:09:43
