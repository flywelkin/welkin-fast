-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `welkin-fast`.sys_user (id, user_name, password, nick_name, avatar, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag) VALUES('5948b87a-9edc-4fea-87f2-f56c12b141b8', 'welkin', '$2a$10$URjQvZzBzjr0C5WmhX6RjOMlCUTp/h6k1VurmkierFAB.Uiqau0CS', '超管', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'YzcmCZNvbXocrsz9dm8e', 'admin@qq.com', '13612345678', 1, '7', 'admin', '2018-08-14 11:11:11', 'admin', '2020-04-17 07:17:52', 0);
INSERT INTO `welkin-fast`.sys_user (id, user_name, password, nick_name, avatar, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag) VALUES('77b546e7-a8ea-4076-8161-f76794c3c9d1', 'yuanjg', '$2a$10$URjQvZzBzjr0C5WmhX6RjOMlCUTp/h6k1VurmkierFAB.Uiqau0CS', '袁金广', 'https://iknow-pic.cdn.bcebos.com/500fd9f9d72a60590cfef2f92934349b023bba62?x-bce-https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D450%2C600/sign=a587b23df11f3a295a9dddcaac159007/500fd9f9d72a60590cfef2f92934349b023bba62.jpg', NULL, 'yjg_678@163.com', '18620302903', 1, '', 'welkin', '2021-03-18 17:25:30', 'welkin', '2021-03-18 17:25:30', 0);

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('e32cccaff38347ee8456b64ae4c55324', '超级管理员', 'admin', '超级管理员', 'admin', '2019-01-19 11:11:11', 'admin', '2019-01-19 19:07:18', 0);

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `welkin-fast`.sys_user_role (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time) VALUES ('04f18ceb2a98d678b9f5d62b30478aef', '77b546e7-a8ea-4076-8161-f76794c3c9d1', 'e32cccaff38347ee8456b64ae4c55324', 'welkin', '2021-03-18 17:25:30', 'welkin', '2021-03-18 17:25:30');
INSERT INTO `welkin-fast`.sys_user_role (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time) VALUES ('9a72f1e0-0e7d-45b9-a882-e708bad555ac', '5948b87a-9edc-4fea-87f2-f56c12b141b8', 'e32cccaff38347ee8456b64ae4c55324', 'admin', '2019-01-19 11:11:11', 'admin', '2019-01-19 19:07:18');

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('86d3d552-ed30-4f47-b82f-6e1d0a0046ed', '系统管理', '0', '/sys', 'sys', 0, 'el-icon-setting', 0, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:05:51', 0);

INSERT INTO `sys_menu` VALUES ('64e0baab-6a17-4b2d-97ca-1953a59a1aab', '用户管理', '86d3d552-ed30-4f47-b82f-6e1d0a0046ed', '/sys/user', 'sys:user:list', 1, 'el-icon-server', 0, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:10:09', 0);
INSERT INTO `sys_menu` VALUES ('c2c385a4-a5c7-44dc-8ad9-befc4cd498d4', '查看', '64e0baab-6a17-4b2d-97ca-1953a59a1aab', NULL, 'sys:user:view', 2, NULL, 0, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:05:51', 0);
INSERT INTO `sys_menu` VALUES ('e1e78a35-4e0a-4b6f-aa71-85aba8d40482', '新增', '64e0baab-6a17-4b2d-97ca-1953a59a1aab', NULL, 'sys:user:add', 2, NULL, 1, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:11:22', 0);
INSERT INTO `sys_menu` VALUES ('2c92a85f-d049-4b60-a352-8b9e20651722', '修改', '64e0baab-6a17-4b2d-97ca-1953a59a1aab', NULL, 'sys:user:update', 2, NULL, 2, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:11:26', 0);
INSERT INTO `sys_menu` VALUES ('0d7b7e93-a2b9-4a69-b6ac-051ea56d9c60', '删除', '64e0baab-6a17-4b2d-97ca-1953a59a1aab', NULL, 'sys:user:delete', 2, NULL, 3, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:11:34', 0);

INSERT INTO `sys_menu` VALUES ('f375c797-85bd-456d-a640-8cf31aea8232', '角色管理', '86d3d552-ed30-4f47-b82f-6e1d0a0046ed', '/sys/role', 'sys:role:list', 1, 'el-icon-view', 1, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:10:36', 0);
INSERT INTO `sys_menu` VALUES ('76072985-10bf-4f5f-931e-650a3153d40c', '查看', 'f375c797-85bd-456d-a640-8cf31aea8232', NULL, 'sys:role:view', 2, NULL, 0, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:05:51', 0);
INSERT INTO `sys_menu` VALUES ('0cfe1bbf-7b99-4625-b8c4-fb6720ef0191', '新增', 'f375c797-85bd-456d-a640-8cf31aea8232', '', 'sys:role:add', 2, NULL, 1, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:11:54', 0);
INSERT INTO `sys_menu` VALUES ('e50f7a00-080b-48ba-8640-55e06b37c9ce', '修改', 'f375c797-85bd-456d-a640-8cf31aea8232', NULL, 'sys:role:update', 2, NULL, 2, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:12:00', 0);
INSERT INTO `sys_menu` VALUES ('52db77df-9514-4e03-9bc0-083186c88cfa', '删除', 'f375c797-85bd-456d-a640-8cf31aea8232', NULL, 'sys:role:delete', 2, NULL, 3, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:12:06', 0);

INSERT INTO `sys_menu` VALUES ('14708abc-443c-42a7-89f0-c4014e102b6a', '菜单管理', '86d3d552-ed30-4f47-b82f-6e1d0a0046ed', '/sys/menu', 'sys:menu:list', 1, 'el-icon-menu', 2, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:10:45', 0);
INSERT INTO `sys_menu` VALUES ('59861c05-d58c-4a62-b58b-fb46fbfa4d79', '查看', '14708abc-443c-42a7-89f0-c4014e102b6a', NULL, 'sys:menu:view', 2, NULL, 0, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:05:51', 0);
INSERT INTO `sys_menu` VALUES ('a28890e7-b1df-4836-bfa5-9d49c981a03d', '新增', '14708abc-443c-42a7-89f0-c4014e102b6a', NULL, 'sys:menu:add', 2, NULL, 1, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:12:15', 0);
INSERT INTO `sys_menu` VALUES ('9875e8fb-21f4-4dfc-8200-32696ee5a807', '修改', '14708abc-443c-42a7-89f0-c4014e102b6a', NULL, 'sys:menu:update', 2, NULL, 2, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:12:21', 0);
INSERT INTO `sys_menu` VALUES ('6ab5dceb-f8b3-4b32-8de1-ab543babdcda', '删除', '14708abc-443c-42a7-89f0-c4014e102b6a', NULL, 'sys:menu:delete', 2, NULL, 3, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:12:33', 0);

INSERT INTO `sys_menu` VALUES ('454d7171-b3e4-4568-84af-d17fc8f24f93', '机构管理', '86d3d552-ed30-4f47-b82f-6e1d0a0046ed', '/sys/dept', 'sys:dept:list', 1, 'el-icon-news', 3, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:10:50', 0);
INSERT INTO `sys_menu` VALUES ('e3be4d55-57ad-4f0c-af8a-fb9dd733633b', '查看', '454d7171-b3e4-4568-84af-d17fc8f24f93', NULL, 'sys:dept:view', 2, NULL, 0, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:05:51', 0);
INSERT INTO `sys_menu` VALUES ('e37a0bd4-8db9-44c6-a7b7-16c879418459', '新增', '454d7171-b3e4-4568-84af-d17fc8f24f93', NULL, 'sys:dept:add', 2, NULL, 1, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:12:41', 0);
INSERT INTO `sys_menu` VALUES ('29fb5213-80a3-4f12-9b2e-d0aae079d9c5', '修改', '454d7171-b3e4-4568-84af-d17fc8f24f93', NULL, 'sys:dept:update', 2, NULL, 2, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:12:46', 0);
INSERT INTO `sys_menu` VALUES ('203c7126-b08c-418e-9c5c-c4463ad407b1', '删除', '454d7171-b3e4-4568-84af-d17fc8f24f93', NULL, 'sys:dept:delete', 2, NULL, 2, 'admin', '2020-09-10 20:05:51', 'admin', '2020-09-10 20:12:51', 0);

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '轻尘集团', '0', 0, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('10', '市场部', '5', 0, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('11', '市场部', '6', 0, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('12', '魏国', '3', 0, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('13', '蜀国', '3', 1, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('14', '吴国', '3', 2, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('2', '牧尘集团', '0', 1, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('3', '三国集团', '0', 2, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('4', '上海分公司', '2', 0, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('5', '北京分公司', '1', 1, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('6', '北京分公司', '2', 1, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('7', '技术部', '5', 0, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('8', '技术部', '4', 0, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);
INSERT INTO `sys_dept` VALUES ('9', '技术部', '6', 0, 'admin', '2018-09-23 19:35:22', 'admin', '2018-09-23 19:35:22', 0);


-- ----------------------------
-- Records of chat_friend
-- ----------------------------
INSERT INTO chat_friend (user_id, friend_id) VALUES('5948b87a-9edc-4fea-87f2-f56c12b141b8', '77b546e7-a8ea-4076-8161-f76794c3c9d1');
INSERT INTO chat_friend (user_id, friend_id) VALUES('77b546e7-a8ea-4076-8161-f76794c3c9d1', '5948b87a-9edc-4fea-87f2-f56c12b141b8');
