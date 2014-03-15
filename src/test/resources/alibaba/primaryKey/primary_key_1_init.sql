create table IF NOT EXISTS primary_key_table_member (
	next_id int 
) ENGINE = MyISAM;
insert into primary_key_table_member values (0);

create table IF NOT EXISTS primary_key_table_fans (
	next_id int 
) ENGINE = MyISAM;
insert into primary_key_table_fans values (0);

create table IF NOT EXISTS primary_key_table_follow (
	next_id int 
) ENGINE = MyISAM;
insert into primary_key_table_follow values (0);

create table IF NOT EXISTS primary_key_table_impresslabel (
	next_id int 
) ENGINE = MyISAM;
insert into primary_key_table_impresslabel values (0);

create table IF NOT EXISTS primary_key_table_eduexp (
	next_id int 
) ENGINE = MyISAM;
insert into primary_key_table_eduexp values (0);

create table IF NOT EXISTS primary_key_table_workexp (
	next_id int 
) ENGINE = MyISAM;
insert into primary_key_table_workexp values (0);