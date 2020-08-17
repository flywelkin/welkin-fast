# welkin-fast

### 介绍

&emsp;&emsp;基于SpringBoot的一款纯净脚手架。努力打造一款免费开源、注释全、文档全适合新手学习、方便快速二次开发的框架。

&emsp;&emsp;使用Spring Security  + JWT实现无状态服务的权限认证，目前不支持服务端状态管理。

### 技术选项

| 技术            | 名称               | 官网                                       |
| --------------- | ------------------ | ------------------------------------------ |
| SpringBoot      | SpringBoot框架     | https://spring.io/projects/spring-boot     |
| Spring Security | 权限框架           | https://spring.io/projects/spring-security |
| JWT             | Json Web Token     | https://jwt.io                             |
| Mybatis Plus    | 持久层框架         | https://baomidou.com                       |
| Swagger2        | 接口文档、测试框架 | https://swagger.io                         |

### 开发环境

- JDK8.0
- mysql5.7以上
- Redis5.0.3

### 使用说明

&emsp;&emsp;项目使用SpringBoot数据库初始化功能，启动前只需要操作一下步骤：

1. 修改Mysql和Redis配置
2. 创建数据库