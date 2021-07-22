-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE `sys_user`
(
    `id`               varchar(64)  NOT NULL COMMENT '编号',
    `user_name`        varchar(50)  NOT NULL COMMENT '用户名',
    `password`         varchar(100) NULL DEFAULT NULL COMMENT '密码',
    `nick_name`        varchar(150) NULL DEFAULT NULL COMMENT '昵称',
    `avatar`           varchar(150) NULL DEFAULT NULL COMMENT '头像',
    `salt`             varchar(40)  NULL DEFAULT NULL COMMENT '加密盐',
    `email`            varchar(100) NULL DEFAULT NULL COMMENT '邮箱',
    `mobile`           varchar(100) NULL DEFAULT NULL COMMENT '手机号',
    `status`           int(4)       NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
    `dept_id`          varchar(64)  NULL DEFAULT NULL COMMENT '机构ID',
    `create_by`        varchar(50)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)  NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)  NULL DEFAULT NULL COMMENT '更新时间',
    `del_flag`         int(4)       NULL DEFAULT 0 COMMENT '是否删除  1：已删除  0：正常',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '用户管理';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE `sys_role`
(
    `id`               varchar(64)  NOT NULL COMMENT '编号',
    `name`             varchar(100) NULL DEFAULT NULL COMMENT '角色名称',
    `role_key`         varchar(100) NULL DEFAULT NULL COMMENT '角色标识',
    `remark`           varchar(100) NULL DEFAULT NULL COMMENT '备注',
    `create_by`        varchar(50)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)  NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)  NULL DEFAULT NULL COMMENT '更新时间',
    `del_flag`         int(4)       NULL DEFAULT 0 COMMENT '是否删除  1：已删除  0：正常',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '角色管理';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE `sys_menu`
(
    `id`               varchar(64)  NOT NULL DEFAULT '' COMMENT '编号',
    `name`             varchar(50)  NULL     DEFAULT NULL COMMENT '菜单名称',
    `parent_id`        varchar(64)  NOT NULL DEFAULT '' COMMENT '父菜单ID，一级菜单为0',
    `url`              varchar(200) NULL     DEFAULT NULL COMMENT '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)',
    `perms`            varchar(500) NULL     DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
    `type`             int(11)      NULL     DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
    `icon`             varchar(50)  NULL     DEFAULT NULL COMMENT '菜单图标',
    `order_num`        int(11)      NULL     DEFAULT NULL COMMENT '排序',
    `create_by`        varchar(50)  NULL     DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)  NULL     DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)  NULL     DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)  NULL     DEFAULT NULL COMMENT '更新时间',
    `del_flag`         int(4)       NULL     DEFAULT 0 COMMENT '是否删除  1：已删除  0：正常',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '菜单管理';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
CREATE TABLE `sys_dept`
(
    `id`               varchar(64) NOT NULL COMMENT '编号',
    `name`             varchar(50) NULL DEFAULT NULL COMMENT '机构名称',
    `parent_id`        varchar(64) NULL DEFAULT NULL COMMENT '上级机构ID，一级机构为0',
    `order_num`        int(11)     NULL DEFAULT NULL COMMENT '排序',
    `create_by`        varchar(50) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50) NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `del_flag`         int(4)      NULL DEFAULT 0 COMMENT '是否删除  1：已删除  0：正常',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '机构管理';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE `sys_user_role`
(
    `id`               varchar(64) NOT NULL COMMENT '编号',
    `user_id`          varchar(64) NULL DEFAULT NULL COMMENT '用户ID',
    `role_id`          varchar(64) NULL DEFAULT NULL COMMENT '角色ID',
    `create_by`        varchar(50) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50) NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '用户角色';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
