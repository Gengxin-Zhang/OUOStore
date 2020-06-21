# OUOStore

## 项目介绍

`OUOStore`项目是一个单卖家商城，包括前台商城系统及后台管理系统，后端基于SpringBoot+MyBatis-Plus实现，采用Docker容器化部署。前端基于xxx实现。前台商城系统包含首页门户、商品搜索、商品展示、购物车、订单流程、会员中心等模块。后台管理系统包含商品管理、订单管理、会员管理、权限管理、设置等模块。

### 项目演示

前端地址：https://github.com/Gengxin-Zhang/OUOStore/admin-frontend

后端地址：https://github.com/Gengxin-Zhang/OUOStore/backend

#### 后台管理系统

项目演示地址： [http://39.97.237.153/admin/index.html](http://39.97.237.153/admin/index.html)  

![后台管理系统功能演示.jpg](/images/admin.jpg)
![后台管理系统功能演示.png](/images/user.png)

#### 前台商城系统

项目演示地址：[http://39.97.237.153/index.html](http://39.97.237.153/index.html)

![前台商城系统功能演示.png]()

### 组织结构

#### 后端组织结构

```
backend
├── ouostore-admin -- 后台接口
├── ouostore-common -- 工具类及通用代码
├── ouostore-mapper -- 对数据库操作的接口及.xml文件
├── ouostore-model -- 数据模型
├── ouostore-portal -- 前台接口
├── ouostore-security -- 通用SpringSecurity模块
└── ouostore-service -- 通用Service层模块

```

#### 前端组织结构

```
.
├── README.md
├── api -- AP 工厂
├── assets -- 资源
├── components -- 组建
├── jsconfig.json 
├── layouts -- 模板
├── middleware -- 中间件
├── nuxt.config.js
├── package.json
├── pages -- 页面
├── plugins -- 插件
├── server
├── static -- 静态文件
└── store -- vuex
```

### 技术选型

#### 后端技术

| 技术                 | 说明                | 官网                                                 |
| -------------------- | ------------------- | ---------------------------------------------------- |
| SpringBoot           | 容器+MVC框架        | https://spring.io/projects/spring-boot               |
| SpringSecurity       | 认证和授权框架      | https://spring.io/projects/spring-security           |
| MyBatis-Plus         | MyBatis增强ORM框架 | https://mp.baomidou.com/       |
| Swagger-UI           | 文档生产工具        | https://github.com/swagger-api/swagger-ui            |
| RabbitMq             | 消息队列            | https://www.rabbitmq.com/                            |
| SpringTask             | 定时任务            | https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/scheduling.html |
| Redis                | 分布式缓存          | https://redis.io/                                    |
| Docker               | 应用容器引擎        | https://www.docker.com                               |
| JWT                  | JWT认证支持         | https://github.com/jwtk/jjwt                         |
| Lombok               | 简化对象封装工具    | https://github.com/rzwitserloot/lombok               |

#### 前端技术

| 技术       | 说明                  | 官网                                   |
| ---------- | --------------------- | -------------------------------------- |
| Vue        | 前端框架              | https://vuejs.org/                     |
| Vue-router | 路由框架              | https://router.vuejs.org/              |
| Vuex       | 全局状态管理框架      | https://vuex.vuejs.org/                |
| Element    | 前端UI框架            | https://element.eleme.io               |
| Axios      | 前端HTTP框架          | https://github.com/axios/axios         |

## 环境搭建

### 开发工具

| 工具          | 说明                | 官网                                            |
| ------------- | ------------------- | ----------------------------------------------- |
| IDEA          | 开发IDE             | https://www.jetbrains.com/idea/downloa         |
| Visual Studio Code | 开发IDE        | https://code.visualstudio.com/         |
| Redis Desktop Manager  | redis客户端连接工具 | https://redisdesktop.com/               |
| Navicat       | 数据库连接工具      | http://www.formysql.com/xiazai.html             |
| Navicat Data Modeler | 数据库设计工具 | https://www.navicat.com.cn/products/navicat-data-modeler                        |
| ProcessOn     | 流程图绘制工具      | https://www.processon.com/                      |
| Postman       | API接口调试工具      | https://www.postman.com/                        |

### 开发环境

| 工具          | 版本号 | 下载                                                         |
| ------------- | ------ | ------------------------------------------------------------ |
| JDK           | 11    | https://www.oracle.com/java/technologies/javase-jdk11-downloads.html |
| SpringBoot    | 2.1.13.RELEASE    | https://start.spring.io/ |
| Mysql         | 5.7    | https://www.mysql.com/                                       |
| Redis         | 4.0    | https://redis.io/download                                    |
| RabbitMq      | 3.8 | http://www.rabbitmq.com/download.html                        |
| Nginx         | 1.19   | http://nginx.org/en/download.html                            |

