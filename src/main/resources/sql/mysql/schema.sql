drop table if exists ss_user;

create table sys_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null default 0,
	primary key (id)
);
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
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8;


CREATE TABLE sys_office
(
	id bigint auto_increment NOT NULL COMMENT '编号',
	parent_id bigint NOT NULL COMMENT '父级编号',
	
	name varchar(100) NOT NULL COMMENT '部门名称',
	type char(1) NOT NULL COMMENT '部门类型',
	grade char(1) NOT NULL COMMENT '部门等级',
	address varchar(255) COMMENT '联系地址',
	zip_code varchar(100) COMMENT '邮政编码',
	master varchar(100) COMMENT '负责人',
	phone varchar(200) COMMENT '电话',
	fax varchar(200) COMMENT '传真',
	email varchar(200) COMMENT '邮箱',
	create_by varchar(64) COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by varchar(64) COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	
	PRIMARY KEY (id),
	CONSTRAINT `sys_office_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `sys_office` (`id`)
) COMMENT = '部门表';

CREATE TABLE sys_role
(
	id bigint auto_increment NOT NULL COMMENT '编号',
	
	name varchar(100) NOT NULL COMMENT '角色名称',

	create_by varchar(64) COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by varchar(64) COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	
	PRIMARY KEY (id)
) COMMENT = '角色表';
CREATE TABLE sys_role_menu
(
	role_id bigint NOT NULL COMMENT '角色编号',
	menu_id bigint NOT NULL COMMENT '菜单编号',
	PRIMARY KEY (role_id, menu_id)
) COMMENT = '角色-菜单';

CREATE TABLE sys_user_office
(
	user_id bigint NOT NULL COMMENT '用户编号',
	office_id bigint NOT NULL COMMENT '部门编号',
	PRIMARY KEY (user_id, office_id)
) COMMENT = '用户-部门';

CREATE TABLE sys_user_role
(
	user_id bigint NOT NULL COMMENT '用户编号',
	role_id bigint NOT NULL COMMENT '角色编号',
	PRIMARY KEY (user_id, role_id)
) COMMENT = '用户-角色';
