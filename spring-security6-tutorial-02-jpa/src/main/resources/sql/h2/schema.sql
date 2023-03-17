drop table if exists SECURITY_USER;
create table SECURITY_USER
(
    id       bigint auto_increment primary key,
    username varchar(40)  not null comment '用户名',
    password varchar(128) not null comment '登录密码'
);
