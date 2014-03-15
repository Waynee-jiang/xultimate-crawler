CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_DATABASE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_DATABASE(id, name) VALUES(1, 'crawler');

CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_TABLE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `virtual_database_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE(id, name, virtual_database_id) VALUES(1, 'MEMBER', 1);
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE(id, name, virtual_database_id) VALUES(2, 'MEMBER_FANS', 1);
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE(id, name, virtual_database_id) VALUES(3, 'MEMBER_FOLLOW', 1);
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE(id, name, virtual_database_id) VALUES(4, 'IMPRESS_LABEL', 1);

CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_TABLE_INTERVAL` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL,
  `virtual_table_id` bigint(20) NOT NULL,
  `start_interval` bigint(20) NOT NULL,
  `end_interval` bigint(20) NOT NULL,
  `available` char(0) DEFAULT NULL,
  `hash_values_count` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_virtualTableId` (`virtual_table_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_TABLE_INTERVAL(id, virtual_table_id, start_interval, end_interval, available, hash_values_count) VALUES
(1, 1, 1, 3000000, '', 3),
(2, 1, 3000000, 6000000, '', 3),
(3, 2, 1, 3000000, '', 6),
(4, 2, 3000000, 6000000, '', 6),
(5, 3, 1, 3000000, '', 6),
(6, 3, 3000000, 6000000, '', 6),
(7, 4, 1, 5000000, '', 1),
(8, 4, 3000000, 6000000, '', 1),

CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_SOCKET` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `hash_values_json` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_SOCKET(id, address, hash_values_json)
VALUES
(1, '192.168.1.2:3306', '[0, 1, 2]'),
(2, '192.168.1.3:3306', '[0, 1, 2]'),
(1, '192.168.1.2:3306', '[0, 1, 2, 3, 4, 5]'),
(2, '192.168.1.3:3306', '[0, 1, 2, 3, 4, 5]'),

CREATE TABLE IF NOT EXISTS `XULTIMATE_VIRTUAL_SOCKET_BIND_RECORD` (
  `virtual_table_interval_id` bigint(20) NOT NULL,
  `virtual_socket_id` bigint(20) NOT NULL,	
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`virtual_table_interval_id`, `virtual_socket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_VIRTUAL_SOCKET_BIND_RECORD(virtual_table_interval_id, virtual_socket_id)
VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(2, 5);

CREATE TABLE IF NOT EXISTS `XULTIMATE_PARTITIONED_TABLE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `virtual_socket_id` bigint(20) NOT NULL,
  `shard_id` bigint(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ix_virtualSocketId` (`virtual_socket_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_PARTITIONED_TABLE(id, virtual_socket_id, shard_id)
VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 1),
(4, 2, 2),
(5, 3, 1),
(6, 3, 2),
(7, 4, 1),
(8, 4, 2),
(9, 5, 1);

CREATE TABLE IF NOT EXISTS `XULTIMATE_PARTITIONED_TABLE_INTERVAL` (
  `partitioned_table_id` bigint(20) NOT NULL,
  `start_interval` bigint(20) NOT NULL,
  `end_interval` bigint(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `ix_partitionedTableId` (`partitioned_table_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT IGNORE INTO XULTIMATE_PARTITIONED_TABLE_INTERVAL(partitioned_table_id, start_interval, end_interval)
VALUES
(1, 1, 50),
(2, 50, 100),
(3, 1, 50),
(4, 50, 100),
(5, 100, 200),
(6, 200, 300),
(7, 100, 150),
(8, 150, 300),
(9, 100, 300);