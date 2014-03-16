CREATE TABLE IF NOT EXISTS `MEMBER_1` (
  `id` bigint NOT NULL,
  `alibaba_id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` tinyint DEFAULT NULL,
  `identity` tinyint DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `telephone` varchar(60) DEFAULT NULL,
  `fax` varchar(60) DEFAULT NULL,
  `live_province` varchar(120) DEFAULT NULL,
  `live_city` varchar(120) DEFAULT NULL,
  `address` varchar(240) DEFAULT NULL,
  `pcode` varchar(60) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `company_url_in_alibaba` varchar(300) DEFAULT NULL,
  `credit_year` int DEFAULT NULL,
  `head_image_url` varchar(300) DEFAULT NULL,
  `birthday` datetime DEFAULT '0000-00-00 00:00:00',
  `blood_type` tinyint DEFAULT NULL,
  `hometown` varchar(30) DEFAULT NULL,
  `income` tinyint DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `religion` tinyint DEFAULT NULL,
  `profile` varchar(120) DEFAULT NULL,
  `interests` varchar(200) DEFAULT NULL,
  `self_intr` varchar(3200) DEFAULT NULL,
  `member_acc` varchar(60) DEFAULT NULL,
  `register_time` datetime DEFAULT '0000-00-00 00:00:00',
  `last_login` datetime DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_alibabaId` (`alibaba_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_2` (
  `id` bigint NOT NULL,
  `alibaba_id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` tinyint DEFAULT NULL,
  `identity` tinyint DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `telephone` varchar(60) DEFAULT NULL,
  `fax` varchar(60) DEFAULT NULL,
  `live_province` varchar(120) DEFAULT NULL,
  `live_city` varchar(120) DEFAULT NULL,
  `address` varchar(240) DEFAULT NULL,
  `pcode` varchar(60) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `company_url_in_alibaba` varchar(300) DEFAULT NULL,
  `credit_year` int DEFAULT NULL,
  `head_image_url` varchar(300) DEFAULT NULL,
  `birthday` datetime DEFAULT '0000-00-00 00:00:00',
  `blood_type` tinyint DEFAULT NULL,
  `hometown` varchar(30) DEFAULT NULL,
  `income` tinyint DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `religion` tinyint DEFAULT NULL,
  `profile` varchar(120) DEFAULT NULL,
  `interests` varchar(200) DEFAULT NULL,
  `self_intr` varchar(3200) DEFAULT NULL,
  `member_acc` varchar(60) DEFAULT NULL,
  `register_time` datetime DEFAULT '0000-00-00 00:00:00',
  `last_login` datetime DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_alibabaId` (`alibaba_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_3` (
  `id` bigint NOT NULL,
  `alibaba_id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` tinyint DEFAULT NULL,
  `identity` tinyint DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `telephone` varchar(60) DEFAULT NULL,
  `fax` varchar(60) DEFAULT NULL,
  `live_province` varchar(120) DEFAULT NULL,
  `live_city` varchar(120) DEFAULT NULL,
  `address` varchar(240) DEFAULT NULL,
  `pcode` varchar(60) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `company_url_in_alibaba` varchar(300) DEFAULT NULL,
  `credit_year` int DEFAULT NULL,
  `head_image_url` varchar(300) DEFAULT NULL,
  `birthday` datetime DEFAULT '0000-00-00 00:00:00',
  `blood_type` tinyint DEFAULT NULL,
  `hometown` varchar(30) DEFAULT NULL,
  `income` tinyint DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `religion` tinyint DEFAULT NULL,
  `profile` varchar(120) DEFAULT NULL,
  `interests` varchar(200) DEFAULT NULL,
  `self_intr` varchar(3200) DEFAULT NULL,
  `member_acc` varchar(60) DEFAULT NULL,
  `register_time` datetime DEFAULT '0000-00-00 00:00:00',
  `last_login` datetime DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_alibabaId` (`alibaba_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_4` (
  `id` bigint NOT NULL,
  `alibaba_id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` tinyint DEFAULT NULL,
  `identity` tinyint DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `telephone` varchar(60) DEFAULT NULL,
  `fax` varchar(60) DEFAULT NULL,
  `live_province` varchar(120) DEFAULT NULL,
  `live_city` varchar(120) DEFAULT NULL,
  `address` varchar(240) DEFAULT NULL,
  `pcode` varchar(60) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `company_url_in_alibaba` varchar(300) DEFAULT NULL,
  `credit_year` int DEFAULT NULL,
  `head_image_url` varchar(300) DEFAULT NULL,
  `birthday` datetime DEFAULT '0000-00-00 00:00:00',
  `blood_type` tinyint DEFAULT NULL,
  `hometown` varchar(30) DEFAULT NULL,
  `income` tinyint DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `religion` tinyint DEFAULT NULL,
  `profile` varchar(120) DEFAULT NULL,
  `interests` varchar(200) DEFAULT NULL,
  `self_intr` varchar(3200) DEFAULT NULL,
  `member_acc` varchar(60) DEFAULT NULL,
  `register_time` datetime DEFAULT '0000-00-00 00:00:00',
  `last_login` datetime DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_alibabaId` (`alibaba_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_5` (
  `id` bigint NOT NULL,
  `alibaba_id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` tinyint DEFAULT NULL,
  `identity` tinyint DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `telephone` varchar(60) DEFAULT NULL,
  `fax` varchar(60) DEFAULT NULL,
  `live_province` varchar(120) DEFAULT NULL,
  `live_city` varchar(120) DEFAULT NULL,
  `address` varchar(240) DEFAULT NULL,
  `pcode` varchar(60) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `company_url_in_alibaba` varchar(300) DEFAULT NULL,
  `credit_year` int DEFAULT NULL,
  `head_image_url` varchar(300) DEFAULT NULL,
  `birthday` datetime DEFAULT '0000-00-00 00:00:00',
  `blood_type` tinyint DEFAULT NULL,
  `hometown` varchar(30) DEFAULT NULL,
  `income` tinyint DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `religion` tinyint DEFAULT NULL,
  `profile` varchar(120) DEFAULT NULL,
  `interests` varchar(200) DEFAULT NULL,
  `self_intr` varchar(3200) DEFAULT NULL,
  `member_acc` varchar(60) DEFAULT NULL,
  `register_time` datetime DEFAULT '0000-00-00 00:00:00',
  `last_login` datetime DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_alibabaId` (`alibaba_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_6` (
  `id` bigint NOT NULL,
  `alibaba_id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` tinyint DEFAULT NULL,
  `identity` tinyint DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `telephone` varchar(60) DEFAULT NULL,
  `fax` varchar(60) DEFAULT NULL,
  `live_province` varchar(120) DEFAULT NULL,
  `live_city` varchar(120) DEFAULT NULL,
  `address` varchar(240) DEFAULT NULL,
  `pcode` varchar(60) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `company_url_in_alibaba` varchar(300) DEFAULT NULL,
  `credit_year` int DEFAULT NULL,
  `head_image_url` varchar(300) DEFAULT NULL,
  `birthday` datetime DEFAULT '0000-00-00 00:00:00',
  `blood_type` tinyint DEFAULT NULL,
  `hometown` varchar(30) DEFAULT NULL,
  `income` tinyint DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `religion` tinyint DEFAULT NULL,
  `profile` varchar(120) DEFAULT NULL,
  `interests` varchar(200) DEFAULT NULL,
  `self_intr` varchar(3200) DEFAULT NULL,
  `member_acc` varchar(60) DEFAULT NULL,
  `register_time` datetime DEFAULT '0000-00-00 00:00:00',
  `last_login` datetime DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_alibabaId` (`alibaba_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_IMPRESS_LABEL_1` (
  `member_id` bigint NOT NULL,
  `impress_label_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`impress_label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_IMPRESS_LABEL_2` (
  `member_id` bigint NOT NULL,
  `impress_label_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`impress_label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_IMPRESS_LABEL_3` (
  `member_id` bigint NOT NULL,
  `impress_label_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`impress_label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_IMPRESS_LABEL_4` (
  `member_id` bigint NOT NULL,
  `impress_label_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`impress_label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_IMPRESS_LABEL_5` (
  `member_id` bigint NOT NULL,
  `impress_label_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`impress_label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_IMPRESS_LABEL_6` (
  `member_id` bigint NOT NULL,
  `impress_label_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`impress_label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `EDUEXP_1` (
  `id` bigint NOT NULL,
  `school_name` varchar(300) DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `specialty` varchar(300) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `EDUEXP_2` (
  `id` bigint NOT NULL,
  `school_name` varchar(300) DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `specialty` varchar(300) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `EDUEXP_3` (
  `id` bigint NOT NULL,
  `school_name` varchar(300) DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `specialty` varchar(300) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `EDUEXP_4` (
  `id` bigint NOT NULL,
  `school_name` varchar(300) DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `specialty` varchar(300) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `EDUEXP_5` (
  `id` bigint NOT NULL,
  `school_name` varchar(300) DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `specialty` varchar(300) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `EDUEXP_6` (
  `id` bigint NOT NULL,
  `school_name` varchar(300) DEFAULT NULL,
  `educat` tinyint DEFAULT NULL,
  `specialty` varchar(300) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `WORKEXP_1` (
  `id` bigint NOT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `industry` varchar(300) DEFAULT NULL,
  `scale` varchar(100) DEFAULT NULL,
  `department` varchar(150) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `remark` varchar(3000) DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `WORKEXP_2` (
  `id` bigint NOT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `industry` varchar(300) DEFAULT NULL,
  `scale` varchar(100) DEFAULT NULL,
  `department` varchar(150) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `remark` varchar(3000) DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `WORKEXP_3` (
  `id` bigint NOT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `industry` varchar(300) DEFAULT NULL,
  `scale` varchar(100) DEFAULT NULL,
  `department` varchar(150) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `remark` varchar(3000) DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `WORKEXP_4` (
  `id` bigint NOT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `industry` varchar(300) DEFAULT NULL,
  `scale` varchar(100) DEFAULT NULL,
  `department` varchar(150) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `remark` varchar(3000) DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `WORKEXP_5` (
  `id` bigint NOT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `industry` varchar(300) DEFAULT NULL,
  `scale` varchar(100) DEFAULT NULL,
  `department` varchar(150) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `remark` varchar(3000) DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `WORKEXP_6` (
  `id` bigint NOT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `industry` varchar(300) DEFAULT NULL,
  `scale` varchar(100) DEFAULT NULL,
  `department` varchar(150) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `remark` varchar(3000) DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_FAN_1` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_1` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `MEMBER_FAN_2` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_2` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_3` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_3` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_4` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_4` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_5` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_5` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_6` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_6` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_7` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_7` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_8` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_8` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_9` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_9` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_10` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_10` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_11` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_11` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `MEMBER_FAN_12` (
  `member_id` bigint NOT NULL,
  `fan_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`fan_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 
CREATE TABLE IF NOT EXISTS `MEMBER_FOLLOW_12` (
  `member_id` bigint NOT NULL,
  `follow_member_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`,`follow_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `IMPRESS_LABEL_1` (
  `id` bigint NOT NULL,
  `label_name` varchar(30) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_labelName` (`label_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;