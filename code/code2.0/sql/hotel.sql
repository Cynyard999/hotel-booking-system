/*
 Navicat Premium Data Transfer

 Source Server         : cyz
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost
 Source Database       : Hotel

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : utf-8

 Date: 03/13/2020 17:19:11 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Hotel`
-- ----------------------------
DROP TABLE IF EXISTS `Hotel`;
CREATE TABLE `Hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotelName` varchar(255) NOT NULL,
  `hotelDescription` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `bizRegion` varchar(255) DEFAULT NULL,
  `hotelStar` varchar(255) DEFAULT NULL,
  `phoneNum` varchar(255) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `Hotel`
-- ----------------------------
BEGIN;
INSERT INTO `Hotel` VALUES ('1', '汉庭酒店', '欢迎您入住', null, 'XiDan', 'Four', '1829373819', '4.8', '1'), ('2', '儒家酒店', '欢迎您入住', '南京市鼓楼区珠江路268号', 'XiDan', 'Four', '1829373819', '4.8', '2'), ('5', '锦江酒店', '欢迎入住', '北京海淀', 'XiDan', 'Four', '12345678189', '4.8', '6');
COMMIT;

-- ----------------------------
--  Table structure for `OrderList`
-- ----------------------------
DROP TABLE IF EXISTS `OrderList`;
CREATE TABLE `OrderList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `hotelId` int(11) DEFAULT NULL,
  `hotelName` varchar(255) DEFAULT NULL,
  `checkInDate` varchar(255) DEFAULT NULL,
  `checkOutDate` varchar(255) DEFAULT NULL,
  `roomType` varchar(255) DEFAULT NULL,
  `roomNum` int(255) DEFAULT NULL,
  `peopleNum` int(255) DEFAULT NULL,
  `haveChild` tinytext,
  `createDate` varchar(255) DEFAULT NULL,
  `price` decimal(65,0) DEFAULT NULL,
  `clientName` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `OrderList`
-- ----------------------------
BEGIN;
INSERT INTO `OrderList` VALUES ('1', '4', '1', null, '2020-03-03 00:00:00.000000', '2020-03-04 00:00:00.000000', 'BigBed', '1', '2', '1', '2020-03-03 00:00:00.000000', '199', null, null), ('3', '4', '1', null, null, '2020-03-04 00:00:00.000000', 'BigBed', '1', '1', '0', '2020-03-03 00:00:00.000000', '199', null, null), ('4', '4', '1', null, null, '2020-03-06 00:00:00.000000', 'BigBed', '2', '2', '0', '2020-03-04 00:00:00.000000', '398', null, null), ('5', '4', '1', null, null, '2020-03-06 00:00:00.000000', 'BigBed', '2', '2', '0', '2020-03-04 00:00:00.000000', '398', null, null), ('6', '4', '1', null, null, '2020-03-09 00:00:00.000000', 'BigBed', '2', '2', '0', '2020-03-12 00:00:00.000000', '398', null, null), ('7', '4', '1', null, null, '2020-03-09 00:00:00.000000', 'BigBed', '2', '2', '0', '2020-03-12 00:00:00.000000', '398', null, null), ('8', '4', '1', null, '2020-03-08 00:00:00.000000', '2020-03-09 00:00:00.000000', 'BigBed', '2', '2', '0', '2020-03-12 00:00:00.000000', '398', null, null), ('9', '4', '1', '汉庭酒店', '2020-03-08', '2020-03-09', 'BigBed', '2', '2', '0', '2020-03-12', '398', '测试一号', '12345678919');
COMMIT;

-- ----------------------------
--  Table structure for `Room`
-- ----------------------------
DROP TABLE IF EXISTS `Room`;
CREATE TABLE `Room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `curNum` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `roomType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `Room`
-- ----------------------------
BEGIN;
INSERT INTO `Room` VALUES ('2', '199', '7', '20', '1', 'BigBed'), ('3', '299', '30', '30', '1', 'DoubleBed'), ('4', '399', '10', '10', '1', 'Family');
COMMIT;

-- ----------------------------
--  Table structure for `User`
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `credit` double(255,0) DEFAULT NULL,
  `usertype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `User`
-- ----------------------------
BEGIN;
INSERT INTO `User` VALUES ('4', '1012681@qq.com', '123456', '测试一号', '12345678919', '100', 'Client'), ('5', '123@qq.com', '123456', '测试二号', '12345678911', '100', 'Client'), ('6', '333@qq.com', '123456', null, null, null, 'HotelManager');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
