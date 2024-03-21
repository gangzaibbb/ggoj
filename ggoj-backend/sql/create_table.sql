# 数据库初始化
# @author <a href="https://github.com/gangzaibbb">李兆港</a>
#

-- 创建库
create database if not exists ggoj;
-- 切换库
use ggoj;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 题目表
create table if not exists question
(
    id          bigint auto_increment comment 'id' primary key,
    title       varchar(512)                       null comment '标题',
    content     text                               null comment '内容',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    judgeCase   text                               null comment '判断用例（json数组：输入用例，输出用例）',
    judgeConfig text                               null comment '判断配置（json数组：时间限制，内存限制）',
    answer      text                               null comment '答案',
    submitNum   int      default 0                 not null comment '提交数',
    acceptedNum int      default 0                 not null comment '通过数',
    thumbNum    int      default 0                 not null comment '点赞数',
    favourNum   int      default 0                 not null comment '收藏数',
    userId      bigint                             not null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '题目' collate = utf8mb4_unicode_ci;

-- 题目提交表
/**
- 判题状态（枚举值）
  - 0-待判题
  - 1-判题中
  - 2-通过
  - 3-不通过
 */
create table if not exists question_submit
(
    id         bigint auto_increment comment 'id' primary key,
    userId     bigint                             not null comment '提交用户id',
    questionId bigint                             not null comment '题目id',
    language   varchar(128)                       not null comment '编程语言',
    code       text                               not null comment '代码',
    status     int      default 0                 not null comment '判题状态',
    judgeInfo  text                               null comment '判题信息',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除',
    index idx_postId (questionId),
    index idx_userId (userId)
) comment '题目提交';

