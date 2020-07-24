/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : meeting

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-07-24 10:58:03
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

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
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '部门删除申请记录 只是不显示 管理员还是可以查看',
  PRIMARY KEY (`apply_id`),
  KEY `fl_2` (`room_id`),
  KEY `fk_1` (`dep_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`dep_id`) REFERENCES `department` (`dep_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fl_2` FOREIGN KEY (`room_id`) REFERENCES `conference_room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
