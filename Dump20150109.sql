CREATE DATABASE  IF NOT EXISTS `noltodo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `noltodo`;
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
-- Table structure for table `m_job`
--

DROP TABLE IF EXISTS `m_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_job` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `parentid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特化一覧';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_job`
--

LOCK TABLES `m_job` WRITE;
/*!40000 ALTER TABLE `m_job` DISABLE KEYS */;
INSERT INTO `m_job` VALUES ('ans','暗殺','nin'),('bug','武芸','sam'),('bus','武士道','sam'),('dou','陰陽道','onm'),('enb','四象','kab'),('fut','仏門','sou'),('gag','雅楽','kam'),('gun','軍学','sam'),('hei','僧兵','sou'),('ijy','医術','yak'),('jin','神通力','yak'),('jyu','忍術','nin'),('kab','傾奇','0'),('kaj','鍛冶','0'),('kam','神職','0'),('kat','刀鍛冶','kaj'),('kos','古神','kam'),('mik','密教','sou'),('nin','忍者','0'),('nou','能楽','kab'),('onm','陰陽','0'),('pou','忍法','nin'),('sam','侍','0'),('sen','仙論','onm'),('sho','召喚','onm'),('shu','修験','yak'),('sin','神道','kam'),('sou','僧','0'),('tat','殺陣','kab'),('tep','鉄砲','kaj'),('yak','薬師','0'),('yor','鎧鍛冶','kaj');
/*!40000 ALTER TABLE `m_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `task` varchar(2048) DEFAULT NULL,
  `writedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userid` varchar(64) DEFAULT NULL,
  `jobid` varchar(64) DEFAULT NULL,
  `limitdate` timestamp NULL DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='タスク一覧';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (23,'薬師の生産','1. 稼業を25にする','2015-01-09 06:15:14',NULL,'kaj','2015-01-09 06:15:14','op'),(24,'刀の職人装備','1. 棍棒の職人装備をそろえる','2015-01-09 08:36:43',NULL,'kat','2015-01-09 08:36:43','op'),(25,'タイトル','AAAAA','2015-01-09 08:32:59',NULL,'ans','2015-01-09 08:32:59','op'),(26,'演舞の覚醒消費','- 週間依頼\r\n- 道場','2015-01-09 06:18:08',NULL,'enb','2015-01-09 06:18:08','op');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `passphrase` varchar(64) NOT NULL,
  `failure` int(11) NOT NULL DEFAULT '0',
  `locktime` timestamp NULL DEFAULT NULL,
  `lastlogintime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='利用ユーザ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('0','a-pz','password',0,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-09 17:39:16
