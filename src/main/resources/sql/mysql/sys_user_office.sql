/*
Navicat MySQL Data Transfer

Source Server         : wj
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : wj

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2014-10-08 11:09:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_office`;
CREATE TABLE `sys_user_office` (
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `office_id` bigint(20) NOT NULL COMMENT '部门编号',
  PRIMARY KEY  (`user_id`,`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-部门';
