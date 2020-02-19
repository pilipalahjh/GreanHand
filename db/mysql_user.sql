# create user table
create table community.user(
id int(10) auto_increment,
name varchar(40),
account_no varchar(20),
token varchar(36),
gmt_create bigint,
gmt_modify bigint ,
primary key(id));