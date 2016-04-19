/*
Navicat MySQL Data Transfer

Source Server         : LocalMySQL
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : db_museu

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2015-12-03 19:43:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_dias_gratuitos`
-- ----------------------------
DROP TABLE IF EXISTS `tb_dias_gratuitos`;
CREATE TABLE `tb_dias_gratuitos` (
  `id_exposicao` int(10) unsigned NOT NULL,
  `dia_semana` int(1) NOT NULL,
  PRIMARY KEY (`id_exposicao`,`dia_semana`),
  CONSTRAINT `diagratuito_exposicao` FOREIGN KEY (`id_exposicao`) REFERENCES `tb_exposicao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_dias_gratuitos
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_emprestimo`
-- ----------------------------
DROP TABLE IF EXISTS `tb_emprestimo`;
CREATE TABLE `tb_emprestimo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_museu` int(10) unsigned NOT NULL,
  `id_obra` int(10) unsigned NOT NULL,
  `data_inicio` date NOT NULL,
  `data_fim` date NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `emprestimo_museu` (`id_museu`),
  KEY `emprestimo_obra` (`id_obra`),
  CONSTRAINT `emprestimo_museu` FOREIGN KEY (`id_museu`) REFERENCES `tb_museu` (`id`),
  CONSTRAINT `emprestimo_obra` FOREIGN KEY (`id_obra`) REFERENCES `tb_obra` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_emprestimo
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_exposicao`
-- ----------------------------
DROP TABLE IF EXISTS `tb_exposicao`;
CREATE TABLE `tb_exposicao` (
  `descricao` varchar(255) DEFAULT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_museu` int(10) unsigned NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `secao` varchar(255) DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  `valor_ingresso` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `exposicao_museu` (`id_museu`),
  CONSTRAINT `exposicao_museu` FOREIGN KEY (`id_museu`) REFERENCES `tb_museu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_exposicao
-- ----------------------------
INSERT INTO `tb_exposicao` VALUES ('', '6', '2', 'Obras do Renascimento à Modernidade', 'Bloco 302, Salão de Exposições', '2015-12-03', '2015-12-03', '20.00');

-- ----------------------------
-- Table structure for `tb_exposicao_obras`
-- ----------------------------
DROP TABLE IF EXISTS `tb_exposicao_obras`;
CREATE TABLE `tb_exposicao_obras` (
  `id_exposicao` int(10) unsigned NOT NULL,
  `id_obra` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_exposicao`,`id_obra`),
  KEY `exposicao_obra_obra` (`id_obra`),
  CONSTRAINT `exposicao_obra_exposicao` FOREIGN KEY (`id_exposicao`) REFERENCES `tb_exposicao` (`id`),
  CONSTRAINT `exposicao_obra_obra` FOREIGN KEY (`id_obra`) REFERENCES `tb_obra` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_exposicao_obras
-- ----------------------------
INSERT INTO `tb_exposicao_obras` VALUES ('6', '11');
INSERT INTO `tb_exposicao_obras` VALUES ('6', '12');
INSERT INTO `tb_exposicao_obras` VALUES ('6', '13');
INSERT INTO `tb_exposicao_obras` VALUES ('6', '14');

-- ----------------------------
-- Table structure for `tb_ingresso`
-- ----------------------------
DROP TABLE IF EXISTS `tb_ingresso`;
CREATE TABLE `tb_ingresso` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_cliente` int(10) unsigned NOT NULL,
  `id_exposicao` int(10) NOT NULL,
  `data` date NOT NULL,
  `valor` double NOT NULL,
  `tipo_desconto` int(10) DEFAULT NULL,
  `id_venda` int(10) unsigned NOT NULL,
  `desconto` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_cliente` (`id_cliente`),
  KEY `item_venda` (`id_venda`),
  CONSTRAINT `item_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `tb_visitante` (`id`),
  CONSTRAINT `item_venda` FOREIGN KEY (`id_venda`) REFERENCES `tb_venda` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_ingresso
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_museu`
-- ----------------------------
DROP TABLE IF EXISTS `tb_museu`;
CREATE TABLE `tb_museu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_museu
-- ----------------------------
INSERT INTO `tb_museu` VALUES ('2', 'Museu Municipal de São Paulo', 'Fernanda Paula de Aguiar', '(11) 3346-0001', '(11) 98754-2347', '03213-002', 'Avenida Cruzeiro do Sul', '4764', '', 'SP', 'museumunicipal@saopaulo.sp.gov.br', 'fp.aguiar@museumunicipal.saopaulo.sp.gov.br');

-- ----------------------------
-- Table structure for `tb_obra`
-- ----------------------------
DROP TABLE IF EXISTS `tb_obra`;
CREATE TABLE `tb_obra` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `descricao` longtext,
  `tipo` varchar(45) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `periodo` varchar(45) DEFAULT NULL,
  `artista` varchar(45) DEFAULT NULL,
  `fl_emprestado` int(2) DEFAULT NULL,
  `valor_estimado` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_obra
-- ----------------------------
INSERT INTO `tb_obra` VALUES ('11', 'Abapuru do Pezão', '', 'Pintura', null, 'Arte do Renascimento à modernidade', 'Tarcila do Amaral', null, '0.00');
INSERT INTO `tb_obra` VALUES ('12', 'Monalisa', '', 'Pintura', null, 'Arte do Renascimento à modernidade', 'Leonardo da Vinci', null, '0.00');
INSERT INTO `tb_obra` VALUES ('13', 'O Grito', '', 'Pintura', null, 'Arte do Renascimento à modernidade', 'Edvard Munch', null, '0.00');
INSERT INTO `tb_obra` VALUES ('14', 'A noite estrelada', '', 'Pintura', null, 'Arte do Renascimento à modernidade', 'Vicent Van Gogh', null, '0.00');
INSERT INTO `tb_obra` VALUES ('15', 'Rapariga com brinco de pérola', '', 'Pintura', null, 'Arte do Renascimento à modernidade', 'Johannes Vermeer', null, '0.00');

-- ----------------------------
-- Table structure for `tb_usuario`
-- ----------------------------
DROP TABLE IF EXISTS `tb_usuario`;
CREATE TABLE `tb_usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `username` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_usuario
-- ----------------------------
INSERT INTO `tb_usuario` VALUES ('1', 'Administrador', 'admin', 'admin');

-- ----------------------------
-- Table structure for `tb_venda`
-- ----------------------------
DROP TABLE IF EXISTS `tb_venda`;
CREATE TABLE `tb_venda` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `forma_pagamento` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_venda
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_visitante`
-- ----------------------------
DROP TABLE IF EXISTS `tb_visitante`;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_visitante
-- ----------------------------
INSERT INTO `tb_visitante` VALUES ('4', 'Maria Paula Saladino', 'Ensino Superior Completo', '023340001', 'Avenida José Paulino', '1267', 'Bloco 2, Ap. 403', '2', '1985-02-13', 'Brasileiro', 'Ônubus, Metrô');
INSERT INTO `tb_visitante` VALUES ('5', 'Raul Gonçalves', 'Ensino Médio', '080800002', 'Rua Andorinha do verão', '124', '', '2', '1995-07-20', 'Brasileiro', 'Carro');
