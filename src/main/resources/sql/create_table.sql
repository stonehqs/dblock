create table catalog  (
id int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
name varchar(50) NOT NULL DEFAULT '' COMMENT '商品名称',
browse_count int(11) NOT NULL DEFAULT 0 COMMENT '浏览数',
version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁，版本号',
PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE table browse (
id int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
cata_id int(11) NOT NULL COMMENT '商品ID',
user varchar(50) NOT NULL DEFAULT '' COMMENT '',
create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

insert into catalog (name, browse_count, version) values('mycatalog', 0, 0);

--for test
--select * from catalog;
--select * from browse

--update catalog set browse_count = 0,version=0;
--truncate table browse;