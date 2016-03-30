CREATE DATABASE  IF NOT EXISTS `db_museu` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_museu`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: db_museu
-- ------------------------------------------------------
-- Server version	5.7.9-log

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
-- Table structure for table `tb_dias_gratuitos`
--

DROP TABLE IF EXISTS `tb_dias_gratuitos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_dias_gratuitos` (
  `id_exposicao` int(10) unsigned NOT NULL,
  `dia_semana` int(1) NOT NULL,
  PRIMARY KEY (`id_exposicao`,`dia_semana`),
  CONSTRAINT `diagratuito_exposicao` FOREIGN KEY (`id_exposicao`) REFERENCES `tb_exposicao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dias_gratuitos`
--

LOCK TABLES `tb_dias_gratuitos` WRITE;
/*!40000 ALTER TABLE `tb_dias_gratuitos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_dias_gratuitos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_emprestimo`
--

DROP TABLE IF EXISTS `tb_emprestimo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_emprestimo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `id_museu` int(10) NOT NULL,
  `id_obra` int(10) NOT NULL,
  `data_inicio` date NOT NULL,
  `data_fim` date NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `emprestimo_museu` (`id_museu`),
  KEY `emprestimo_obra` (`id_obra`),
  CONSTRAINT `emprestimo_museu` FOREIGN KEY (`id_museu`) REFERENCES `tb_museu` (`id`),
  CONSTRAINT `emprestimo_obra` FOREIGN KEY (`id_obra`) REFERENCES `tb_obra` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_emprestimo`
--

LOCK TABLES `tb_emprestimo` WRITE;
/*!40000 ALTER TABLE `tb_emprestimo` DISABLE KEYS */;
INSERT INTO `tb_emprestimo` VALUES (2,2,7,'2015-09-12','2015-09-20','yjyjuy');
/*!40000 ALTER TABLE `tb_emprestimo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_exposicao`
--

DROP TABLE IF EXISTS `tb_exposicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_exposicao` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `secao` varchar(45) NOT NULL,
  `descricao` varchar(45) NOT NULL,
  `id_museu` int(11) NOT NULL,
  `data_inicio` date NOT NULL,
  `data_fim` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_exposicao`
--

LOCK TABLES `tb_exposicao` WRITE;
/*!40000 ALTER TABLE `tb_exposicao` DISABLE KEYS */;
INSERT INTO `tb_exposicao` VALUES (5,'exposicao','','asdasd',2,'2015-09-12','2015-09-20'),(6,'expo','','asd',2,'2015-09-12','2015-09-20'),(7,'expoaaaa','','Descricao legal',2,'2015-09-12','2015-09-20');
/*!40000 ALTER TABLE `tb_exposicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_exposicao_obras`
--

DROP TABLE IF EXISTS `tb_exposicao_obras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_exposicao_obras` (
  `id_exposicao` int(11) NOT NULL,
  `id_obra` int(11) NOT NULL,
  PRIMARY KEY (`id_exposicao`,`id_obra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_exposicao_obras`
--

LOCK TABLES `tb_exposicao_obras` WRITE;
/*!40000 ALTER TABLE `tb_exposicao_obras` DISABLE KEYS */;
INSERT INTO `tb_exposicao_obras` VALUES (5,7),(6,7),(7,7);
/*!40000 ALTER TABLE `tb_exposicao_obras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_museu`
--

DROP TABLE IF EXISTS `tb_museu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_museu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `nome_responsavel` varchar(255) NOT NULL,
  `fone` varchar(45) NOT NULL,
  `fone_responsavel` varchar(45) NOT NULL,
  `cep` varchar(45) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  `numero` varchar(255) NOT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `estado` varchar(2) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_responsavel` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_museu`
--

LOCK TABLES `tb_museu` WRITE;
/*!40000 ALTER TABLE `tb_museu` DISABLE KEYS */;
INSERT INTO `tb_museu` VALUES (2,'museu','dono do museu','12312313','123123','08250620','um endereco','89aaa2','89','SP','outro@gmail.com','email@emial.com');
/*!40000 ALTER TABLE `tb_museu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_obra`
--

DROP TABLE IF EXISTS `tb_obra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_obra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `descricao` longtext,
  `tipo` varchar(45) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `periodo` varchar(45) DEFAULT NULL,
  `artista` varchar(45) DEFAULT NULL,
  `valor_estimado` decimal(10,0) DEFAULT NULL,
  `fl_emprestado` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_obra`
--

LOCK TABLES `tb_obra` WRITE;
/*!40000 ALTER TABLE `tb_obra` DISABLE KEYS */;
INSERT INTO `tb_obra` VALUES (6,'asdasd','asdasds2222222222','Comedia','2015-05-10','atigoaaaaaaaaaaaaaaaaaa','asdasd',NULL,0),(7,'asdasdew12123','asdasdadsfwsssssssssssssssssssss','asdasd','2015-10-09','asd','asd',NULL,0);
/*!40000 ALTER TABLE `tb_obra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_usuario`
--

DROP TABLE IF EXISTS `tb_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `username` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_usuario`
--

LOCK TABLES `tb_usuario` WRITE;
/*!40000 ALTER TABLE `tb_usuario` DISABLE KEYS */;
INSERT INTO `tb_usuario` VALUES (1,'Administrador','admin','admin');
/*!40000 ALTER TABLE `tb_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_visitante`
--

DROP TABLE IF EXISTS `tb_visitante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_visitante` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `escolaridade` varchar(45) DEFAULT NULL,
  `cep` varchar(45) DEFAULT NULL,
  `endereco` varchar(45) DEFAULT NULL,
  `numero` varchar(45) DEFAULT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `genero` int(11) NOT NULL,
  `data_nascimento` date NOT NULL,
  `nacionalidade` varchar(45) DEFAULT NULL,
  `meio_transporte` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_visitante`
--

LOCK TABLES `tb_visitante` WRITE;
/*!40000 ALTER TABLE `tb_visitante` DISABLE KEYS */;
INSERT INTO `tb_visitante` VALUES (1,'Thais Quintas de Arcanjo','Superior completo','08250620','Rua Barbara Leoni, 89','89','12a',2,'2015-09-29','brasileira','metro');
/*!40000 ALTER TABLE `tb_visitante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'db_museu'
--

--
-- Dumping routines for database 'db_museu'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-29 23:58:56
