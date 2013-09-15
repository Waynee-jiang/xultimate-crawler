CREATE TABLE IF NOT EXISTS `ali_eduexp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `school_name` varchar(300) DEFAULT NULL,
  `educat` int(11) DEFAULT NULL,
  `specialty` varchar(300) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `member_id` varchar(50) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ali_impress_label` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label_name` varchar(30) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `label_name_btree` (`label_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ali_member_impress_label` (
  `member_id` varchar(50) NOT NULL,
  `impress_label_id` int(10) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`member_id`,`impress_label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ali_member_shard` (
  `member_id` varchar(50) NOT NULL,
  `shard_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`member_id`,`shard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ali_workexp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `company_name` varchar(300) DEFAULT NULL,
  `industry` varchar(300) DEFAULT NULL,
  `scale` varchar(100) DEFAULT NULL,
  `department` varchar(150) DEFAULT NULL,
  `position` varchar(150) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `remark` varchar(3000) DEFAULT NULL,
  `member_id` varchar(50) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

