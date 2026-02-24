-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (arm64)
--
-- Host: localhost    Database: materialOrder
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `materialOrder`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `materialOrder` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `materialOrder`;

--
-- Table structure for table `buyer`
--

DROP TABLE IF EXISTS `buyer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buyer` (
  `buyer_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY (`buyer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer`
--

LOCK TABLES `buyer` WRITE;
/*!40000 ALTER TABLE `buyer` DISABLE KEYS */;
INSERT INTO `buyer` VALUES (1,'test','1234','測試員','TW'),(2,'alice','1234','Alice Chen','TW'),(3,'bob','1234','Bob Smith','USA'),(4,'charlie','1234','Charlie Wang','TW'),(5,'david','1234','David Lee','HK'),(6,'eve','1234','Eve Jones','UK'),(7,'frank','1234','Frank Liu','CN'),(8,'grace','1234','Grace Wu','TW'),(9,'hank','1234','Hank Miller','USA'),(10,'ivy','1234','Ivy Lin','TW'),(11,'jack','1234','Jack Wilson','CA'),(12,'aaa','1234','Allen','TW'),(13,'bbb','1234','Peter','JP'),(14,'ccc','1234','Monster','USA'),(15,'ddd','1234','Bobo','TW');
/*!40000 ALTER TABLE `buyer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(45) NOT NULL,
  `customer_country` varchar(45) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Nike Inc.','USA'),(2,'Adidas AG','Germany'),(3,'Puma SE','Germany'),(4,'New Balance','USA'),(5,'Under Armour','USA');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `material_id` int NOT NULL AUTO_INCREMENT,
  `material_name` varchar(45) NOT NULL,
  `stock_qty` int NOT NULL DEFAULT '0',
  `unit_price` int NOT NULL,
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,'Pu',3000,10),(2,'Mesh',2000,5),(3,'Leather',1000,50),(4,'Nylon',2500,9),(5,'Canvas',1500,7);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(45) NOT NULL,
  `customer_id` int NOT NULL,
  `buyer_id` int NOT NULL,
  `material_id` int NOT NULL,
  `supplier_id` int DEFAULT NULL,
  `material_name` varchar(45) NOT NULL,
  `unit_price` int NOT NULL,
  `need_qty` int NOT NULL,
  `stock_qty` int NOT NULL,
  `deduct_qty` int NOT NULL,
  `order_qty` int NOT NULL,
  `total_price` int NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `fk_orders_customer` (`customer_id`),
  KEY `fk_orders_buyer` (`buyer_id`),
  KEY `fk_orders_inventory` (`material_id`),
  CONSTRAINT `fk_orders_buyer` FOREIGN KEY (`buyer_id`) REFERENCES `buyer` (`buyer_id`),
  CONSTRAINT `fk_orders_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_orders_inventory` FOREIGN KEY (`material_id`) REFERENCES `inventory` (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'20260223175411',1,15,1,1,'PU',10,1000,3000,100,900,9000,'2026-02-23 17:54:11'),(2,'20260223175411',1,15,2,2,'Mesh',5,1000,2000,100,900,4500,'2026-02-23 17:54:11'),(3,'20260223175411',1,15,3,9,'Leather',50,1000,1000,100,900,45000,'2026-02-23 17:54:11'),(4,'20260223175411',1,15,4,3,'Nylon',9,1000,2500,100,900,8100,'2026-02-23 17:54:11'),(5,'20260223175411',1,15,5,13,'Canvas',7,1000,1500,100,900,6300,'2026-02-23 17:54:11'),(6,'20260223175846',3,12,1,1,'PU',10,1000,3000,0,1000,10000,'2026-02-23 17:58:46'),(7,'20260223175846',3,12,2,2,'Mesh',5,1000,2000,0,1000,5000,'2026-02-23 17:58:46'),(8,'20260223175846',3,12,3,9,'Leather',50,1000,1000,0,1000,50000,'2026-02-23 17:58:46'),(9,'20260223175846',3,12,4,11,'Nylon',9,1000,2500,0,1000,9000,'2026-02-23 17:58:46'),(10,'20260223175846',3,12,5,12,'Canvas',7,1000,1500,0,1000,7000,'2026-02-23 17:58:46'),(11,'20260223194529',1,15,1,1,'PU',10,100,3000,0,100,1000,'2026-02-23 19:45:29'),(12,'20260223194529',1,15,2,2,'Mesh',5,30,2000,0,30,150,'2026-02-23 19:45:29'),(13,'20260223194529',1,15,3,9,'Leather',50,3,1000,0,3,150,'2026-02-23 19:45:29'),(14,'20260223194529',1,15,4,11,'Nylon',9,3,2500,0,3,27,'2026-02-23 19:45:29');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `supplier_id` int NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(45) NOT NULL,
  `supplier_country` varchar(45) NOT NULL,
  `material_id` int DEFAULT NULL,
  PRIMARY KEY (`supplier_id`),
  KEY `fk_supplier_inventory` (`material_id`),
  CONSTRAINT `fk_supplier_inventory` FOREIGN KEY (`material_id`) REFERENCES `inventory` (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Formosa Plastics','TW',1),(2,'Toray Industries','JP',2),(3,'DuPont','USA',4),(4,'Formosa Plastics','TW',1),(5,'Baosheng Materials','CN',1),(6,'Toray Industries','JP',2),(7,'Teijin Frontier','JP',2),(8,'Prime Leather Co.','IT',3),(9,'Gruppo Mastrotto','IT',3),(10,'DuPont','USA',4),(11,'Invista','USA',4),(12,'Yuanantex','TW',5),(13,'Samsung C&T','KR',5);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-23 21:20:01
