-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: hotel
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `Coupon`
--

DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Coupon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `hotelId` int DEFAULT '-1',
  `couponType` int NOT NULL,
  `couponName` varchar(255) NOT NULL,
  `roomNum` int DEFAULT NULL,
  `target_money` int DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `status` int DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `discount_money` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
--

INSERT INTO `Coupon` (`id`, `description`, `hotelId`, `couponType`, `couponName`, `roomNum`, `target_money`, `discount`, `status`, `start_time`, `end_time`, `discount_money`) VALUES (1,'满500-100优惠！',2,3,'满减优惠券',NULL,500,NULL,1,NULL,NULL,100),(2,'满3间九折优惠',2,2,'多间特惠券',3,NULL,0.9,1,NULL,NULL,NULL),(3,'6月27日-7月1日下单立刻享受九五折优惠',2,4,'限时优惠券',NULL,NULL,0.95,1,'2020-06-27','2020-07-01',NULL),(4,'国庆节出游享九折优惠',2,5,'节日优惠券',NULL,NULL,0.9,1,'2020-10-01','2020-10-08',NULL),(5,'个人会员专属生日优惠',-1,1,'生日优惠券',NULL,NULL,0.9,1,NULL,NULL,NULL),(6,'企业会员专属入住优惠',-1,6,'企业优惠券',NULL,NULL,0.8,1,NULL,NULL,NULL);

--
-- Table structure for table `CreditRecord`
--

