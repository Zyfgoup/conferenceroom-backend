/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : meeting

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-07-28 17:47:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `e_id` varchar(50) NOT NULL,
  `e_no` varchar(255) NOT NULL COMMENT '工号',
  `e_name` varchar(255) NOT NULL,
  `e_password` varchar(255) NOT NULL,
  `dep_id` varchar(50) NOT NULL,
  `role` varchar(255) DEFAULT 'user',
  PRIMARY KEY (`e_id`),
  KEY `e_fk_1` (`dep_id`),
  CONSTRAINT `e_fk_1` FOREIGN KEY (`dep_id`) REFERENCES `department` (`dep_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
