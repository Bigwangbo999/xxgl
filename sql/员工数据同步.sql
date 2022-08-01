-- 数据库同步表数据:在19.168.55.131:1521/orcl XXGL库中执行以下操作 --
create database link dblink_test connect to sshe identified by "admin" USING '192.168.96.248:1521/SJCK';
select * from dba_db_links;
create table SYORGANIZATION as select *from SYORGANIZATION@DBLINK_TEST;
create table SYS_USER as select *from SYS_USER@DBLINK_TEST;

