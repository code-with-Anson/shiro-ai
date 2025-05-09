-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 47.117.77.234    Database: shiro_online
-- ------------------------------------------------------
-- Server version	8.4.4

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
-- Table structure for table `bill_month_statistics`
--

DROP TABLE IF EXISTS `bill_month_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_month_statistics` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '统计记录ID，自增主键',
  `user_id` bigint NOT NULL COMMENT '用户ID，关联users表',
  `year` int NOT NULL COMMENT '统计年份',
  `month` int NOT NULL COMMENT '统计月份，1-12',
  `total_expense` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '月度总支出金额',
  `total_income` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '月度总收入金额',
  `category_details` json DEFAULT NULL COMMENT '各分类金额明细，JSON格式存储，如：{"分类1":100.00,"分类2":200.00}',
  `expense_category_details` json DEFAULT NULL COMMENT '支出分类明细，JSON格式',
  `income_category_details` json DEFAULT NULL COMMENT '收入分类明细，JSON格式',
  `bill_count` int NOT NULL DEFAULT '0' COMMENT '该月账单总数量',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_year_month` (`user_id`,`year`,`month`) COMMENT '用户每月统计数据唯一索引',
  KEY `idx_user_id` (`user_id`) COMMENT '用户ID索引',
  KEY `idx_year_month` (`year`,`month`) COMMENT '年月复合索引，用于按时间查询'
) ENGINE=InnoDB AUTO_INCREMENT=778 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账单月度统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '账单ID，自增，主键',
  `userid` bigint NOT NULL COMMENT '用户ID，外键，关联user表的id',
  `amount` decimal(10,2) NOT NULL COMMENT '金额，可以为正数或负数',
  `type` int NOT NULL COMMENT '账单类型，收入或支出，使用整数表示',
  `category_id` bigint NOT NULL COMMENT '交易分类ID，用来记录交易的分类',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '消费详情，可为空，用于记录备注信息',
  `date` date NOT NULL COMMENT '账单日期，精确到年月日，由用户前端输入',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除字段，标记是否被删除',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_bills_users` (`userid`),
  KEY `fk_bills_category` (`category_id`),
  CONSTRAINT `fk_bills_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_bills_users` FOREIGN KEY (`userid`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1223 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账单记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类的名称',
  `category_type` int NOT NULL DEFAULT '0' COMMENT '这个属性用来表示该分类属于收入还是支出',
  `user_id` bigint NOT NULL COMMENT '外键，用于关联users表单的id',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，默认当前时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，自动更新为当前时间',
  PRIMARY KEY (`id`),
  KEY `fk_category_users` (`user_id`),
  CONSTRAINT `fk_category_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账单分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片ID，主键，自增',
  `bill_id` bigint DEFAULT NULL COMMENT '关联账单表的ID，外键，可以为空',
  `renew_id` bigint DEFAULT NULL COMMENT '关联周期计费表的ID，外键，可以为空',
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片地址，存储后端返回的URL',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除字段，标记是否被删除',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_images_bills` (`bill_id`),
  KEY `fk_images_renew` (`renew_id`),
  CONSTRAINT `fk_images_bills` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_images_renew` FOREIGN KEY (`renew_id`) REFERENCES `renew_bill` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图片表，存储账单或周期计费相关的图片附件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `renew_bill`
--

DROP TABLE IF EXISTS `renew_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `renew_bill` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '周期计费记录ID，主键，自增',
  `user_id` bigint NOT NULL COMMENT '循环账单关联的用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '周期计费商品名称',
  `details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '周期计费商品备注',
  `category_id` bigint NOT NULL COMMENT '循环计费商品分类ID，用来记录对应的分类',
  `cycle` int NOT NULL COMMENT '计费周期，数字含义由前端解析',
  `beginning` date NOT NULL COMMENT '循环付费开始时间',
  `ending` date NOT NULL COMMENT '循环付费结束时间',
  `renew` int NOT NULL DEFAULT '0' COMMENT '是否自动续费，数字含义由前端解析',
  `cost` decimal(10,2) NOT NULL COMMENT '周期计费的费用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除字段，标记是否被删除',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_renew_bill_users` (`user_id`),
  KEY `fk_renew_bill_category` (`category_id`),
  CONSTRAINT `fk_renew_bill_category` FOREIGN KEY (`category_id`) REFERENCES `renew_category` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_renew_bill_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='周期计费表，存储循环计费信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `renew_category`
--

DROP TABLE IF EXISTS `renew_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `renew_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `name` varchar(10) NOT NULL COMMENT '分类名称',
  `user_id` bigint NOT NULL COMMENT '外键，关联users表的id',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，默认当前时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，自动更新为当前时间',
  PRIMARY KEY (`id`),
  KEY `fk_renew_category_users` (`user_id`),
  CONSTRAINT `fk_renew_category_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='循环付费账单分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL COMMENT '用户ID，主键，是项目中唯一一个由MybatisPlus使用雪花算法生成的id属性，其他都是自增',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称，1到10个字符',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '加密后的密码',
  `sex` int NOT NULL DEFAULT '2' COMMENT '性别，0是男性，1是女性，预留扩展数值支持其他选项',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用于存放用户的邮箱，邮箱是用户登录以及找回密码的重要凭据',
  `avatar` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'https://shiro-1305030750.cos.ap-shanghai.myqcloud.com/01-defaultUserAvatar.jpg' COMMENT '用户头像URL，默认URL由后端管理',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除字段，标记是否被删除',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'shiro_online'
--

--
-- Dumping routines for database 'shiro_online'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-09 21:45:15
