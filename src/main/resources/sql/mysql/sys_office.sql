/*
Navicat MySQL Data Transfer

Source Server         : wj
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : wj

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2014-10-08 11:08:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '编号',
  `parent_id` bigint(20) default NULL COMMENT '父级编号',
  `name` varchar(100) NOT NULL COMMENT '部门名称',
  `type` char(1) default NULL COMMENT '部门类型',
  `grade` char(1) default NULL COMMENT '部门等级',
  `address` varchar(255) default NULL COMMENT '联系地址',
  `zip_code` varchar(100) default NULL COMMENT '邮政编码',
  `master` varchar(100) default NULL COMMENT '负责人',
  `phone` varchar(200) default NULL COMMENT '电话',
  `fax` varchar(200) default NULL COMMENT '传真',
  `email` varchar(200) default NULL COMMENT '邮箱',
  `create_by` varchar(64) default NULL COMMENT '创建者',
  `create_date` datetime default NULL COMMENT '创建时间',
  `update_by` varchar(64) default NULL COMMENT '更新者',
  `update_date` datetime default NULL COMMENT '更新时间',
  `remarks` varchar(255) default NULL COMMENT '备注信息',
  `office_code` varchar(50) NOT NULL,
  `spell_code` varchar(50) NOT NULL,
  `leaf` varchar(20) default NULL,
  `parent_ids` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `sys_office_ibfk_1` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='部门表';
