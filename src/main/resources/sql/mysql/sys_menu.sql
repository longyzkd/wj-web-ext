/*
Navicat MySQL Data Transfer

Source Server         : wj
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : wj

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2014-10-08 11:08:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL auto_increment,
  `component` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  `icon_cls` varchar(255) default NULL,
  `text` varchar(255) default NULL,
  `sort` int(11) default NULL,
  `type` varchar(255) default NULL,
  `parent_id` int(11) default NULL,
  `leaf` varchar(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK5E13532B60A4259E` (`parent_id`),
  CONSTRAINT `sys_menu_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8;