CREATE TABLE `sys_role_menu`
(
    `id`               varchar(100) NOT NULL COMMENT '编号',
    `role_id`          varchar(100) NULL DEFAULT NULL COMMENT '角色ID',
    `menu_id`          varchar(100) NULL DEFAULT NULL COMMENT '菜单ID',
    `create_by`        varchar(50)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)  NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)  NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '角色菜单';

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
CREATE TABLE `sys_role_dept`
(
    `id`               varchar(100) NOT NULL COMMENT '编号',
    `role_id`          varchar(100) NULL DEFAULT NULL COMMENT '角色ID',
    `dept_id`          varchar(100) NULL DEFAULT NULL COMMENT '机构ID',
    `create_by`        varchar(50)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)  NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)  NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '角色机构';

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
CREATE TABLE `sys_config`
(
    `id`               varchar(100)   NOT NULL COMMENT '编号',
    `value`            varchar(100)   NOT NULL COMMENT '数据值',
    `label`            varchar(100)   NOT NULL COMMENT '标签名',
    `type`             varchar(100)   NOT NULL COMMENT '类型',
    `description`      varchar(100)   NOT NULL COMMENT '描述',
    `sort`             decimal(10, 0) NOT NULL COMMENT '排序（升序）',
    `create_by`        varchar(50)    NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)    NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)    NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)    NULL DEFAULT NULL COMMENT '更新时间',
    `remarks`          varchar(255)   NULL DEFAULT NULL COMMENT '备注信息',
    `del_flag`         int(4)         NULL DEFAULT 0 COMMENT '是否删除  1：已删除  0：正常',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '系统配置表';



-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
CREATE TABLE `sys_dict`
(
    `id`               varchar(100)   NOT NULL COMMENT '编号',
    `value`            varchar(100)   NOT NULL COMMENT '数据值',
    `label`            varchar(100)   NOT NULL COMMENT '标签名',
    `type`             varchar(100)   NOT NULL COMMENT '类型',
    `description`      varchar(100)   NOT NULL COMMENT '描述',
    `sort`             decimal(10, 0) NOT NULL COMMENT '排序（升序）',
    `create_by`        varchar(50)    NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)    NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)    NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)    NULL DEFAULT NULL COMMENT '更新时间',
    `remarks`          varchar(255)   NULL DEFAULT NULL COMMENT '备注信息',
    `del_flag`         int(4)         NULL DEFAULT 0 COMMENT '是否删除  1：已删除  0：正常',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '字典表';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
CREATE TABLE `sys_log`
(
    `id`               varchar(100)  NOT NULL COMMENT '编号',
    `user_name`        varchar(50)   NULL DEFAULT NULL COMMENT '用户名',
    `operation`        varchar(50)   NULL DEFAULT NULL COMMENT '用户操作',
    `method`           varchar(200)  NULL DEFAULT NULL COMMENT '请求方法',
    `params`           varchar(5000) NULL DEFAULT NULL COMMENT '请求参数',
    `time`             bigint(20)    NOT NULL COMMENT '执行时长(毫秒)',
    `ip`               varchar(64)   NULL DEFAULT NULL COMMENT 'IP地址',
    `create_by`        varchar(50)   NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)   NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)   NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)   NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '系统操作日志';

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
CREATE TABLE `sys_login_log`
(
    `id`               varchar(100) NOT NULL COMMENT '编号',
    `user_name`        varchar(50)  NULL DEFAULT NULL COMMENT '用户名',
    `status`           varchar(50)  NULL DEFAULT NULL COMMENT '登录状态（online:在线，登录初始状态，方便统计在线人数；login:退出登录后将online置为login；logout:退出登录）',
    `ip`               varchar(64)  NULL DEFAULT NULL COMMENT 'IP地址',
    `create_by`        varchar(50)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime(0)  NULL DEFAULT NULL COMMENT '创建时间',
    `last_update_by`   varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
    `last_update_time` datetime(0)  NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '系统登录日志';


-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
CREATE TABLE `sys_task` (
    `id`                varchar(64) COLLATE utf8mb4_bin NOT NULL,
    `job_name`          varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务名',
    `description`       varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务描述',
    `cron_expression`   varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'cron表达式',
    `bean_class`        varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
    `job_status`        varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务状态',
    `job_group`         varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '任务分组',
    `create_by`         varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
    `create_time`       datetime DEFAULT NULL COMMENT '创建时间',
    `last_update_by`    varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
    `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT = '定时任务列表';

-- ----------------------------
-- Table structure for chat_friend
-- ----------------------------
CREATE TABLE `chat_friend` (
   `user_id` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
   `friend_id` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '好友ID'
) COMMENT = '好友关系表';

-- ----------------------------
-- Table structure for mini_gif_count
-- ----------------------------
CREATE TABLE `mini_gif_count`
(
    `open_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
    `count`   int(11) DEFAULT NULL,
    PRIMARY KEY (`open_id`)
);