/*
Navicat MySQL Data Transfer

Source Server         : 139.199.16.239
Source Server Version : 50721
Source Host           : 139.199.16.239:3306
Source Database       : dance

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-05-11 12:34:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `id` bigint(20) NOT NULL,
  `name` varchar(16) NOT NULL COMMENT '活动名称',
  `appid` varchar(32) NOT NULL COMMENT '自定义密钥',
  `content` text COMMENT '活动内容',
  `detail` text COMMENT '活动描述',
  `regular` text COMMENT '活动规则',
  `address` varchar(128) DEFAULT NULL COMMENT '活动地址',
  `ticket_address` varchar(128) DEFAULT NULL COMMENT '领票地址',
  `total_ticket` int(11) DEFAULT '0' COMMENT '总票数',
  `surplus_ticket` int(11) DEFAULT '0' COMMENT '剩余总票数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户表';

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES ('1', '默认商家', 'DANCE789street', '商家活动内容信息', '商家活动详细描述信息', '商家活动规则', '商家活动地址', '商家活动领票地址', '1000', '1000', '2018-05-11 12:20:50');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `open_id` varchar(64) NOT NULL COMMENT 'openId',
  `subject_id` bigint(20) NOT NULL COMMENT '题目id',
  `is_true` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '是否正确(未作答:-1/错误:0/正确:1)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`open_id`,`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='答题记录表';

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` bigint(20) NOT NULL,
  `title` varchar(16) NOT NULL COMMENT '题头',
  `content` varchar(16) NOT NULL COMMENT '题干',
  `answer` varchar(11) NOT NULL COMMENT '答案',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '等级(简单:1/中等:2/困难:3)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目表';

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', '1', '1', 'A', '1');
INSERT INTO `subject` VALUES ('2', '2', '2', 'C', '1');
INSERT INTO `subject` VALUES ('3', '3', '3', 'D', '1');
INSERT INTO `subject` VALUES ('4', '4', '4', 'B', '1');
INSERT INTO `subject` VALUES ('5', '5', '5', 'A', '1');
INSERT INTO `subject` VALUES ('6', '6', '6', 'B', '1');

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` bigint(20) NOT NULL,
  `name` varchar(16) NOT NULL COMMENT '票名称',
  `content` varchar(64) DEFAULT NULL COMMENT '票内容',
  `detail` varchar(128) DEFAULT NULL COMMENT '票详细描述',
  `current_num` int(11) DEFAULT '0' COMMENT '当前数量',
  `total` int(11) DEFAULT '0' COMMENT '总票数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `valid_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '有效时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票表';

-- ----------------------------
-- Records of ticket
-- ----------------------------
INSERT INTO `ticket` VALUES ('-1', '未知', '未知', '未知', '-1', '-1', '2018-05-11 12:27:44', '2018-05-11 12:27:44');
INSERT INTO `ticket` VALUES ('0', '再接再厉票', '再接再厉票', '再接再厉票', '100', '100', '2018-05-11 12:23:01', '2018-05-11 12:23:01');
INSERT INTO `ticket` VALUES ('1', '普通票', '普通票', '普通票', '400', '400', '2018-05-11 12:23:39', '2018-05-11 12:23:39');
INSERT INTO `ticket` VALUES ('2', '普通会员票', '普通会员票', '普通会员票', '350', '350', '2018-05-11 12:24:01', '2018-05-11 12:24:01');
INSERT INTO `ticket` VALUES ('3', '高级会员票', '高级会员票', '高级会员票', '100', '100', '2018-05-11 12:24:15', '2018-05-11 12:24:15');
INSERT INTO `ticket` VALUES ('4', '至尊会员票', '至尊会员票', '至尊会员票', '50', '50', '2018-05-11 12:24:29', '2018-05-11 12:24:29');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `open_id` varchar(64) NOT NULL,
  `name` varchar(16) NOT NULL COMMENT '姓名',
  `sex` varchar(4) DEFAULT '男' COMMENT '性别(未知/男/女)',
  `age` int(11) DEFAULT '18' COMMENT '年龄',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `qq` varchar(16) DEFAULT NULL COMMENT 'qq号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态(未答题:0/答题中:1/已答题:2)',
  `ticket_id` bigint(20) DEFAULT '-1' COMMENT '票id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `post_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '答案提交时间',
  PRIMARY KEY (`open_id`),
  UNIQUE KEY `uniq_name` (`phone`),
  UNIQUE KEY `uniq_qq` (`qq`),
  KEY `index_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '小明', '男', '18', '123456789', '123456789', '0', '-1', '2018-05-11 12:28:43', '2018-05-11 12:28:43');
INSERT INTO `user` VALUES ('2', '小花', '女', '18', '123456', '123456', '0', '-1', '2018-05-11 12:29:18', '2018-05-11 12:29:18');
