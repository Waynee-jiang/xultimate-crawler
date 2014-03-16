CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_DATABASE` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_DATABASE(id, name) VALUES(1, 'crawler_db');

CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_TABLE` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `virtual_database_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE(id, name, virtual_database_id) VALUES(1, 'MEMBER', 1);
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE(id, name, virtual_database_id) VALUES(2, 'MEMBER_RELATION', 1);
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE(id, name, virtual_database_id) VALUES(3, 'IMPRESS_LABEL', 1);

CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_TABLE_INTERVAL` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  `virtual_table_id` bigint NOT NULL,
  `start_interval` bigint NOT NULL,
  `end_interval` bigint NOT NULL,
  `available` char(0) DEFAULT NULL,
  `hash_values_count` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_virtualTableId` (`virtual_table_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE_INTERVAL(id, virtual_table_id, start_interval, end_interval, available, hash_values_count) VALUES
(1, 1, 1, 3000000, '', 2),
(2, 1, 3000000, 6000000, '', 2),
(3, 2, 1, 3000000, '', 2),
(4, 2, 3000000, 6000000, '', 2),
(5, 3, 1, 10000000, '', 2);


CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_SOCKET` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ix_address` (`address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_SOCKET(id, address)
VALUES
(1, '192.168.1.2:3306'),
(2, '192.168.1.3:3306');

CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_SOCKET_BIND_RECORD` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `virtual_table_interval_id` bigint NOT NULL,
  `virtual_socket_id` bigint NOT NULL,	
  `hash_values_json` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ix_virtualTableIntervalId_virtualSocketId` (`virtual_table_interval_id`, `virtual_socket_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_SOCKET_BIND_RECORD(id, virtual_table_interval_id, virtual_socket_id, hash_values_json)
VALUES
(1, 1, 1, '[0]'),
(2, 1, 2, '[1]'),
(3, 2, 1, '[0]'),
(4, 2, 2, '[1]'),
(5, 3, 1, '[0]'),
(6, 3, 2, '[1]'),
(7, 4, 1, '[0]'),
(8, 4, 2, '[1]'),
(9, 5, 1, '[0]'),
(10, 5, 2, '[1]');

CREATE TABLE IF NOT EXISTS `XULTIMATE_PARTITIONED_TABLE` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `virtual_socket_bind_record_id` bigint NOT NULL,
  `shard_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ix_virtualSocketBindRecordId` (`virtual_socket_bind_record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_PARTITIONED_TABLE(id, virtual_socket_bind_record_id, shard_id)
VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 1),
(5, 2, 2),
(6, 2, 3),
(7, 3, 4),
(8, 3, 5),
(9, 3, 6),
(10, 4, 4),
(11, 4, 5),
(12, 4, 6),
(13, 5, 1),
(14, 5, 2),
(15, 5, 3),
(16, 5, 4),
(17, 5, 5),
(18, 5, 6),
(19, 6, 1),
(20, 6, 2),
(21, 6, 3),
(22, 6, 4),
(23, 6, 5),
(24, 6, 6),
(25, 7, 7),
(26, 7, 8),
(27, 7, 9),
(28, 7, 10),
(29, 7, 11),
(30, 7, 12),
(31, 8, 7),
(32, 8, 8),
(33, 8, 9),
(34, 8, 10),
(35, 8, 11),
(36, 8, 12),
(37, 9, 1),
(38, 10, 1);

CREATE TABLE IF NOT EXISTS `XULTIMATE_PARTITIONED_TABLE_INTERVAL` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `partitioned_table_id` bigint NOT NULL,
  `start_interval` bigint NOT NULL,
  `end_interval` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ix_partitionedTableId` (`partitioned_table_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_PARTITIONED_TABLE_INTERVAL(id, partitioned_table_id, start_interval, end_interval)
VALUES
(1, 1, 1, 1000000),
(2, 2, 1000000, 2000000),
(3, 3, 2000000, 3000000),
(4, 4, 1, 1000000),
(5, 5, 1000000, 2000000),
(6, 6, 2000000, 3000000),
(7, 7, 1, 1000000),
(8, 8, 1000000, 2000000),
(9, 9, 2000000, 3000000),
(10, 10, 1, 1000000),
(11, 11, 1000000, 2000000),
(12, 12, 2000000, 3000000),
(13, 13, 1, 500000),
(14, 14, 500000, 1000000),
(15, 15, 1000000, 1500000),
(16, 16, 1500000, 2000000),
(17, 17, 2000000, 2500000),
(18, 18, 2500000, 3000000),
(19, 19, 1, 500000),
(20, 20, 500000, 1000000),
(21, 21, 1000000, 1500000),
(22, 22, 1500000, 2000000),
(23, 23, 2000000, 2500000),
(24, 24, 2500000, 3000000),
(25, 25, 3000000, 3500000),
(26, 26, 3500000, 4000000),
(27, 27, 4000000, 4500000),
(28, 28, 4500000, 5000000),
(29, 29, 5000000, 5500000),
(30, 30, 5500000, 6000000),
(31, 31, 3000000, 3500000),
(32, 32, 3500000, 4000000),
(33, 33, 4000000, 4500000),
(34, 34, 4500000, 5000000),
(35, 35, 5000000, 5500000),
(36, 36, 5500000, 6000000),
(37, 37, 1, 10000000),
(38, 38, 1, 10000000);