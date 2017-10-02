# xdu-cloudcourse-web

[![Build Status](https://travis-ci.org/muxfe/xdu-cloudcourse-web.svg?branch=master)](https://travis-ci.org/muxfe/xdu-cloudcourse-web.svg?branch=master)

2017

西电云计算课程大作业Web端代码示例及简易文档教程。

# 部署运行此项目

简单概述部署运行此项目的环境和方法，以

- Window7-64位系统
- IntelliJ IDEA-2017.2
- JDK-1.8
- Maven-3.5.0
- SpringBoot-1.5.7

为例，其他版本/工具/类库请自行查找教程示例。

## 准备环境

- 安装[JDK-1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)，并[设置环境变量](http://jingyan.baidu.com/article/925f8cb836b26ac0dde0569e.html)
- 安装IntelliJ IDEA
- 下载此项目源码
  - `git clone https://github.com/muxfe/xdu-cloudcourse-web.git`
  - [打包下载ZIP](https://github.com/muxfe/xdu-cloudcourse-web/archive/master.zip)

## 导入项目

使用IntelliJ IDEA导入此项目，导入时使用Maven作为构建工具，之后下一步到完成即可。导入后需要下载依赖包，先确保电脑联网状态。如未自动下载，可以点击右侧`Maven Project`的`Reimport`继续下载依赖包。

## 配置SpringBoot

点击IDEA右上角的`Run/Debug Configuration`，添加`SpringBoot`，设置`Main class`为`edu.xidian.sselab.cloudcourse.CloudcourseApplication`，`Use classpath of modules`为`cloudcourse`，并修改最上方`Name`为`CloudcourseApplication`，最好点击`Ok`即可。

## 配置Hbase节点

找到并打开`src/main/resources/application.properties`文件，修改其中的`hbase.nodes`右边的值，改为运行此项目当前环境中存在的hbase节点。

## 运行项目

点击IDEA右上角绿色三角形符号，启动项目（也可以选择Debug模式启动）。看到控制台最终输出`Started CloudcourseApplication in 6.345 seconds (JVM running for 8.42)`类字样表示启动成功。可以在浏览器打开`http://localhost:8080`查看项目首页。

## 热更新

配置项目自动编译选项可以不需要手动关闭重启项目，`File`->`Settings`->`Build,Execution,Deployment`->`Compiler`页，勾选右边的`Build project automatically`。

## 使用项目

此项目实现了一个简单的过车统计功能，即从Hbase数据库查询出数据，并在页面上以表格方式呈现出来。启动成功项目后，访问`http://localhost:8080/record`即可浏览此功能，并添加了主键的搜索方式，默认不查询数据，点击查询按钮后才进行查询，可以输入查询条件（之间为且的关系）。

## 开发项目

过车统计为作业基本完成功能，加分项还有轨迹重现和实时报警两个功能页的实现。其中轨迹重现需要用到百度地图，可以在`http://localhost:8080/bmap`看如何使用百度地图的一个简单示例。

### Hint

#### 轨迹重现

定义Restful数据接口，通过Ajax方式查询出数据，然后在页面上以百度地图API实现轨迹的呈现。

#### 实时报警

使用Redis等缓存数据库，不断检查插入信息是否为报警信息，当接收到报警信息后通知前端页面予以呈现。

此处通知前端有两种方式：

- Http 长轮询（单工通信）
- Websocket 双工通信

网页呈现部分可以用颜色较鲜艳的元素来表示报警信息。

## **Q&A**

### 1 依赖下载失败

检查网络连接，并多次尝试`Maven Reimport`。

### 2 项目启动失败

#### 2.1 8080端口被占用

修改`application.properties`中的`server.port`属性为其他未被占用的端口。

#### 2.2 未发现类定义或未找到XX方法等

缺少项目依赖包，参考第1条中的方法，尝试重新下载依赖。

#### 2.3 找不到主类（入口类）

IDEA左上角菜单栏，`File`->`Project Structure`->`Modules`，手动设置`src/main/java`为`Sources`文件夹，`src/main/resources`为`Resources`文件夹。同理设置`Tests`和`Test Resources`。

#### 2.4 JDK不兼容

同样2.3一样在`Project Structure`->`Project`和`Modules`中分别设置此项目选用JDK和`Language Level`为`8`。

### 3 配置文件缺少自动提示或`Event log`中显示未配置Facet

`File`->`Project Structure`->`Facet`->`Spring`，点击右侧面板上的绿色加号，添加项目入口类即可（`CloudcourseApplication`）。

### 4 @Data等Lombok库的注解无效，对象报错：没有setter或getter方法

#### 安装 [Lombok](https://projectlombok.org/) 插件

Lombok 是一个非常好用的 Java 库，如可以不用写非常麻烦的 Getter 和 Setter 方法，通过`File`->`Settings`->`Plugins`搜索`Lombok`插件并安装即可。

#### 启用注解处理器

`File`->`Settings`->`Build,Execution,Deployment`->`Compiler`->`Annotation Processors`页，勾选`Enable annotation processing`。

#### **其他未列错误或问题，请先自行尝试搜索引擎解决。**

# 项目涉及的技术说明

## [SpringBoot](https://projects.spring.io/spring-boot/)

SpringBoot 是一个高度封装和自动化配置的框架，仅使用注解和少量代码就可以快速搭建一个Web程序。可以用[Spring Initializr](https://start.spring.io/)快速初始化一个项目，并自定义选用所需的依赖。

### 文档和教程

- <https://spring.io/guides/gs/spring-boot/>
- <http://www.mkyong.com/tutorials/spring-boot-tutorials/>

## [Thymeleaf](http://www.thymeleaf.org/)

Thymeleaf 是一个模板解析引擎，类似JSP，由于JSP技术在SpringBoot以Jar包部署情况下并不适用，所以使用了近似的模板引擎来对网页文件进行服务端渲染。

### 文档和教程

- <http://www.thymeleaf.org/documentation.html>
- <https://github.com/kolorobot/spring-boot-thymeleaf>
- <https://spring.io/guides/gs/serving-web-content/>
- <http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html>
- <http://www.mkyong.com/spring-boot/spring-boot-hello-world-example-thymeleaf/>

## [jQuery](https://jquery.com)

jQuery 是运行于浏览器端的一个JavaScript库，主要用于操纵DOM和发起HTTP请求。

项目中已引入，可以直接用，版本4.0。

### 文档和教程

- <https://api.jquery.com/>
- <http://www.w3school.com.cn/jquery/>
- <http://www.runoob.com/jquery/jquery-tutorial.html>

## [Bootstrap](https://getbootstrap.com/docs/4.0/examples/)

Bootstrap 是 Twitter 推出的一款前端设计框架，使用预定义的样式和事件快速构建还算好看的Web页面。

项目中已引入，可以直接用，版本4.0。

### 文档和教程

- <https://getbootstrap.com/docs/4.0/getting-started/introduction/>
- <http://www.runoob.com/bootstrap/bootstrap-tutorial.html>
- <https://www.w3cschool.cn/bootstrap/>
- <https://www.zhihu.com/question/20221979>