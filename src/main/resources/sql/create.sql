-- 题目表
DROP TABLE IF EXISTS subject;
CREATE TABLE subject (
  `id` BIGINT PRIMARY KEY,
  `title` VARCHAR(128) NOT NULL COMMENT '题头',
  `content` VARCHAR(128) NOT NULL COMMENT '题干',
  `answer` VARCHAR(11) NOT NULL COMMENT '答案',
  `type` TINYINT NOT NULL DEFAULT 1 COMMENT '等级(简单:1/中等:2/困难:3)'
) ENGINE=innodb, CHARSET=utf8mb4, COMMENT='题目表';

-- 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  `open_id` VARCHAR(64) PRIMARY KEY,
  `name` VARCHAR(16) NOT NULL COMMENT '姓名',
  `sex` VARCHAR(4) DEFAULT '男' COMMENT '性别(未知/男/女)',
  `age` INTEGER DEFAULT 18 COMMENT '年龄',
  `phone` VARCHAR(16) COMMENT '手机号',
  `avatar` VARCHAR(225) COMMENT '头像',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态(未答题:0/答题中:1/已答题:2)',
  `ticket_id` BIGINT DEFAULT -1 COMMENT '票id',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  `post_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '答案提交时间',
  `valid_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '票过期时间',
  INDEX index_name(name),
  UNIQUE INDEX uniq_name(phone)
) ENGINE=innodb, CHARSET=utf8mb4, COMMENT='用户表';

-- 答题记录表
DROP TABLE IF EXISTS record;
CREATE TABLE record (
  `open_id` VARCHAR(64) NOT NULL COMMENT 'openId',
  `subject_id` BIGINT NOT NULL COMMENT '题目id',
  `is_true` TINYINT NOT NULL DEFAULT -1 COMMENT '是否正确(未作答:-1/错误:0/正确:1)',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '申请时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',
  PRIMARY KEY (open_id, subject_id)
) ENGINE=innodb, CHARSET=utf8mb4, COMMENT='答题记录表';

-- 票表
DROP TABLE IF EXISTS ticket;
CREATE TABLE ticket (
  `id` BIGINT PRIMARY KEY,
  `name` VARCHAR(16) NOT NULL COMMENT '票名称',
  `content` VARCHAR(64) COMMENT '票内容',
  `detail` VARCHAR(128) COMMENT '票详细描述',
  `current_num` INTEGER DEFAULT 0 COMMENT '当前数量',
  `total` INTEGER DEFAULT 0 COMMENT '总票数',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  `valid_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '有效时间',
  UNIQUE INDEX uniq_name(name)
) ENGINE=innodb, CHARSET=utf8mb4, COMMENT='票表';

-- 商户表
DROP TABLE IF EXISTS business;
CREATE TABLE business (
  `id` BIGINT PRIMARY KEY,
  `name` VARCHAR(16) NOT NULL COMMENT '活动名称',
  `appid` VARCHAR(32) NOT NULL COMMENT '自定义密钥',
  `content` TEXT DEFAULT NULL COMMENT '活动内容',
  `detail` TEXT DEFAULT NULL COMMENT '活动描述',
  `regular` TEXT DEFAULT NULL COMMENT '活动规则',
  `address` VARCHAR(128) DEFAULT NULL COMMENT '活动地址',
  `ticket_address` VARCHAR(128) DEFAULT NULL COMMENT '领票地址',
  `total_ticket` INTEGER DEFAULT 0 COMMENT '总票数',
  `surplus_ticket` INTEGER DEFAULT 0 COMMENT '剩余总票数',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  UNIQUE INDEX uniq_name(name)
) ENGINE=innodb, CHARSET=utf8mb4, COMMENT='商户表';

-- 明星表
DROP TABLE IF EXISTS star;
CREATE TABLE star (
  `id` BIGINT PRIMARY KEY,
  `name` VARCHAR(16) NOT NULL COMMENT '名字',
  `content` VARCHAR(255) NOT NULL COMMENT '内容',
  `blank1` TEXT DEFAULT NULL COMMENT '内容1',
  `blank2` TEXT DEFAULT NULL COMMENT '内容2',
  `blank3` TEXT DEFAULT NULL COMMENT '内容3',
  `blank4` TEXT DEFAULT NULL COMMENT '内容4',
  `blank5` TEXT DEFAULT NULL COMMENT '内容5',
  `blank6` TEXT DEFAULT NULL COMMENT '内容6',
  `blank7` TEXT DEFAULT NULL COMMENT '内容7',
  `blank8` TEXT DEFAULT NULL COMMENT '内容8',
  UNIQUE INDEX uniq_name(name)
) ENGINE=innodb, CHARSET=utf8mb4, COMMENT='明星表';