/*
Navicat MySQL Data Transfer

Source Server         : LocalMySQL
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : db_livraria

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2016-04-30 00:16:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_autor`
-- ----------------------------
DROP TABLE IF EXISTS `tb_autor`;
CREATE TABLE `tb_autor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `local_nascimento` varchar(255) DEFAULT NULL,
  `local_falecimento` varchar(255) DEFAULT NULL,
  `biografia` varchar(255) DEFAULT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `data_falecimento` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_autor
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_categoria_livro`
-- ----------------------------
DROP TABLE IF EXISTS `tb_categoria_livro`;
CREATE TABLE `tb_categoria_livro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_categoria_livro
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_cliente`
-- ----------------------------
DROP TABLE IF EXISTS `tb_cliente`;
CREATE TABLE `tb_cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `rg` varchar(255) DEFAULT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `estado_civil` varchar(255) DEFAULT NULL,
  `logradouro` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `numero` int(5) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `bairro` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `uf` varchar(255) DEFAULT NULL,
  `sexo` varchar(255) DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cliente_usuario` (`id_usuario`),
  CONSTRAINT `cliente_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `tb_usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cliente
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_editora`
-- ----------------------------
DROP TABLE IF EXISTS `tb_editora`;
CREATE TABLE `tb_editora` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `cnpj` varchar(255) DEFAULT NULL,
  `telefones` varchar(255) DEFAULT NULL,
  `logradouro` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `bairro` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `uf` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_editora
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_itens_pedido`
-- ----------------------------
DROP TABLE IF EXISTS `tb_itens_pedido`;
CREATE TABLE `tb_itens_pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) NOT NULL,
  `id_livro` int(11) NOT NULL,
  `percDesconto` decimal(10,0) DEFAULT NULL,
  `quantidade` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `itenspedido_livro` (`id_livro`),
  KEY `itenspedido_pedido` (`id_pedido`),
  CONSTRAINT `itenspedido_livro` FOREIGN KEY (`id_livro`) REFERENCES `tb_livro` (`id`),
  CONSTRAINT `itenspedido_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `tb_pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_itens_pedido
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_livro`
-- ----------------------------
DROP TABLE IF EXISTS `tb_livro`;
CREATE TABLE `tb_livro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_editora` int(11) NOT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `formato` varchar(255) DEFAULT NULL,
  `sumario` varchar(255) DEFAULT NULL,
  `resumo` varchar(255) DEFAULT NULL,
  `data_publicacao` datetime DEFAULT NULL,
  `preco_custo` decimal(10,0) DEFAULT NULL,
  `preco_venda` decimal(10,0) DEFAULT NULL,
  `margem_lucro` decimal(10,0) DEFAULT NULL,
  `quantidade_estoque` int(11) DEFAULT NULL,
  `estoque_minimo` int(11) DEFAULT NULL,
  `numero_paginas` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `livro_editora` (`id_editora`),
  CONSTRAINT `livro_editora` FOREIGN KEY (`id_editora`) REFERENCES `tb_editora` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_livro
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_livros_autores`
-- ----------------------------
DROP TABLE IF EXISTS `tb_livros_autores`;
CREATE TABLE `tb_livros_autores` (
  `id_livro` int(11) NOT NULL,
  `id_autor` int(11) NOT NULL,
  PRIMARY KEY (`id_livro`,`id_autor`),
  KEY `autor_livro` (`id_autor`),
  CONSTRAINT `autor_livro` FOREIGN KEY (`id_autor`) REFERENCES `tb_autor` (`Id`),
  CONSTRAINT `livro_autor` FOREIGN KEY (`id_livro`) REFERENCES `tb_livro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_livros_autores
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_livros_categorias`
-- ----------------------------
DROP TABLE IF EXISTS `tb_livros_categorias`;
CREATE TABLE `tb_livros_categorias` (
  `id_livro` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  PRIMARY KEY (`id_livro`,`id_categoria`),
  KEY `categoria_livro` (`id_categoria`),
  CONSTRAINT `categoria_livro` FOREIGN KEY (`id_categoria`) REFERENCES `tb_categoria_livro` (`id`),
  CONSTRAINT `livro_categoria` FOREIGN KEY (`id_livro`) REFERENCES `tb_livro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_livros_categorias
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_pedido`
-- ----------------------------
DROP TABLE IF EXISTS `tb_pedido`;
CREATE TABLE `tb_pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) NOT NULL,
  `data_venda` datetime DEFAULT NULL,
  `forma_pagamento` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pedido_cliente` (`id_cliente`),
  CONSTRAINT `pedido_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_pedido
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_usuario`
-- ----------------------------
DROP TABLE IF EXISTS `tb_usuario`;
CREATE TABLE `tb_usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_usuario
-- ----------------------------
