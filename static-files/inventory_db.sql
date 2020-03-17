-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: inventory
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `log_tb_item`
--

DROP TABLE IF EXISTS `log_tb_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_tb_item` (
  `log_id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'log主键',
  `item_number` int(6) NOT NULL COMMENT '部件号',
  `item_desc` varchar(100) NOT NULL COMMENT '描述',
  `item_brand` varchar(20) NOT NULL COMMENT '品牌',
  `item_model` varchar(40) NOT NULL COMMENT '型号',
  `vmi` tinyint(4) NOT NULL DEFAULT '0' COMMENT '供应商库存',
  `safe_vmi` int(6) NOT NULL DEFAULT '0' COMMENT '安全库存数',
  `expire_date` int(11) NOT NULL COMMENT '有效期',
  `item_unit` varchar(10) NOT NULL DEFAULT '0' COMMENT '单位',
  `create_user` varchar(40) DEFAULT NULL COMMENT '添加人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `modify_user` varchar(40) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:启用;1:禁用',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_tb_item`
--

LOCK TABLES `log_tb_item` WRITE;
/*!40000 ALTER TABLE `log_tb_item` DISABLE KEYS */;
INSERT INTO `log_tb_item` VALUES (1,17,'123descedit','123brandedit','123modeledit',0,123,360,'unitedit',NULL,NULL,NULL,NULL,0),(2,17,'123descedit','123brandedit','123modeledit',0,123,360,'unitedit',NULL,NULL,NULL,NULL,0),(3,17,'123descedit','123brandedit','123modeledit',0,123,360,'unitedit',NULL,NULL,NULL,NULL,0),(4,17,'123descedit','123brandedit','123modeledit',1,123,360,'unitedit',NULL,'2019-12-06 11:33:54','lchen2','2019-12-06 03:33:55',0),(5,17,'123descedit','123brandedit','123modeledit',1,123,360,'unitedit',NULL,'2019-12-06 11:37:03','lchen2','2019-12-06 03:36:47',0),(6,17,'123descedit','123brandedit','123modeledit',1,123,360,'unitedit',NULL,'2019-12-06 11:45:03','lchen2','2019-12-06 03:44:46',0),(7,17,'123descedit','123brandedit','123modeledit',1,123,360,'unitedit','lchen2','2019-12-06 02:49:00','lchen2','2019-12-09 02:28:51',0),(8,18,'物品123','物品品牌123','型号abc',0,-22,360,'unit','lchen2','2019-12-17 02:37:06','lchen2','2019-12-17 02:37:25',0),(9,17,'123descedit','123brandedit','123modeledit',1,123,360,'unitedit','lchen2','2019-12-06 02:49:00','lchen2','2019-12-18 07:46:07',0),(10,12,'sad撒多撒edit','sad撒多撒edit','sad撒多撒edit',0,145,360,'123edit','lchen2','2019-12-05 07:41:07','lchen2','2019-12-19 05:55:51',0),(11,2,'物品描述','物品品牌','型号',1,1,360,'0','lchen2','2019-12-04 05:14:49','lchen2','2019-12-26 05:39:57',0),(12,2,'物品描述','物品品牌','型号',1,1,360,'0','lchen2',NULL,'lchen2','2019-12-26 05:57:02',0),(13,2,'物品描述Edit','物品品牌Edit','型号Edit',0,11,360,'unit','lchen2','2019-12-04 05:14:49','lchen2','2019-12-26 06:02:45',0),(14,2,'物品描述Edit','物品品牌Edit','型号Edit',1,11,360,'unit','lchen2','2019-12-04 05:14:49','lchen2','2019-12-26 06:03:10',0),(15,12,'sad撒多撒edit','sad撒多撒edit','sad撒多撒edit',1,145,360,'123edit','lchen2','2019-12-05 07:41:07','lchen2','2019-12-31 02:58:44',0),(16,20,'物品描述123阿萨德自行车','物品品牌123阿萨德自行车','物品型号123阿萨德自行车',0,-122,360,'unit','lchen2','2019-12-19 08:20:58','lchen2','2019-12-31 02:59:03',0),(17,23,'test描述1','test品牌2','test型号3',1,-999,360,'件','lchen2','2020-01-02 05:28:41','lchen2','2020-01-02 05:28:49',0),(18,23,'test描述1','test品牌2','test型号3',1,-999,360,'套','lchen2','2020-01-02 05:28:41','lchen2','2020-01-02 05:29:23',0),(19,22,'禁用描述1','禁用品牌2','禁用型号3',1,-1,360,'件','lchen2','2020-01-02 05:27:54','lchen2','2020-01-02 05:31:01',0),(20,23,'test描述1','test品牌2','test型号3',1,-999,360,'套','lchen2','2020-01-02 05:28:41','lchen2','2020-01-02 05:33:14',0),(21,23,'test描述1','test品牌2','test型号3',1,-999,360,'个','lchen2','2020-01-02 05:28:41','lchen2','2020-01-02 05:33:27',0),(22,29,'test123物品描述123','test123物品品牌1234','test123物品品牌12345678',0,-9,360,'3','lchen2','2020-01-10 05:19:22','lchen2','2020-01-10 05:19:57',0),(23,23,'test描述1','test品牌2','test型号3',1,123,360,'件','lchen2','2020-01-02 05:28:41','lchen2','2020-01-16 01:29:47',0),(24,23,'test描述1','test品牌2','test型号3',1,123,360,'件','lchen2','2020-01-02 05:28:41','lchen2','2020-01-16 01:40:07',0),(25,23,'test描述1','test品牌2','test型号3',1,123,360,'件','lchen2','2020-01-02 05:28:41','lchen2','2020-01-16 01:44:31',0),(26,23,'test描述1','test品牌2','test型号3',1,213,360,'件','lchen2','2020-01-02 05:28:41','lchen2','2020-01-16 01:46:05',0),(27,23,'test描述1','test品牌2','test型号3',0,213,360,'m','lchen2','2020-01-02 05:28:41','lchen2','2020-01-16 01:54:18',0),(28,23,'test描述1','test品牌2','test型号3',0,213,360,'m','lchen2','2020-01-02 05:28:41','lchen2','2020-01-16 09:58:19',0),(29,32,'描述1','描述1','描述1',1,0,360,'件','lchen2','2020-01-16 16:21:11','lchen2','2020-01-17 10:12:46',0),(30,32,'描述1','描述1','描述1',0,0,360,'件','lchen2','2020-01-16 16:21:11','lchen2','2020-01-17 10:13:18',0),(31,32,'描述1','描述1','描述1',1,0,360,'件','lchen2','2020-01-16 16:21:11','lchen2','2020-01-17 10:13:39',0),(32,33,'test123456wqe','test123456wqe','test123456wqe',0,0,360,'件','lchen2','2020-01-17 10:27:15','lchen2','2020-01-17 10:27:27',0),(33,33,'test123456wqe','test123456wqe','test123456wqe',1,0,360,'件','lchen2','2020-01-17 10:27:15','lchen2','2020-01-17 10:27:38',0),(34,32,'描述1','描述1','描述1',1,0,360,'件','lchen2','2020-01-16 16:21:11','lchen2','2020-01-19 13:02:49',0),(35,36,'物品test描述1','物品test品牌1','物品test型号1',0,0,360,'件','lchen2','2020-03-13 13:26:57','lchen2','2020-03-13 13:27:25',0),(36,36,'物品test描述1','物品test品牌1','物品test型号1',1,0,360,'件','lchen2','2020-03-13 13:26:57','lchen2','2020-03-13 13:27:37',0);
/*!40000 ALTER TABLE `log_tb_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_dc`
--

DROP TABLE IF EXISTS `tb_dc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_dc` (
  `dc_seq` int(6) NOT NULL AUTO_INCREMENT,
  `dc_code` varchar(2) NOT NULL,
  PRIMARY KEY (`dc_seq`,`dc_code`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dc`
--

LOCK TABLES `tb_dc` WRITE;
/*!40000 ALTER TABLE `tb_dc` DISABLE KEYS */;
INSERT INTO `tb_dc` VALUES (1,'MP'),(2,'IC'),(2,'MP'),(3,'IC'),(3,'MP'),(4,'IC'),(4,'MP'),(5,'IC'),(5,'MP'),(6,'IC'),(6,'MP'),(7,'IC'),(7,'MP'),(8,'IC'),(8,'MP'),(9,'IC'),(9,'MP'),(10,'IC'),(10,'MP'),(11,'MP'),(12,'IC'),(12,'MP'),(13,'IC'),(13,'MP'),(14,'IC'),(14,'MP'),(15,'MP'),(16,'MP'),(17,'IC'),(17,'MP'),(18,'IC'),(18,'MP'),(19,'IC'),(19,'MP'),(20,'IC'),(20,'MP'),(21,'IC'),(21,'MP'),(22,'IC'),(22,'MP'),(23,'IC'),(23,'MP'),(24,'IC'),(24,'MP'),(25,'MP'),(26,'MP'),(27,'MP'),(28,'MP'),(29,'MP'),(30,'MP'),(31,'MP'),(32,'MP'),(33,'MP'),(34,'MP'),(35,'MP'),(36,'MP'),(37,'MP');
/*!40000 ALTER TABLE `tb_dc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_inv`
--

DROP TABLE IF EXISTS `tb_inv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_inv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_number` varchar(6) NOT NULL COMMENT '部件号',
  `from_inv` varchar(10) NOT NULL COMMENT 'from_inv',
  `to_inv` varchar(10) NOT NULL COMMENT 'to_inv',
  `from_loc` varchar(10) DEFAULT NULL COMMENT 'from_loc',
  `to_loc` varchar(10) DEFAULT NULL COMMENT 'to_loc',
  `qty` int(6) NOT NULL COMMENT '数量',
  `lot_number` varchar(100) DEFAULT NULL COMMENT '批号',
  `inv_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '日期',
  `create_user` varchar(40) DEFAULT NULL COMMENT '添加人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  `ic_number` varchar(10) DEFAULT NULL COMMENT '预留',
  `price` decimal(10,4) DEFAULT NULL COMMENT '单价',
  `amount` decimal(40,5) DEFAULT NULL COMMENT '金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_inv`
--

LOCK TABLES `tb_inv` WRITE;
/*!40000 ALTER TABLE `tb_inv` DISABLE KEYS */;
INSERT INTO `tb_inv` VALUES (33,'21','RI','VI','C','B',-9,'-867876','2019-12-30 00:00:00','lchen2','2019-12-30 08:41:34',NULL,'',NULL,NULL),(34,'20','RI','VI','A','C',-8,'123213qweq','2019-12-30 00:00:00','lchen2','2019-12-30 08:41:34',NULL,'',NULL,NULL),(35,'17','MI','VI','A','B',-76,'-9','2019-12-26 00:00:00','lchen2','2019-12-30 08:41:34',NULL,'',NULL,NULL),(36,'21','VI','MI','A','C',-9,'8','2019-12-30 00:00:00','lchen2','2019-12-30 08:54:17',NULL,'',NULL,NULL),(37,'20','MI','VI','C','B',-888,'-999','2019-12-30 00:00:00','lchen2','2019-12-30 08:54:17',NULL,'',NULL,NULL),(38,'20','RI','VI','B','B',-1,'-9','2019-12-29 00:00:00','lchen2','2019-12-31 03:06:10',NULL,'',NULL,NULL),(39,'12','VI','RI','B','A',-99,'-87','2019-12-29 00:00:00','lchen2','2019-12-31 03:06:10',NULL,'',NULL,NULL),(40,'12','RI','MI','A','C',-765,'qweasd23','2019-12-27 00:00:00','lchen2','2019-12-31 03:06:10',NULL,'',NULL,NULL),(41,'12','RI','MI','B','C',-99,'qweasdzxc','2019-12-27 00:00:00','lchen2','2019-12-31 03:06:10',NULL,'',NULL,NULL),(42,'21','RI','VI','B','C',-999,'q','2019-12-25 00:00:00','lchen2','2019-12-31 03:09:32',NULL,'',NULL,NULL),(43,'20','VI','MI','A','C',-8755,'-请问奥所多','2019-12-28 00:00:00','lchen2','2019-12-31 03:09:32',NULL,'',NULL,NULL),(44,'17','RI','VI','C','A',-12,'-999','2019-12-27 00:00:00','lchen2','2019-12-31 03:09:32',NULL,'',NULL,NULL),(45,'12','VI','RI','B','C',-20,'qwesad','2019-12-26 00:00:00','lchen2','2019-12-31 03:09:32',NULL,'',NULL,NULL),(46,'23','VI','VI','A','B',-9,'-87','2020-01-02 00:00:00','lchen2','2020-01-02 05:39:39',NULL,'',NULL,NULL),(47,'23','VI','RI','B','C',9,'-8878','2020-01-02 00:00:00','lchen2','2020-01-02 05:39:39',NULL,'',NULL,NULL),(48,'22','RI','MI','B','A',-10,'123','2019-12-29 00:00:00','lchen2','2020-01-02 05:39:39',NULL,'',NULL,NULL),(49,'22','VI','MI','A','C',10,'456','2019-12-29 00:00:00','lchen2','2020-01-02 05:39:39',NULL,'',NULL,NULL),(50,'22','RI','VI','C','C',-9,'123qwe','2020-01-02 00:00:00','lchen2','2020-01-02 05:50:04',NULL,'',NULL,NULL),(51,'22','RI','VI','A','B',9,'77767','2020-01-02 00:00:00','lchen2','2020-01-02 08:48:49',NULL,'',NULL,NULL),(52,'22','RI','VI','B','B',98,'qwewq','2020-01-02 00:00:00','lchen2','2020-01-02 08:49:45',NULL,'',NULL,NULL),(53,'22','MI','RI','A','B',-12,'qweqwa','2020-01-01 00:00:00','lchen2','2020-01-02 08:49:45',NULL,'',NULL,NULL),(54,'22','VI','RI','B','B',-2132,'eweee','2020-01-06 00:00:00','lchen2','2020-01-06 08:52:32',NULL,NULL,NULL,NULL),(55,'21','VI','RI','B','A',1,'q','2020-01-06 00:00:00','lchen2','2020-01-06 09:07:25','1',NULL,NULL,NULL),(56,'22','VI','MI','A','B',-9,'999','2020-01-06 00:00:00','lchen2','2020-01-07 02:00:22','note1',NULL,NULL,NULL),(57,'20','VI','RI','A','C',-8,'8888','2020-01-05 00:00:00','lchen2','2020-01-07 02:00:22','note2',NULL,NULL,NULL),(58,'20','MI','RI','C','A',-8,'9999','2020-01-05 00:00:00','lchen2','2020-01-07 02:00:22','note3',NULL,NULL,NULL),(59,'21','VI','RI','A','B',-9,'99','2020-01-07 00:00:00','lchen2','2020-01-07 02:01:30','',NULL,NULL,NULL),(60,'21','VI','RI','A','C',-9,'99','2020-01-07 00:00:00','lchen2','2020-01-07 02:01:57','',NULL,NULL,NULL),(64,'23','RI','VI','A','B',-9,'23','2020-01-15 00:00:00','lchen2','2020-01-15 01:47:46','note',NULL,NULL,NULL),(65,'22','MI','RI','C','A',-12,'2345','2020-01-15 00:00:00','lchen2','2020-01-15 01:47:46','note1',NULL,NULL,NULL),(66,'30','VI','RI','B','B',9,'-9','2020-01-17 08:00:00','lchen2','2020-01-17 14:05:56','1111',NULL,NULL,NULL),(67,'32','RI','VI','A','A',10,'000','2020-01-17 08:00:00','lchen2','2020-01-17 14:05:56','abc',NULL,NULL,NULL),(112,'32','VA','VG',NULL,NULL,1,'','2020-02-20 08:00:00','lchen2','2020-02-20 14:37:15','1','IC000012',NULL,NULL),(113,'32','VA','VG',NULL,NULL,1,'','2020-02-20 08:00:00','lchen2','2020-02-20 14:37:15','','IC000012',NULL,NULL),(114,'33','VA','VG',NULL,NULL,1,'','2020-02-20 08:00:00','lchen2','2020-02-20 14:37:15','1','IC000012',NULL,NULL),(115,'33','VA','VG',NULL,NULL,1,'','2020-02-20 08:00:00','lchen2','2020-02-20 14:37:15','','IC000012',NULL,NULL),(116,'32','VA','VG',NULL,NULL,5,'','2020-02-20 08:00:00','lchen2','2020-02-20 15:29:20','','IC000013',NULL,NULL),(117,'32','VA','VG',NULL,NULL,5,'','2020-02-20 08:00:00','lchen2','2020-02-20 15:29:20','','IC000013',NULL,NULL),(118,'33','VA','VG',NULL,NULL,6,'1','2020-02-21 08:00:00','eliu','2020-02-21 10:08:00','','IC000014',NULL,NULL),(119,'33','VA','VG',NULL,NULL,7,'1','2020-02-20 08:00:00','eliu','2020-02-21 10:08:00','','IC000014',NULL,NULL),(120,'33','VA','VG',NULL,NULL,1,'1','2020-02-21 08:00:00','lchen2','2020-02-21 13:57:22','','IC000017',NULL,NULL),(121,'33','VA','VG',NULL,NULL,2,'2','2020-02-21 08:00:00','lchen2','2020-02-21 13:57:22','','IC000017',NULL,NULL),(122,'33','VA','VG',NULL,NULL,-1,'1','2020-02-21 08:00:00','lchen2','2020-02-21 14:01:03','','IC000000',NULL,NULL),(123,'32','VA','VG',NULL,NULL,1,'1','2020-02-21 08:00:00','lchen2','2020-02-21 14:01:03','','IC000000',NULL,NULL),(124,'32','VA','VM',NULL,NULL,12,'','2020-02-25 08:00:00','eliu','2020-02-25 14:04:29','','IC000019',NULL,NULL),(125,'32','VA','VM',NULL,NULL,12,'2','2020-02-25 08:00:00','eliu','2020-02-25 14:04:29','','IC000019',NULL,NULL),(126,'33','RI','MI','A','B',10,'1','2020-02-26 08:00:00','lchen2','2020-02-26 13:22:26','','IC000020',NULL,NULL),(127,'33','RI','MI','B','C',5,'2','2020-02-26 08:00:00','lchen2','2020-02-26 13:22:26','','IC000020',NULL,NULL),(128,'33','RI','MI','B','D',3,'3','2020-02-26 08:00:00','lchen2','2020-02-26 13:22:26','','IC000020',NULL,NULL),(129,'33','VI','MI','A','E',3,'4','2020-02-26 08:00:00','lchen2','2020-02-26 13:22:26','','IC000020',NULL,NULL),(130,'33','RI','VI','E','B',2,'5','2020-02-26 08:00:00','lchen2','2020-02-26 13:22:26','','IC000020',NULL,NULL),(131,'33','RI','MI','A','B',100,'1','2020-02-25 08:00:00','lchen2','2020-02-27 10:02:02','','IC000021',NULL,NULL),(132,'33','VI','MI','B','C',5,'2','2020-02-26 08:00:00','lchen2','2020-02-27 10:02:02','','IC000021',NULL,NULL),(133,'33','VI','MI','B','D',5,'3','2020-02-23 08:00:00','lchen2','2020-02-27 10:02:02','','IC000021',NULL,NULL),(134,'34','RI','MI','A','B',100,'1','2020-02-20 08:00:00','lchen2','2020-02-27 10:10:26','','IC000022',NULL,NULL),(135,'34','RI','MI','B','C',10,'2','2020-02-21 08:00:00','lchen2','2020-02-27 10:10:26','','IC000022',NULL,NULL),(136,'34','RI','MI','B','D',5,'3','2020-02-22 08:00:00','lchen2','2020-02-27 10:10:26','','IC000022',NULL,NULL),(137,'34','RI','MI','D','E',5,'4','2020-02-22 08:00:00','lchen2','2020-02-27 10:10:26','','IC000022',NULL,NULL),(138,'34','RI','MI','A','B',15,'lot1','2020-02-26 08:00:00','lchen2','2020-03-02 10:30:25','','IC000023',NULL,NULL),(139,'34','RI','MI','B','C',10,'lot2','2020-02-27 08:00:00','lchen2','2020-03-02 10:30:25','','IC000023',NULL,NULL),(140,'34','RI','MI','C','D',5,'lot3','2020-02-28 08:00:00','lchen2','2020-03-02 10:30:25','','IC000023',NULL,NULL),(141,'35','RI','MI','A','B',5,'lot1','2020-03-03 08:00:00','lchen2','2020-03-03 10:32:20','','IC000024',NULL,NULL),(142,'35','RI','MI','A','C',10,'lot2','2020-03-03 08:00:00','lchen2','2020-03-03 10:32:20','','IC000024',NULL,NULL),(143,'35','RI','MI','A','D',5,'lot3','2020-03-03 08:00:00','lchen2','2020-03-03 10:32:20','','IC000024',NULL,NULL),(144,'35','RI','MI','E','A',30,'lot4','2020-03-03 08:00:00','lchen2','2020-03-03 10:32:20','','IC000024',NULL,NULL);
/*!40000 ALTER TABLE `tb_inv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_inv_code`
--

DROP TABLE IF EXISTS `tb_inv_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_inv_code` (
  `inv_code` varchar(2) NOT NULL COMMENT 'codeName',
  `inv_desc` varchar(50) DEFAULT NULL COMMENT 'codeDesc',
  `vmi` tinyint(4) NOT NULL DEFAULT '0' COMMENT '供应商库存',
  `inv_rank` int(11) NOT NULL DEFAULT '1' COMMENT '分类的排序值 被使用的越多数值越大',
  PRIMARY KEY (`inv_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_inv_code`
--

LOCK TABLES `tb_inv_code` WRITE;
/*!40000 ALTER TABLE `tb_inv_code` DISABLE KEYS */;
INSERT INTO `tb_inv_code` VALUES ('MI','MI描述',0,3),('RI','RI描述',0,1),('VG','Geroge VMI INV',1,1),('VI','VI描述',0,2),('VM','ME VMI INV',1,1);
/*!40000 ALTER TABLE `tb_inv_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_item`
--

DROP TABLE IF EXISTS `tb_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_item` (
  `item_number` int(6) NOT NULL AUTO_INCREMENT COMMENT '部件号',
  `item_desc` varchar(100) NOT NULL COMMENT '描述',
  `item_brand` varchar(20) NOT NULL COMMENT '品牌',
  `item_model` varchar(40) NOT NULL COMMENT '型号',
  `vmi` tinyint(4) NOT NULL DEFAULT '0' COMMENT '供应商库存',
  `safe_vmi` int(6) NOT NULL DEFAULT '0' COMMENT '安全库存数',
  `expire_date` int(6) DEFAULT NULL,
  `item_unit` varchar(10) NOT NULL DEFAULT '0' COMMENT '单位',
  `create_user` varchar(40) DEFAULT NULL COMMENT '添加人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `modify_user` varchar(40) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:启用;1:禁用',
  PRIMARY KEY (`item_number`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_item`
--

LOCK TABLES `tb_item` WRITE;
/*!40000 ALTER TABLE `tb_item` DISABLE KEYS */;
INSERT INTO `tb_item` VALUES (2,'物品描述Edit','物品品牌Edit','型号Edit',1,11,360,'件','lchen2','2019-12-04 05:14:49','lchen2','2019-12-26 06:03:10',1),(11,'描述Add','品牌Add','型号Add',1,11,360,'件','lchen2','2019-12-05 07:26:40','lchen2','2019-12-05 07:27:06',1),(12,'sad撒多撒edit','sad撒多撒edit','sad撒多撒edit',1,145,360,'件','lchen2','2019-12-05 07:41:07','lchen2','2019-12-31 02:58:44',1),(17,'123descedit','123brandedit','123modeledit',1,123,360,'件','lchen2','2019-12-06 02:49:00','lchen2','2019-12-18 07:46:07',1),(20,'物品描述123阿萨德自行车','物品品牌123阿萨德自行车','物品型号123阿萨德自行车',0,-122,360,'件','lchen2','2019-12-19 08:20:58','lchen2','2019-12-31 02:59:03',1),(21,'描述Test123','品牌Test123','型号Test123',1,-12,360,'件','lchen2','2019-12-26 06:05:05',NULL,NULL,1),(22,'禁用描述1','禁用品牌2','禁用型号3',1,-1,360,'件','lchen2','2020-01-02 05:27:54','lchen2','2020-01-02 05:31:01',1),(23,'test描述1','test品牌2','test型号3',0,213,123,'m','lchen2','2020-01-02 05:28:41','lchen2','2020-01-16 09:58:19',1),(29,'test123物品描述123','test123物品品牌1234','test123物品品牌12345678',0,-9,360,'件','lchen2','2020-01-10 05:19:22','lchen2','2020-01-10 05:19:57',0),(30,'test描述1','test品牌1','test描述1',0,0,360,'件','lchen2','2020-01-13 06:54:49',NULL,NULL,1),(32,'描述1','描述1','描述1',1,0,360,'件','lchen2','2020-01-16 16:21:11','lchen2','2020-01-19 13:02:49',1),(33,'test123456wqe','test123456wqe','test123456wqe',1,0,360,'件','lchen2','2020-01-17 10:27:15','lchen2','2020-01-17 10:27:38',1),(34,'NewItem测试','NewItem测试','NewItem测试',0,20,360,'件','lchen2','2020-02-27 10:07:27',NULL,NULL,1),(35,'Item测试描述库存','Item测试品牌库存','Item测试型号库存',0,0,360,'件','lchen2','2020-03-03 10:29:34',NULL,NULL,1),(36,'物品test描述1','物品test品牌1','物品test型号1',1,0,360,'件','lchen2','2020-03-13 13:26:57','lchen2','2020-03-13 13:27:37',1);
/*!40000 ALTER TABLE `tb_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_item_vendor`
--

DROP TABLE IF EXISTS `tb_item_vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_item_vendor` (
  `item_number` int(6) NOT NULL,
  `vend_id` varchar(15) NOT NULL,
  KEY `item_number` (`item_number`),
  KEY `vendor_id` (`vend_id`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`item_number`) REFERENCES `tb_item` (`item_number`),
  CONSTRAINT `vendor_ibfk_1` FOREIGN KEY (`vend_id`) REFERENCES `tb_vendor` (`vend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_item_vendor`
--

LOCK TABLES `tb_item_vendor` WRITE;
/*!40000 ALTER TABLE `tb_item_vendor` DISABLE KEYS */;
INSERT INTO `tb_item_vendor` VALUES (33,'2'),(33,'3'),(32,'1'),(32,'2'),(32,'3'),(36,'1');
/*!40000 ALTER TABLE `tb_item_vendor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_location`
--

DROP TABLE IF EXISTS `tb_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_location` (
  `loc_code` varchar(10) NOT NULL COMMENT '库位编号',
  `loc_desc` varchar(50) DEFAULT NULL COMMENT '库位描述',
  `loc_rank` int(11) NOT NULL DEFAULT '1' COMMENT '分类的排序值 被使用的越多数值越大',
  PRIMARY KEY (`loc_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_location`
--

LOCK TABLES `tb_location` WRITE;
/*!40000 ALTER TABLE `tb_location` DISABLE KEYS */;
INSERT INTO `tb_location` VALUES ('A','A库位描述',1),('B','B库位描述',2),('C','C库位描述',3),('D','D库位描述',4),('E','E库位描述',5);
/*!40000 ALTER TABLE `tb_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_material_plan`
--

DROP TABLE IF EXISTS `tb_material_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_material_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_number` varchar(6) NOT NULL COMMENT '部件号',
  `qty` int(6) NOT NULL COMMENT '预定数量',
  `mp_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '预定日期',
  `create_user` varchar(40) DEFAULT NULL COMMENT '预定人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  `mp_number` varchar(10) DEFAULT NULL,
  `state` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2051 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_material_plan`
--

LOCK TABLES `tb_material_plan` WRITE;
/*!40000 ALTER TABLE `tb_material_plan` DISABLE KEYS */;
INSERT INTO `tb_material_plan` VALUES (1,'32',1,'2020-02-24 08:00:00','lchen2','2020-02-23 13:43:45','note1','MP000005',1),(2,'33',2,'2020-02-24 08:00:00','lchen2','2020-02-23 13:43:45','note2','MP000005',1),(3,'33',1,'2020-02-24 15:27:35','lchen2','2020-02-23 15:27:53','','MP000006',1),(4,'32',1,'2020-02-24 15:27:35','lchen2','2020-02-23 15:27:53','','MP000006',1),(5,'33',5,'2020-02-28 16:13:19','lchen2','2020-02-24 15:45:25','note1','MP000007',2),(6,'30',2,'2020-02-28 16:13:19','lchen2','2020-02-24 15:45:25','note2','MP000007',2),(7,'23',2,'2020-02-28 16:19:18','lchen2','2020-02-25 13:04:59','note2','MP000008',2),(8,'21',5,'2020-02-28 16:19:18','lchen2','2020-02-25 13:04:59','note3','MP000008',2),(16,'34',5,'2020-03-05 14:46:38','lchen2','2020-02-27 10:11:36','note1','MP000013',2),(17,'34',5,'2020-03-05 14:46:38','lchen2','2020-02-27 10:11:36','note2','MP000013',2),(18,'34',2,'2020-03-05 14:46:38','lchen2','2020-02-27 10:11:36','note3','MP000013',2),(19,'34',8,'2020-02-28 16:19:18','lchen2','2020-02-27 16:20:48','note4','MP000008',2),(20,'34',8,'2020-02-28 16:19:18','lchen2','2020-02-27 16:22:46','note4','MP000008',2),(2006,'34',5,'2020-02-29 16:21:21','lchen2','2020-02-28 16:21:45','','MP000018',1),(2007,'34',6,'2020-02-29 16:21:21','lchen2','2020-02-28 16:21:45','','MP000018',1),(2010,'35',20,'2020-03-04 10:45:00','lchen2','2020-03-03 10:45:09','','MP000023',1),(2011,'35',5,'2020-03-05 10:59:22','lchen2','2020-03-04 11:01:19','','MP000024',2),(2012,'17',8,'2020-03-05 14:46:38','lchen2','2020-03-04 14:47:59','note4','MP000013',2),(2013,'35',5,'2020-03-05 11:20:03','lchen2','2020-03-05 11:08:16','note1','MP000025',2),(2014,'34',5,'2020-03-05 11:20:03','lchen2','2020-03-05 11:08:16','note2','MP000025',2),(2015,'32',2,'2020-03-05 11:20:03','lchen2','2020-03-05 11:21:12','note333','MP000025',2),(2016,'35',5,'2020-02-28 16:13:19','lchen2','2020-03-05 11:30:41','note3','MP000007',2),(2017,'34',5,'2020-02-28 16:13:19','lchen2','2020-03-05 11:30:41','note3','MP000007',2),(2019,'34',3,'2020-03-06 13:52:27','lchen2','2020-03-05 13:53:59','','MP000026',2),(2021,'35',3,'2020-03-06 14:16:12','lchen2','2020-03-05 14:17:09','','MP000028',2),(2022,'34',1,'2020-03-06 14:16:12','lchen2','2020-03-05 14:17:09','','MP000028',2),(2025,'35',2,'2020-03-03 13:04:58','lchen2','2020-03-06 14:30:56','','MP000019',2),(2026,'34',3,'2020-03-03 13:04:58','lchen2','2020-03-06 14:31:31','','MP000019',2),(2027,'32',2,'2020-03-03 13:04:58','lchen2','2020-03-06 14:31:31','','MP000019',2),(2028,'35',2,'2020-03-07 14:41:01','lchen2','2020-03-06 14:41:25','note1','MP000029',1),(2029,'34',5,'2020-03-07 14:41:01','lchen2','2020-03-06 14:41:25','note2','MP000029',1),(2030,'33',5,'2020-03-07 14:41:01','lchen2','2020-03-06 14:41:25','note3','MP000029',1),(2031,'35',6,'2020-03-10 12:58:43','lchen2','2020-03-09 12:59:52','note1','MP000030',2),(2032,'34',5,'2020-03-10 12:58:43','lchen2','2020-03-09 13:00:10','note2','MP000030',2),(2033,'33',2,'2020-03-10 12:58:43','lchen2','2020-03-09 13:00:46','note3','MP000030',2),(2034,'35',1,'2020-03-11 11:00:49','lchen2','2020-03-10 11:01:16','','MP000031',0),(2035,'34',2,'2020-03-11 11:00:49','lchen2','2020-03-10 11:01:16','','MP000031',0),(2036,'35',2,'2020-03-11 12:39:54','lchen2','2020-03-10 12:40:00','','MP000032',0),(2037,'32',3,'2020-03-06 13:52:27','lchen2','2020-03-10 14:09:06','','MP000026',2),(2039,'33',2,'2020-03-06 13:52:27','lchen2','2020-03-10 14:11:48','','MP000026',2),(2040,'33',2,'2020-03-16 14:22:21','lchen2','2020-03-10 14:22:38','note1','MP000033',0),(2041,'32',1,'2020-03-16 14:22:21','lchen2','2020-03-10 14:22:38','note2','MP000033',0),(2042,'35',2,'2020-03-16 14:22:21','lchen2','2020-03-10 14:23:03','note3','MP000033',0),(2043,'35',1,'2020-03-11 14:45:47','lchen2','2020-03-10 14:46:04','','MP000034',0),(2044,'34',2,'2020-03-11 14:45:47','lchen2','2020-03-10 14:46:04','','MP000034',0),(2046,'33',10,'2020-03-12 13:48:04','lchen2','2020-03-11 13:50:08','note1','MP000036',0),(2047,'34',1,'2020-03-12 13:48:04','lchen2','2020-03-11 13:57:31','note2','MP000036',0),(2048,'33',96,'2020-03-06 13:54:21','lchen2','2020-03-12 13:11:58','','MP000027',2),(2049,'35',2,'2020-03-21 13:16:14','lchen2','2020-03-12 13:16:59','','MP000037',2),(2050,'34',2,'2020-03-21 13:16:14','lchen2','2020-03-12 13:18:37','','MP000037',2);
/*!40000 ALTER TABLE `tb_material_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_role` (
  `role_name` varchar(20) NOT NULL,
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES ('admin'),('inv_add'),('inv_search'),('item');
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_trans_code`
--

DROP TABLE IF EXISTS `tb_trans_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_trans_code` (
  `from_inv` varchar(40) NOT NULL COMMENT 'from_inv',
  `to_inv` varchar(40) NOT NULL COMMENT 'to_inv',
  `operator` varchar(10) NOT NULL COMMENT '运算符',
  PRIMARY KEY (`from_inv`,`to_inv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_trans_code`
--

LOCK TABLES `tb_trans_code` WRITE;
/*!40000 ALTER TABLE `tb_trans_code` DISABLE KEYS */;
INSERT INTO `tb_trans_code` VALUES ('MI','RI','+'),('MI','VI','-'),('RI','MI','+'),('RI','VI','-'),('VA','VG','+'),('VA','VM','+'),('VI','MI','+'),('VI','RI','+');
/*!40000 ALTER TABLE `tb_trans_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_unit`
--

DROP TABLE IF EXISTS `tb_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_unit` (
  `unit_name` varchar(200) NOT NULL,
  PRIMARY KEY (`unit_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_unit`
--

LOCK TABLES `tb_unit` WRITE;
/*!40000 ALTER TABLE `tb_unit` DISABLE KEYS */;
INSERT INTO `tb_unit` VALUES ('m'),('个'),('件'),('套');
/*!40000 ALTER TABLE `tb_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_userrole`
--

DROP TABLE IF EXISTS `tb_userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_userrole` (
  `user_name` varchar(200) NOT NULL,
  `role_name` varchar(20) NOT NULL COMMENT '角色权限',
  `full_name` varchar(45) NOT NULL,
  `vend_id` varchar(15) NOT NULL,
  PRIMARY KEY (`user_name`,`vend_id`),
  KEY `role_name` (`role_name`),
  KEY `userrole_vend_idx` (`vend_id`),
  CONSTRAINT `userrole_ibfk_1` FOREIGN KEY (`role_name`) REFERENCES `tb_role` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_userrole`
--

LOCK TABLES `tb_userrole` WRITE;
/*!40000 ALTER TABLE `tb_userrole` DISABLE KEYS */;
INSERT INTO `tb_userrole` VALUES ('bhuang','admin','','2'),('eliu','admin','Elvis Liu','1'),('lchen2','admin','Lauren Chen','1');
/*!40000 ALTER TABLE `tb_userrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_vendor`
--

DROP TABLE IF EXISTS `tb_vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_vendor` (
  `vend_id` varchar(15) NOT NULL,
  `vend_name` varchar(45) NOT NULL,
  `vmi` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) DEFAULT '1',
  `vmi_inv` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`vend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_vendor`
--

LOCK TABLES `tb_vendor` WRITE;
/*!40000 ALTER TABLE `tb_vendor` DISABLE KEYS */;
INSERT INTO `tb_vendor` VALUES ('1',' 精英制模实业（深圳）有限公司',1,1,'VM'),('2','乔治费歇尔精密机床（上海）有限公司',1,1,'VG'),('3','德马吉森精机机床贸易有限公司',0,1,''),('4','约克（中国）商贸有限公司',0,1,''),('5','无锡兆田建筑装饰工程有限公司',0,1,''),('6','迪恩易模具科技（深圳）有限公司',0,1,''),('7','固安捷（中国）工业品销售有限责任公司',0,1,'');
/*!40000 ALTER TABLE `tb_vendor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-16 16:26:41
