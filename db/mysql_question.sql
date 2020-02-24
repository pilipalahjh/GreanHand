create table community.question(
    id int(10) auto_increment,
    creator int,    #创建者
    title varchar(40),  #标题
    msg varchar(100),   #内容
    tag varchar(50), #标签主题
    like_count int(20) default 0, #点赞数
    view_count int(20) default 0, #阅览数
    comment_count int default 0,#评论数
    gmt_create bigint default 0, #创建时间
    gmt_modify bigint default 0, #修改时间
    primary key (id));