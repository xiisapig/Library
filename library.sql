/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50037
Source Host           : localhost:3306
Source Database       : library

Target Server Type    : MYSQL
Target Server Version : 50037
File Encoding         : 65001

Date: 2018-02-22 15:22:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bid` int(30) NOT NULL auto_increment,
  `bname` varchar(100) NOT NULL,
  `bauthor` varchar(100) default NULL,
  `btype` varchar(100) NOT NULL,
  `bprice` int(30) default NULL,
  `bpublisher` varchar(100) default NULL,
  `bstore` int(30) default NULL,
  PRIMARY KEY  (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('15', '算法导论', '李世民', '计算机', '29', '清华大学出版社', '19');

-- ----------------------------
-- Table structure for huanshu
-- ----------------------------
DROP TABLE IF EXISTS `huanshu`;
CREATE TABLE `huanshu` (
  `id` int(30) NOT NULL,
  `name` varchar(100) NOT NULL,
  `bid` int(30) NOT NULL,
  `bname` varchar(100) NOT NULL,
  `jtime` varchar(100) NOT NULL,
  `htime` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of huanshu
-- ----------------------------

-- ----------------------------
-- Table structure for jianyi
-- ----------------------------
DROP TABLE IF EXISTS `jianyi`;
CREATE TABLE `jianyi` (
  `id` int(30) NOT NULL,
  `name` varchar(100) NOT NULL,
  `advice` varchar(100) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jianyi
-- ----------------------------

-- ----------------------------
-- Table structure for jieshu
-- ----------------------------
DROP TABLE IF EXISTS `jieshu`;
CREATE TABLE `jieshu` (
  `id` int(30) NOT NULL,
  `name` varchar(100) NOT NULL,
  `bid` int(30) NOT NULL,
  `bname` varchar(100) NOT NULL,
  `jtime` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jieshu
-- ----------------------------
INSERT INTO `jieshu` VALUES ('11111111', '李四', '15', '算法导论', '2018-02-22 15:21:44');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(30) NOT NULL,
  `pwd` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sex` varchar(10) default NULL,
  `depart` varchar(100) default NULL,
  `class` varchar(100) default NULL,
  `tel` varchar(100) default NULL,
  `role` int(2) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('11111111', '123456', '李四', '女', '机械', '121', '1235467798', '0');
INSERT INTO `users` VALUES ('12345678', '12345678', '张三', '男', '计算机科学与技术', '151', '12345678', '1');
SET FOREIGN_KEY_CHECKS=1;
