-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: noltodo
-- ------------------------------------------------------
-- Server version	5.5.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tagid` varchar(64) NOT NULL,
  `display` varchar(256) NOT NULL,
  PRIMARY KEY (`tagid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES ('2a1cad0fe7e3cb5da74f649538df36da7cb3e4874394b57919a77ea92076ba29','ãã¹ã2'),('2f6863a5c3d2796b0007ecdae8c226d10d539d843c3d7d36bbcd937758bf2971','ç­0083'),('44d409b982c785ee990266b6160e96a08eeb3b3af8761fd3e7dbc477aa78b096','å§åµNext2'),('49b2134b4f8f32ae53b0bc0e25fb6e549a010f0339fb9dc9c17d08b533d83e50','ãã¹ã'),('812d83652abad22f952091a5e04b1580e0278c394a5b5dceb49fb3042b78dd7c','ãã¹ã2'),('87224cb57f8e05ddf24094cdbe6fb22bd0d423a99c3be6009c94832ae0799792','ç­008X'),('a7439e3b086ea3b98d2fb76059553a09d5d6c1c3566a4a5cb4c40c9d32521539','ç­'),('d70e88a3261767da884d71eaf62007241c016edda04f4837ca385dc3a1a4939d','å§åµNext'),('e17a971addcf38680ca759f40fd4eb61900a2b35a4a243c3b74052429ad3fc85','å§åµNext2'),('TESTA','テストA'),('TESTB','テストB'),('TESTC','テストC');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasktag`
--

DROP TABLE IF EXISTS `tasktag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasktag` (
  `taskid` varchar(64) NOT NULL,
  `tagid` varchar(64) NOT NULL,
  PRIMARY KEY (`taskid`,`tagid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='タスクとタグを関連付けるテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasktag`
--

LOCK TABLES `tasktag` WRITE;
/*!40000 ALTER TABLE `tasktag` DISABLE KEYS */;
INSERT INTO `tasktag` VALUES ('31','TESTA'),('31','TESTC'),('31','TESTE'),('41','TESTA'),('41','TESTB'),('42','TESTB'),('42','TESTC'),('43','TESTC'),('44','3a88cdcdd70580523a9658061946832d6208b80842a3a7f8b5e704d87a0257b3'),('44','7223398ae310d1f8047c6ef79a102a62ca8f31a6bbf1774afe82d159bc14dab4'),('44','ec8b0dbb836cef9e3dbc7559c21cf3ca9a2debb25f8a5df056d182c52e0fbb7a'),('44','TESTD');
/*!40000 ALTER TABLE `tasktag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-20 17:18:06
