/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7
Source Server Version : 50723
Source Host           : localhost:3307
Source Database       : shirodemo

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-03-25 17:19:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'add', null);
INSERT INTO `permission` VALUES ('2', 'delete', null);
INSERT INTO `permission` VALUES ('3', 'edit', null);
INSERT INTO `permission` VALUES ('4', 'query', null);

-- ----------------------------
-- Table structure for permission_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  KEY `idx_rid` (`rid`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限角色关系表';

-- ----------------------------
-- Records of permission_role
-- ----------------------------
INSERT INTO `permission_role` VALUES ('1', '1');
INSERT INTO `permission_role` VALUES ('1', '2');
INSERT INTO `permission_role` VALUES ('1', '3');
INSERT INTO `permission_role` VALUES ('1', '4');
INSERT INTO `permission_role` VALUES ('2', '1');
INSERT INTO `permission_role` VALUES ('2', '2');
INSERT INTO `permission_role` VALUES ('2', '4');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `rname` varchar(255) NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin');
INSERT INTO `role` VALUES ('2', 'customer');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123');
INSERT INTO `user` VALUES ('2', 'demo', '123');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  KEY `idx_uid` (`uid`),
  KEY `idx_pid` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '2');
