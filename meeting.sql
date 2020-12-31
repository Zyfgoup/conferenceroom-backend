/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : meeting

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-12-31 14:16:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) NOT NULL COMMENT '管理员用户名',
  `password` varchar(255) NOT NULL COMMENT '管理员密码',
  `role` varchar(255) NOT NULL DEFAULT 'admin' COMMENT '角色是管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1231232144 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1231232143', 'admin', '96e79218965eb72c92a549dd5a330112', 'admin');

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `apply_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `dep_id` varchar(50) NOT NULL COMMENT '申请的部门id',
  `room_id` int(3) NOT NULL COMMENT '会议室id',
  `audit_state` int(1) NOT NULL DEFAULT '0' COMMENT '审核状态 0审核中 1表示通过 2表示未通过',
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间 默认自动放入插入时的时间',
  `audit_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '审核时间',
  `reject_reason` varchar(255) DEFAULT NULL COMMENT '审核未通过理由',
  `deleted` int(1) DEFAULT '0' COMMENT '部门删除申请记录 只是不显示 管理员还是可以查看',
  `e_id` varchar(50) NOT NULL,
  PRIMARY KEY (`apply_id`),
  KEY `fl_2` (`room_id`),
  KEY `fk_1` (`dep_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`dep_id`) REFERENCES `department` (`dep_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fl_2` FOREIGN KEY (`room_id`) REFERENCES `conference_room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apply
-- ----------------------------
INSERT INTO `apply` VALUES ('1', '1273538060037570562', '3', '1', '2020-06-24 00:14:31', '2020-06-24 01:01:18', '', '0', '');
INSERT INTO `apply` VALUES ('4', '1275275170956386305', '4', '1', '2020-06-24 10:08:23', '2020-06-26 03:37:58', null, '0', '');
INSERT INTO `apply` VALUES ('5', '1275275170956386305', '3', '1', '2020-06-24 10:52:11', '2020-06-26 03:38:44', null, '0', '');
INSERT INTO `apply` VALUES ('7', '1273538060037570562', '3', '2', '2020-06-24 16:29:40', '2020-06-25 02:02:07', '不允许使用', '0', '');
INSERT INTO `apply` VALUES ('9', '1273538060037570562', '3', '1', '2020-06-25 22:25:32', '2020-06-26 03:38:45', null, '0', '');
INSERT INTO `apply` VALUES ('10', '1273538060037570562', '3', '1', '2020-06-25 22:25:46', '2020-06-26 03:38:46', null, '0', '');
INSERT INTO `apply` VALUES ('11', '1273538060037570562', '5', '1', '2020-06-25 22:26:03', '2020-06-26 03:47:43', null, '0', '');
INSERT INTO `apply` VALUES ('12', '1273538060037570562', '5', '1', '2020-06-25 22:26:15', '2020-06-26 03:50:55', null, '0', '');
INSERT INTO `apply` VALUES ('13', '1273538060037570562', '5', '1', '2020-06-25 22:26:32', '2020-06-26 03:54:52', null, '0', '');
INSERT INTO `apply` VALUES ('14', '1273538060037570562', '3', '1', '2020-06-25 22:34:01', '2020-06-26 03:57:32', null, '0', '');
INSERT INTO `apply` VALUES ('15', '1273538060037570562', '3', '1', '2020-06-25 22:36:37', '2020-06-26 03:59:19', null, '0', '');
INSERT INTO `apply` VALUES ('16', '1273538060037570562', '5', '1', '2020-06-25 22:37:01', '2020-06-26 17:25:58', null, '0', '');
INSERT INTO `apply` VALUES ('18', '1275275170956386305', '3', '1', '2020-06-26 03:47:15', '2020-06-26 03:48:19', null, '0', '');
INSERT INTO `apply` VALUES ('19', '1275275170956386305', '3', '2', '2020-06-26 03:50:08', '2020-06-26 04:08:27', '时间太长不允许', '0', '');
INSERT INTO `apply` VALUES ('20', '1275275170956386305', '3', '1', '2020-06-26 03:50:41', '2020-07-04 12:36:48', null, '0', '');
INSERT INTO `apply` VALUES ('21', '1273538060037570562', '3', '1', '2020-06-26 03:54:12', '2020-06-26 17:32:49', null, '0', '');
INSERT INTO `apply` VALUES ('22', '1273538060037570562', '3', '1', '2020-06-26 03:57:10', '2020-06-26 03:59:25', null, '0', '');
INSERT INTO `apply` VALUES ('23', '1273538060037570562', '3', '1', '2020-06-26 03:59:03', '2020-06-26 17:18:18', null, '0', '');
INSERT INTO `apply` VALUES ('24', '1273538060037570562', '3', '2', '2020-06-26 17:36:28', '2020-06-26 17:37:27', '时间冲突 ', '0', '');
INSERT INTO `apply` VALUES ('26', '1273545211254628354', '5', '1', '2020-06-26 23:54:38', '2020-06-26 23:54:38', null, '0', '');
INSERT INTO `apply` VALUES ('27', '1273544560097320962', '3', '1', '2020-06-26 23:59:00', '2020-06-26 23:59:00', null, '0', '');
INSERT INTO `apply` VALUES ('28', '1275320444835799041', '5', '1', '2020-06-27 00:00:13', '2020-06-27 00:00:13', null, '0', '');
INSERT INTO `apply` VALUES ('29', '1273538060037570562', '5', '1', '2020-06-30 04:05:27', '2020-06-30 04:05:48', null, '0', '');
INSERT INTO `apply` VALUES ('30', '1273538060037570562', '5', '1', '2020-06-30 04:18:32', '2020-06-30 04:18:46', null, '0', '');
INSERT INTO `apply` VALUES ('31', '1273538060037570562', '6', '1', '2020-07-04 12:33:09', '2020-07-04 12:33:41', null, '0', '');
INSERT INTO `apply` VALUES ('34', '1273538060037570562', '6', '2', '2020-07-04 13:23:42', '2020-07-04 16:24:44', '时间已过', '0', '');
INSERT INTO `apply` VALUES ('36', '1273538060037570562', '6', '1', '2020-07-04 16:24:08', '2020-07-04 16:24:31', null, '0', '');
INSERT INTO `apply` VALUES ('37', '1273538060037570562', '5', '1', '2020-07-28 14:49:10', '2020-07-28 14:49:25', null, '0', '');
INSERT INTO `apply` VALUES ('38', '1273538060037570562', '3', '0', '2020-08-07 15:49:08', null, null, '0', '');
INSERT INTO `apply` VALUES ('39', '1273538060037570562', '4', '0', '2020-08-07 15:51:39', null, null, '0', '');
INSERT INTO `apply` VALUES ('40', '1273538060037570562', '4', '0', '2020-08-11 17:11:15', null, null, '0', '');
INSERT INTO `apply` VALUES ('41', '1273538060037570562', '3', '0', '2020-08-21 11:21:17', null, null, '0', '');
INSERT INTO `apply` VALUES ('42', '1273538060037570562', '3', '0', '2020-08-21 11:23:39', null, null, '0', '');
INSERT INTO `apply` VALUES ('43', '1273538060037570562', '3', '0', '2020-08-21 11:27:14', null, null, '0', '');
INSERT INTO `apply` VALUES ('44', '1273538060037570562', '3', '0', '2020-08-21 11:33:04', null, null, '0', '');
INSERT INTO `apply` VALUES ('45', '1273538060037570562', '4', '0', '2020-08-21 11:37:43', null, null, '0', '');
INSERT INTO `apply` VALUES ('46', '1273538060037570562', '5', '0', '2020-08-21 11:42:21', null, null, '0', '');
INSERT INTO `apply` VALUES ('47', '1273538060037570562', '4', '0', '2020-08-21 11:43:31', null, null, '0', '');
INSERT INTO `apply` VALUES ('48', '1273538060037570562', '3', '0', '2020-08-21 11:44:05', null, null, '0', '');
INSERT INTO `apply` VALUES ('49', '1273538060037570562', '6', '0', '2020-08-21 12:04:47', null, null, '0', '1296648253227036673');
INSERT INTO `apply` VALUES ('50', '1273538060037570562', '3', '0', '2020-08-24 16:47:59', null, null, '0', '1296648253227036673');
INSERT INTO `apply` VALUES ('51', '1273538060037570562', '4', '1', '2020-08-24 16:51:34', '2020-08-24 16:51:35', null, '0', '1296648253227036673');

-- ----------------------------
-- Table structure for conference_record
-- ----------------------------
DROP TABLE IF EXISTS `conference_record`;
CREATE TABLE `conference_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `apply_id` int(11) NOT NULL COMMENT '外键 对申请的id',
  `start_time` datetime NOT NULL COMMENT '会议开始时间',
  `end_time` datetime NOT NULL COMMENT '会议结束时间',
  `theme` varchar(100) NOT NULL COMMENT '会议主题',
  `digest` varchar(255) NOT NULL COMMENT '会议内容摘要',
  `content` longtext COMMENT '会议内容',
  `person_count` int(11) NOT NULL COMMENT '会议人数',
  PRIMARY KEY (`record_id`),
  KEY `con_re_fk_1` (`apply_id`),
  CONSTRAINT `con_re_fk_1` FOREIGN KEY (`apply_id`) REFERENCES `apply` (`apply_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of conference_record
-- ----------------------------
INSERT INTO `conference_record` VALUES ('1', '1', '2020-06-25 00:14:42', '2020-06-26 00:14:46', 'asd', 'sad', 'asdsadsadas', '0');
INSERT INTO `conference_record` VALUES ('2', '4', '2020-06-24 10:30:00', '2020-06-24 11:30:00', '测试主题', '测试摘要', null, '10');
INSERT INTO `conference_record` VALUES ('3', '5', '2020-06-24 02:54:59', '2020-06-24 03:55:02', 'asdas', 'asdasd', '', '12');
INSERT INTO `conference_record` VALUES ('5', '7', '2020-06-26 10:00:00', '2020-06-26 11:00:00', '测试时间冲突', 'asdasdas', null, '31');
INSERT INTO `conference_record` VALUES ('7', '9', '2020-06-27 10:00:00', '2020-06-27 10:30:00', '1111111111', '111111', null, '11');
INSERT INTO `conference_record` VALUES ('8', '10', '2020-06-28 10:00:00', '2020-06-28 10:30:00', '222222', '222222222', null, '22');
INSERT INTO `conference_record` VALUES ('9', '11', '2020-06-29 10:00:00', '2020-06-29 10:30:00', '333333', '33333333', null, '33');
INSERT INTO `conference_record` VALUES ('10', '12', '2020-06-30 10:00:00', '2020-06-30 10:30:00', '444444', '44444444', null, '44');
INSERT INTO `conference_record` VALUES ('11', '13', '2020-07-01 10:00:00', '2020-07-02 10:30:00', '555555', '55555555', null, '55');
INSERT INTO `conference_record` VALUES ('12', '14', '2020-06-30 10:00:00', '2020-06-30 10:30:00', '66666666', '66666666', null, '6');
INSERT INTO `conference_record` VALUES ('13', '15', '2020-06-30 12:00:00', '2020-06-30 13:30:00', '777777', '7777777777', null, '7');
INSERT INTO `conference_record` VALUES ('14', '16', '2020-06-30 12:00:00', '2020-06-30 12:30:00', '88888', '888888', null, '88');
INSERT INTO `conference_record` VALUES ('16', '18', '2020-07-09 10:00:00', '2020-07-09 10:30:00', '按时打算开打打死了', '阿斯顿撒sad', null, '23');
INSERT INTO `conference_record` VALUES ('17', '19', '2020-07-02 10:00:00', '2020-07-02 12:30:00', 'werew', 'sadfsdfsd', null, '22');
INSERT INTO `conference_record` VALUES ('18', '20', '2020-07-03 10:00:00', '2020-07-03 10:30:00', 'safsdgfhfj', 'et wetw4', null, '12');
INSERT INTO `conference_record` VALUES ('19', '21', '2020-07-04 10:00:00', '2020-07-04 10:30:00', 'ylhklkjh', 'ghjghj', null, '11');
INSERT INTO `conference_record` VALUES ('20', '22', '2020-07-10 10:00:00', '2020-07-10 10:30:00', 'asrgv', 'sdfsdg', null, '11');
INSERT INTO `conference_record` VALUES ('21', '23', '2020-07-16 10:00:00', '2020-07-16 10:30:00', 'hjkhasd', 'hjkhjk', null, '11');
INSERT INTO `conference_record` VALUES ('22', '24', '2020-07-17 10:00:00', '2020-07-17 10:30:00', '测试审核时时间冲突', '阿发达奥术大', null, '11');
INSERT INTO `conference_record` VALUES ('24', '26', '2020-07-06 10:00:00', '2020-07-06 10:30:00', '测试紧急申请', '1223423', null, '123');
INSERT INTO `conference_record` VALUES ('25', '27', '2020-07-23 10:00:00', '2020-07-23 10:30:00', '按时打算开打打死了', '21311', null, '23');
INSERT INTO `conference_record` VALUES ('26', '28', '2020-07-17 10:00:00', '2020-07-17 10:30:00', '测试4444', '全都是多阿斯顿撒', null, '111');
INSERT INTO `conference_record` VALUES ('27', '29', '2020-08-01 10:00:00', '2020-08-01 10:30:00', '1111111111', '111111111', null, '11');
INSERT INTO `conference_record` VALUES ('28', '30', '2020-09-01 10:00:00', '2020-09-01 10:30:00', '11112312', '12321312', null, '11');
INSERT INTO `conference_record` VALUES ('29', '31', '2020-07-08 10:00:00', '2020-07-08 10:30:00', '1111111111', '1111111', null, '11');
INSERT INTO `conference_record` VALUES ('32', '34', '2020-07-04 15:00:00', '2020-07-04 23:30:00', '11111111111111', '11111', null, '11');
INSERT INTO `conference_record` VALUES ('34', '36', '2020-07-04 16:40:46', '2020-07-04 17:30:00', '1111111111111', '11111111', null, '11');
INSERT INTO `conference_record` VALUES ('35', '37', '2020-07-28 20:00:00', '2020-07-28 20:30:00', '测试员工申请', '111111111', null, '11');
INSERT INTO `conference_record` VALUES ('36', '38', '2020-08-08 10:00:00', '2020-08-08 10:30:00', '11111111', '11111111', null, '11');
INSERT INTO `conference_record` VALUES ('37', '39', '2020-08-08 10:00:00', '2020-08-08 10:30:00', '111111', '111111', null, '11');
INSERT INTO `conference_record` VALUES ('38', '40', '2020-08-15 10:00:00', '2020-08-15 10:30:00', '111111111', '111111', null, '11');
INSERT INTO `conference_record` VALUES ('39', '41', '2020-08-22 10:00:00', '2020-08-22 10:30:00', '1111111', '11111', null, '11');
INSERT INTO `conference_record` VALUES ('40', '42', '2020-08-23 10:00:00', '2020-08-23 10:30:00', '11222', '11233', null, '11');
INSERT INTO `conference_record` VALUES ('41', '43', '2020-08-24 10:00:00', '2020-08-24 10:30:00', '111111', '112312', null, '12');
INSERT INTO `conference_record` VALUES ('42', '44', '2020-08-26 10:00:00', '2020-08-26 10:30:00', '123123', '111123', null, '12');
INSERT INTO `conference_record` VALUES ('43', '45', '2020-08-22 10:00:00', '2020-08-22 10:30:00', '11111111', '11111', null, '11');
INSERT INTO `conference_record` VALUES ('44', '46', '2020-08-22 10:00:00', '2020-08-22 10:30:00', '12345', '12345', null, '11');
INSERT INTO `conference_record` VALUES ('45', '47', '2020-08-25 10:00:00', '2020-08-25 10:30:00', '12345', '213123', null, '11');
INSERT INTO `conference_record` VALUES ('46', '48', '2020-08-28 10:00:00', '2020-08-28 10:30:00', '123111', '123123', null, '11');
INSERT INTO `conference_record` VALUES ('47', '49', '2020-08-31 10:00:00', '2020-08-31 10:30:00', '777777', '77777', null, '11');
INSERT INTO `conference_record` VALUES ('48', '50', '2020-08-31 10:00:00', '2020-08-31 10:30:00', '1111111111', '12345', null, '11');
INSERT INTO `conference_record` VALUES ('49', '51', '2020-08-31 10:00:00', '2020-08-31 10:30:00', '测试直接申请', '123456', null, '11');

-- ----------------------------
-- Table structure for conference_room
-- ----------------------------
DROP TABLE IF EXISTS `conference_room`;
CREATE TABLE `conference_room` (
  `room_id` int(3) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `room_no` varchar(50) NOT NULL COMMENT '会议室门牌号',
  `room_floor` varchar(50) NOT NULL COMMENT '会议室楼层',
  `room_type` varchar(50) NOT NULL COMMENT '会议室类型',
  `room_size` int(4) NOT NULL COMMENT '会议室容纳人数',
  `room_state` int(1) NOT NULL COMMENT '会议室状态0表示维修 1表示可用',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of conference_room
-- ----------------------------
INSERT INTO `conference_room` VALUES ('3', '302', '14', '小多媒体会议室', '50', '1');
INSERT INTO `conference_room` VALUES ('4', '301', '12', '中媒体会议室', '150', '1');
INSERT INTO `conference_room` VALUES ('5', '302', '5', '大多媒体会议室', '300', '1');
INSERT INTO `conference_room` VALUES ('6', '201', '11', '普通会议室', '30', '1');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `dep_id` varchar(50) NOT NULL COMMENT '主键uuid',
  `dep_no` varchar(50) NOT NULL COMMENT '部门账号作为登录账号',
  `dep_name` varchar(50) NOT NULL COMMENT '部门名称',
  `dep_password` varchar(50) NOT NULL COMMENT '部门密码',
  `dep_person_count` int(4) NOT NULL COMMENT '部门人数',
  `dep_email` varchar(50) NOT NULL COMMENT '部门邮箱 一般账号掌控在一个人手里 通过提醒通知要开会',
  `role` varchar(50) NOT NULL DEFAULT 'user' COMMENT '角色是用户',
  PRIMARY KEY (`dep_id`),
  UNIQUE KEY `dep_no` (`dep_no`) USING BTREE,
  UNIQUE KEY `dep_name` (`dep_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1273538060037570562', '001', '研发部', 'e10adc3949ba59abbe56e057f20f883e', '19', 'zyf333p@qq.com', 'user');
INSERT INTO `department` VALUES ('1273544560097320962', '002', '运营部', 'e10adc3949ba59abbe56e057f20f883e', '19', '6195552@qq.com', 'user');
INSERT INTO `department` VALUES ('1273545211254628354', '003', '市场部', 'e10adc3949ba59abbe56e057f20f883e', '19', '619122ss@qq.com', 'user');
INSERT INTO `department` VALUES ('1275275170956386305', '007', '公关部', 'e10adc3949ba59abbe56e057f20f883e', '12', '61912662@qq.com', 'user');
INSERT INTO `department` VALUES ('1275320444835799041', '005', '采购部', 'e10adc3949ba59abbe56e057f20f883e', '345', 'asdasd@qq.com', 'user');

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `did` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dname` varchar(50) NOT NULL COMMENT '名称',
  `dnumber` int(11) NOT NULL COMMENT '设备数量',
  `room_id` int(3) NOT NULL COMMENT '会议室id',
  PRIMARY KEY (`did`),
  KEY `device_fk_1` (`room_id`),
  CONSTRAINT `device_fk_1` FOREIGN KEY (`room_id`) REFERENCES `conference_room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', '多媒体投影仪', '0', '3');
INSERT INTO `device` VALUES ('5', 'Intel计算机', '35', '3');
INSERT INTO `device` VALUES ('7', '话筒', '7', '3');
INSERT INTO `device` VALUES ('8', '风扇', '1', '3');
INSERT INTO `device` VALUES ('9', '风扇111', '1', '3');

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

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '00011', '王小明', 'e10adc3949ba59abbe56e057f20f883e', '1273538060037570562', 'user');
INSERT INTO `employee` VALUES ('1288045790806867969', '0001122', '小军1', 'e10adc3949ba59abbe56e057f20f883e', '1273538060037570562', 'user');
INSERT INTO `employee` VALUES ('1296648253227036673', '01111', '邹1', 'e10adc3949ba59abbe56e057f20f883e', '1273538060037570562', 'user');