DROP TABLE IF EXISTS `CreditRecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CreditRecord` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `user_id` int NOT NULL,
  `change_action` varchar(255) DEFAULT NULL,
  `change_time` varchar(255) DEFAULT NULL,
  `change_value` double(255,0) DEFAULT NULL,
  `after_change_value` double(255,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CreditRecord`
--


--
-- Table structure for table `FavorList`
--

DROP TABLE IF EXISTS `FavorList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FavorList` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `hotel_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FavorList`
--


--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Hotel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hotelName` varchar(255) NOT NULL,
  `hotelDescription` varchar(255) DEFAULT NULL,
  `hotelDetail` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `bizRegion` varchar(255) DEFAULT NULL,
  `hotelStar` varchar(255) DEFAULT NULL,
  `phoneNum` varchar(255) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `manager_id` int DEFAULT NULL,
  `evaluatorNum` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

INSERT INTO `Hotel` (`id`, `hotelName`, `hotelDescription`, `hotelDetail`, `address`, `bizRegion`, `hotelStar`, `phoneNum`, `rate`, `manager_id`, `evaluatorNum`) VALUES (1,'桔子水晶南京新街口酒店','浪漫情侣、商务出行、地铁周边','酒店地处繁华的商业中心，紧邻地铁一号线，邻近夫子庙、1912街区、总统府等景点，周边金融、餐饮、休闲配套资源一应俱全，宾客出行便捷。 ','南京 秦淮区 中山南路三元巷5号（地铁新街口15张府园1）','XinJieKou','Four',2147483647,4,5,1),(2,'乐优居精品公寓','酒店公寓、浪漫情侣、地铁周边','乐优居精品公寓（南京雨花客厅店）位于南京市雨花台区软件谷核心地带甲级生态办公领域，汇聚华为、VIVO、中兴、京东等430余家国内外高新企业，毗邻区府各行政部门，离建邺区河西各大高新企业、文化体育休闲公园仅数步之遥；约1分钟步行至地铁1号线天隆寺站，地铁1号连通新街口、南京南站、奥体中心等繁华地带。雨花客厅E-PARK集购物、休闲、体验、娱乐、餐饮等多功能综合体；让您出门居家旅游及商务出差尽享便利。','南京 雨花台区 软件大道109号雨花客厅6栋1楼大厅','HeXi','Three',2147483647,4,6,1),(3,'南京夜泊秦淮·南都会酒店','休闲度假、亲子酒店','南京夜泊秦淮·南都会酒店位于南京市秦淮区钓鱼台，定位为传承中国城市文化的街区高端休闲生活空间。</br>酒店核心思想是梳理和引导一种以秦淮历史与文化语言的原生态的休闲生活方式，成为未来南京秦淮区域具备自己语言的作品典范。酒店设计核心理念：东方文化；生态休闲；城市精品；专享服务。酒店融合园林艺术与金陵文化相结合的整体设计风格，展示了金陵大地的历史典故与文化传统。为喜爱美居、静养、闲适的精英提供了艺术文化与生活美学的城市休闲空间，这里不仅能收获尊重和尊贵；更有品质、品味的享受。酒店目前委托浙江君亭酒店管理股份','南京 秦淮区 钓鱼台路56号','XianLin','Five',2147483647,4,7,1),(4,'南京金鹰珠江壹号国际酒店','休闲度假、商务出行、地铁周边','南京金鹰珠江壹号国际酒店地处南京新街口商圈内，傲居城市中轴中山路与珠江路黄金交汇点，城市CBD中央，省市政府两大政务中心聚集地，紧邻南京大学、东南大学两大著名学府，距总统府、玄武湖等主要景点约5分钟车程；同时酒店与地铁1号线及金鹰国际购物中心（珠江路店）紧密直连，购物中心汇聚众多国际一线名品、G-Mart超市、亲子乐园、特色餐饮等。','南京 玄武区 珠江路1号','ZhuJiangLu','Five',2147483647,4,8,1);

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderList` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `hotelId` int DEFAULT NULL,
  `hotelName` varchar(255) DEFAULT NULL,
  `checkInDate` varchar(255) DEFAULT NULL,
  `checkOutDate` varchar(255) DEFAULT NULL,
  `roomType` varchar(255) DEFAULT NULL,
  `roomNum` int DEFAULT NULL,
  `peopleNum` int DEFAULT NULL,
  `haveChild` tinytext,
  `createDate` varchar(255) DEFAULT NULL,
  `price` decimal(65,2) DEFAULT NULL,
  `clientName` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `orderState` varchar(255) DEFAULT NULL,
  `evaluation` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

INSERT INTO `OrderList` (`id`, `userId`, `hotelId`, `hotelName`, `checkInDate`, `checkOutDate`, `roomType`, `roomNum`, `peopleNum`, `haveChild`, `createDate`, `price`, `clientName`, `phoneNumber`, `orderState`, `evaluation`)
VALUES (1,9,2,'乐优居精品公寓','2020-07-01','2020-07-02','Family',1,1,'0','2020-06-24',299.00,'Jack','12345678919','已预订',''),
       (2,9,2,'乐优居精品公寓','2020-07-01','2020-07-02','Family',1,1,'0','2020-06-24',299.00,'Jack','12345678919','已撤销',''),
       (3,9,2,'乐优居精品公寓','2020-06-10','2020-06-12','Family',1,1,'0','2020-06-01',299.00,'Jack','12345678919','已完成','不好'),
       (4,9,2,'乐优居精品公寓','2020-06-20','2020-06-21','Family',1,1,'0','2020-06-19',299.00,'Jack','12345678919','异常',''),
       (5,9,2,'乐优居精品公寓','2020-06-25','2020-06-26','Family',1,1,'0','2020-06-24',299.00,'Jack','12345678919','已申诉','');

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `curNum` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `hotel_id` int DEFAULT NULL,
  `roomType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

INSERT INTO `Room` (`id`, `price`, `curNum`, `total`, `hotel_id`, `roomType`) VALUES (1,799,10,10,1,'Family'),(2,199,20,20,1,'BigBed'),(3,299,30,30,2,'DoubleBed'),(4,399,13,10,2,'Family'),(5,122,7,0,3,'BigBed'),(6,399,10,10,3,'Family'),(7,299,30,30,4,'DoubleBed'),(8,399,10,10,4,'Family');

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `credit` double(255,0) DEFAULT NULL,
  `usertype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `email`, `password`, `username`, `phonenumber`, `credit`, `usertype`) VALUES (1,'admin@qq.com','123456','管理员',NULL,NULL,'Manager'),(2,'saleMan1@qq.com','123456','网站营销人员1号','2581314',NULL,'SaleMen'),(3,'saleMan2@qq.com','123456','网站营销人员2号','2581314',NULL,'SaleMen'),(4,'saleMan3@qq.com','123456','网站营销人员3号','2581314',NULL,'SaleMen'),(5,'hotelManager1@qq.com','123456','桔子水晶南京新街口酒店工作人员','777777',1000,'HotelManager'),(6,'hotelManager2@qq.com','123456','乐优居精品公寓工作人员','777777',1000,'HotelManager'),(7,'hotelManager3@qq.com','123456','南京夜泊秦淮·南都会酒店工作人员','777777',1000,'HotelManager'),(8,'hotelManager4@qq.com','123456','南京金鹰珠江壹号国际酒店工作人员','777777',1000,'HotelManager'),(9,'user1@qq.com','123456','测试用户1号','12345678911',100,'Client'),(10,'user2@qq.com','123456','测试用户2号','12345678911',100,'Client'),(11,'user3@qq.com','123456','测试用户3号','12345678911',100,'Client');

--
-- Table structure for table `Vip`
--

DROP TABLE IF EXISTS `Vip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Vip` (
  `user_id` int NOT NULL,
  `vip_type` smallint DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `register_date` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vip`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-28 22:35:49
