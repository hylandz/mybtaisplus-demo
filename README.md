# mybatisplus-demo
## 一、简介
学习`MyBatis-Plus`框架的,主要实现CRUD,和分页,逻辑删除,乐观锁,字段自动填充功能.采用的是代码注解实现

## 二、快速上手
### 2.1 环境搭建
> pom.xml
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>


<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!--spring-boot-test-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- spring-boot aop -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>


<!-- Spring Boot: mybatis-plus -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.3.1</version>
</dependency>


<!--mybatis-plus generate插件-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.3.1</version>
</dependency>


<!--mysql-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- h2 DataBase -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.200</version>
    <scope>runtime</scope>
</dependency>

<!--thymeleaf,代码自动生成时注释掉,否则会冲突-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- 使用代码自动生成时打开,否则注释掉 -->
<!--<dependency>
       <groupId>org.apache.velocity</groupId>
       <artifactId>velocity-engine-core</artifactId>
       <version>2.1</version>
</dependency>-->

<!--druid-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.21</version>
</dependency>

<!--lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!--统一表达式语言 JSR341-->
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.el</artifactId>
    <version>3.0.1-b11</version>
</dependency>

<!--commons-lang3工具包-->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.5</version>
</dependency>
<!--fastjson-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.57</version>
</dependency>


<!-- wiremock单元测试 -->
<dependency>
    <groupId>com.github.tomakehurst</groupId>
    <artifactId>wiremock</artifactId>
    <version>2.26.3</version>
    <type>pom</type>
    <scope>test</scope>
</dependency>
```
### 2.2数据库设计
> sys_user.sql
```sql
/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : mybatisplusdb

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 05/04/2020 11:25:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门id',
  `nick_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `real_name` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真名',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `token` char(37) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cookie使用',
  `gender` int(1) NULL DEFAULT NULL COMMENT '性别,1:男;2:女;0:保密',
  `birth` date NULL DEFAULT NULL COMMENT '出生日期,yyyy-MM-dd',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除,1:已删除;0:未删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '用户状态,枚举,1:正常,2:锁定等',
  `gmt_create` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```
### 2.3 项目文件结构
```
mybatisplus-demo
├── com.xlx.mpd
│       	└── common
|                  └── advice                     // 全局异常处理
│       	       └── generator                 // 自动生成代码
│       	       └── mpconfig       			 // 功能组件注解配置
│       	
│       	└── system                
│       		   └── controller            // 控制层        
│       	       └── dao                   // DAO层
│       		   └── dto                   //数据对象
│       		   └── entity                // 实体层
│       	       └── enums                 // 枚举
│       	       └── exception             // 自定义异常
│       	       └── service               // 业务层     	       
│       	       └── util                  // 自定义工具类
|
|
├── recesources         
│       └── db           // sql文件
│       └── mapper       // xxxMapper.xml文件
│       └── static                   
│       └── templates
│       └── application.yml        // 主配置文件
│       └── application-dev.yml               // 开发配置 
│       └── application-prod.yml				// 生产配置
│       └── applicationContext.xml			// 使用xml格式配置环境
|
├── test   // 单元测试
|     └── mybatisplus   // 单元测试
|     └── ApplicationTests.java
|
```
### 2.4 代码自动生成
> MyBatisPlusGenerator.java
```java
public class MyBatisPlusGenerator {
  
  public static void main(String[] args) {

    //自动生成器
    AutoGenerator mpg = new AutoGenerator();
    // 1.全局配置
    GlobalConfig gc = new GlobalConfig();
    gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
    gc.setFileOverride(true); //文件覆盖
    gc.setActiveRecord(true);// ActiveRecord
    gc.setEnableCache(false);// XML 二级缓存
    gc.setBaseResultMap(true);// XML ResultMap
    gc.setBaseColumnList(true);// XML sql片段
    gc.setIdType(IdType.AUTO);//主键策略
    gc.setAuthor("xlx");//作者

    // 自定义文件命名，注意 %s 会自动填充表实体属性！
    gc.setMapperName("%sMapper");//xxxMapper接口
    gc.setXmlName("%sMapper");//xxxMapper.xml
    gc.setServiceName("%sService");
    gc.setServiceImplName("%sServiceImpl");
    gc.setControllerName("%sController");//xxxController
    mpg.setGlobalConfig(gc);

    // 2.数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setDbType(DbType.MYSQL);
    //dsc.setTypeConvert(new MySqlTypeConvert());
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("mango");
    dsc.setPassword("root5.7.22");
    dsc.setUrl("jdbc:mysql://localhost:3306/mybatisplusdb?useUnicode=true&characterEncoding=utf8");
    mpg.setDataSource(dsc);

    // 3.策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
    strategy.setTablePrefix("sys_");// 数据库表前缀,生成的实体类名会省略前缀sys_users,users
    strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略(下划线变驼峰式)
    strategy.setEntityLombokModel(true);//lombok注解
    strategy.setInclude("sys_user"); // 需要生成的表
    // strategy.setExclude(new String[]{"test"}); // 排除生成的表
    // 自定义实体父类
    // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
    // 自定义实体，公共字段
    // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
    // 自定义 mapper 父类
    // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
    // 自定义 service 父类
    // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
    // 自定义 service 实现类父类
    // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
    // 自定义 controller 父类
    // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
    // 【实体】是否生成字段常量（默认 false）
    // public static final String ID = "test_id";
    // strategy.setEntityColumnConstant(true);
    // 【实体】是否为构建者模型（默认 false）
    // public User setName(String name) {this.name = name; return this;}
    // strategy.setEntityBuilderModel(true);
    mpg.setStrategy(strategy);

    // 4.包名配置
    PackageConfig pc = new PackageConfig();
    pc.setParent("com.xlx.mpd.system")//父路径
            .setMapper("dao")
            .setService("service")
            .setController("controller")
            .setEntity("entity");
    mpg.setPackageInfo(pc);

    // 5.xml配置
    // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
    InjectionConfig cfg = new InjectionConfig() {
      @Override
      public void initMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
        this.setMap(map);
      }
    };
  
    // 如果模板引擎是 freemarker
    //String templatePath = "/templates/mapper.xml.ftl";
    // 如果模板引擎是 velocity
    String templatePath = "/templates/mapper.xml.vm";
    
    List<FileOutConfig> focList = new ArrayList<>();
    // 自定义 xxList.jsp 生成

    // 调整 xml 生成目录演示
    focList.add(new FileOutConfig(templatePath) {
      @Override
      public String outputFile(TableInfo tableInfo) {
        return System.getProperty("user.dir") +"/src/main/resources/mapper/" + tableInfo.getEntityName()
                       + "Mapper" + StringPool.DOT_XML;
      }
    });
    
    cfg.setFileOutConfigList(focList);
    mpg.setCfg(cfg);

    
    // 关闭默认 xml 生成，调整生成 至 根目录
    TemplateConfig tc = new TemplateConfig();
    tc.setXml(null);
    mpg.setTemplate(tc);


    // 6.执行生成
    mpg.execute();
  }
}
```


### 2.5 yml/xml配置
> application.yml
```yml
#服务配置
server:
  port: 8080

  # spring配置
spring:
  profiles:
    active: @spring.profiles.active@
  # jdbc配置
  datasource:
    driver-class-name: org.h2.Driver
    #schema: classpath:db/schema-h2.sql
    #data: classpath:db/data-h2.sql
    # mem表示在缓存里
    url: jdbc:h2:D://data//test
    username: sa
    password: 123
  #thymeleaf模板
  thymeleaf:
    mode: HTML
    encoding: UTF-8
    cache: false

```
> application-dev.ym
```yml
#服务配置
server:
  port: 8887

  # spring配置
spring:
  datasource:
    druid:
      db-type: com.alibaba.druid.pool.DruidDataSource
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/mybatisplusdb?useSSL=false&useUnicode=true&characterEncoding=utf8
      username: mango
      password: root5.7.22
      # 连接池配置
      initial-size: 5
      min-idle: 5
      max-active: 20
      # 连接等待超时时间
      max-wait: 30000
      # 配置检测可以关闭的空闲连接间隔时间
      time-between-eviction-runs-millis: 60000
      # 配置连接在池中的最小生存时间
      min-evictable-idle-time-millis: 300000
      validation-query: select '1' from dual
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
      # 打开PSCache，并且指定每个连接上PSCache的大小
      pool-prepared-statements: true
      max-open-prepared-statements: 20
      max-pool-prepared-statement-per-connection-size: 20
      #druid的监控
  #thymeleaf模板
  thymeleaf:
    mode: HTML
    encoding: UTF-8
    cache: false
  aop:
    auto: true
    proxy-target-class: true


# mybatis-plus
mybatis-plus:
  mapper-locations: classpath:/mapper/*Mapper.xml
  #实体扫描，多个package用逗号或者分号分隔
  typeAliasesPackage: com.xlx.mpd.system.entity
  typeEnumsPackage:
  # 原生配置
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
    jdbc-type-for-null: null
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

  global-config:
    db-config:
      # 主键自增
      id-type: AUTO
      #逻辑删除配置
      logic-delete-field: deleted # 全局逻辑删除字段为deleted
      # 逻辑已删除值(默认为 1)
      logic-delete-value: 1
      # 逻辑未删除值(默认为 0)
      logic-not-delete-value: 0

```
> application-prod.yml
```yml
#服务配置
server:
  port: 8889

  # spring配置
spring:
  # jdbc配置
  datasource:
    driver-class-name: org.h2.Driver
    #schema: classpath:db/schema-h2.sql
    #data: classpath:db/data-h2.sql
    # mem表示在缓存里
    url: jdbc:h2:D://data//test
    username: sa
    password: 123
  #thymeleaf模板
  thymeleaf:
    mode: HTML
    encoding: UTF-8
    cache: false

```
> applicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
		http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
		http://www.springframework.org/schema/context 
		http://www.springframework.org/schema/context/spring-context-4.0.xsd">
		
		<!-- 引入属性文件 -->
		<context:property-placeholder />
		
		<!-- 自动扫描包 -->
		<context:component-scan base-package="com.xlx.mpd">
			<!-- 除了不扫描controller -->
			<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
		</context:component-scan>
		
		<!-- 配置数据源，以前用的是dbcp ,现在使用的是alibaba的Druid(德鲁伊)数据源 -->
    <bean name="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
       <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
        <property name="url" value="jdbc:mysql://localhost:3306/springbootdb?useUnicode=true&amp;characterEncoding=utf8" />
        <property name="username" value="mango" />
        <property name="password" value="root5.7.22" />
        
        <!-- 初始化连接大小 -->
        <property name="initialSize" value="0" />
        <!-- 连接池最大使用连接数量 -->
        <property name="maxActive" value="20" />
        <!-- 连接池最大空闲 -->
       <!--  <property name="maxIdle" value="20" /> -->
        <!-- 连接池最小空闲 -->
        <property name="minIdle" value="0" />
        <!-- 获取连接最大等待时间 -->
        <property name="maxWait" value="60000" />
        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="60000" />
        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="25200000" />
        <!-- 打开removeAbandoned功能 -->
        <property name="removeAbandoned" value="true" />
        <!-- 1800秒，也就是30分钟 -->
        <property name="removeAbandonedTimeout" value="1800" />
        <!-- 关闭abanded连接时输出错误日志 -->
        <property name="logAbandoned" value="true" />
        <!-- 监控数据库 -->
        <!-- <property name="filters" value="stat" /> -->
        <property name="filters" value="mergeStat" />
    </bean>


    <!--mybatis-plus配置开始-->
    <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
        <!--1. 数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!-- 2. 配置实体扫描路径，多个package可以用分号; 逗号, 分隔， 支持通配符*-->
        <!-- com.a.b.entity;com.a.c.entity;com.d.*.entity-->
        <property name="typeAliasesPackage" value="com.xlx.mpd.system.entity"/>
        <!--3. 自定义配置-->
        <property name="configuration" ref="mybatisConfig"/>
        <!-- 4. MP 全局配置注入 -->
        <property name="globalConfig" ref="globalConfig"/>
        <!--5. 插件-->
        <property name="plugins">
            <array>
                <!-- 分页插件配置 -->
                <bean id="paginationInterceptor"
                      class="com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor"/>
                <!--性能分析插件-->
                <!--<bean class="com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor">
                    <property name="stopProceed" value="true"></property>
                </bean>-->
                <!-- 乐观锁插件 -->
                <bean id="optimisticLockerInterceptor"
                      class="com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor">
                </bean>
                <!-- 性能拦截器，兼打印sql，不建议生产环境配置-->
                <!--<bean id="performanceInterceptor"
                      class="com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor">
                </bean>-->
            </array>
        </property>
    </bean>

    <!--自定义填充字段处理-->
    <bean id="myMetaObjectHandler" class="com.xlx.mpd.common.mpconfig.MyMetaObjectHandler"/>
    <!--mp配置-->
    <bean id="mybatisConfig" class="com.baomidou.mybatisplus.core.MybatisConfiguration">
        <property name="mapUnderscoreToCamelCase" value="true"/>
    </bean>

    <!-- 定义 MP 全局策略 -->
    <bean id="globalConfig" class="com.baomidou.mybatisplus.core.config.GlobalConfig">
        <!-- 逻辑/假删除 定义下面3个参数-->
        <!--<property name="sqlInjector" ref="logicSqlInjector"/>-->
        <!--公共字段填充处理器-->
        <property name="metaObjectHandler" ref="myMetaObjectHandler"/>

    </bean>

    <!--逻辑删除-->
  <!-- <bean id="logicSqlInjector" class="com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill"/>-->

    <!-- 配置mybatis 扫描mapper接口的路径, 相当于注解@MapperScan，@MapperScan("com.baomidou.mybatisplus.test.h2.entity.mapper")-->
    <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.xlx.mpd.system.dao"/>
    </bean>
    
   <!-- 配置Spring的事务管理器bean -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>
    <!--mybatis-plus配置结束-->

    <!-- 配置druid监控spring jdbc -->
    <bean id="druid-stat-interceptor" class="com.alibaba.druid.support.spring.stat.DruidStatInterceptor">
    </bean>
    <bean id="druid-stat-pointcut" class="org.springframework.aop.support.JdkRegexpMethodPointcut" scope="prototype">
        <property name="patterns">
            <list>
                <value>com.xlx.mpd.system.service.*</value>
            </list>
        </property>
    </bean>
    <aop:config>
        <aop:advisor advice-ref="druid-stat-interceptor" pointcut-ref="druid-stat-pointcut" />
    </aop:config>
    <!-- 
    	由于生成代理类有两种方式：JDK和CGLIB，一种是基于接口的，一种是基于类的。
    	如果添加上面的属性则使用基于类的cglib的方式，相反，如果没有写或者是false则通过jdk的基于接口的方式生成代理类
     -->
    
	<aop:aspectj-autoproxy proxy-target-class="true"/>

</beans>
```


### 2.6 相关功能组件注解配置
>  MyMetaObjectHandler.java
```java
/**
 * 字段自动填充实现类
*/
public class MyMetaObjectHandler implements MetaObjectHandler {

  /**
   * 插入时填充
   * FieldFill.INSERT
   */
  @Override
  public void insertFill(MetaObject metaObject) {

    log.info("*************************");
    log.info("插入数据时填充");
    log.info("*************************");

    // 填充当前日期
    this.strictInsertFill(metaObject,"gmtCreate",Long.class,DateTimeUtil.getMilliSecondOfNow());
  }
  
  /**
   * 更新时填充
   * FieldFill.UPDATE
   * @param metaObject 元对象
   */
  @Override
  public void updateFill(MetaObject metaObject) {

    //更新时填充
    log.info("*************************");
    log.info("更新数据时填充");
    log.info("*************************");
  
    // 填充当前日期,可以多个
    this.strictUpdateFill(metaObject,"gmtModified",Long.class,DateTimeUtil.getMilliSecondOfNow());
  }
}
```
> MyBatisPlusConfig.java
```java
/**
 * 组件注解配置
*/
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = "com.xlx.mpd.system.dao.*.mapper*")
public class MyBatisPlusConfig {
    
    /**
     * 分页
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
    
    /**
     * 字段自动填充
     * @return MetaObjectHandler
     */
    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MyMetaObjectHandler();
    }
    
    /**
     * 乐观锁,Version
     * @return OptimisticLockerInterceptor
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }
    
}
```

### 2.7 编写Controller层代码
> UserController.java
```java
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    @ResponseBody
    public ResultDTO listAll(){
        Page<User> page = userService.page(new Page<>());
        List<User> records = page.getRecords();
        
        return ResultDTO.ok().message("用户列表").data("records",records);
    }
    
    @GetMapping("/search")
    @ResponseBody
    public ResultDTO search(@RequestParam  String userName){
        User one = userService.getOne(new QueryWrapper<User>().eq("user_name", userName));
        
        return one != null ? ResultDTO.ok().message("用户查询").data("user",one) : ResultDTO.setResult(ResultCodeEnum.DATA_NOT_FOUND);
    }
    
    @GetMapping("/get")
    @ResponseBody
    public ResultDTO getUser(@RequestParam  String userName){
        User user = userService.queryUserByName(userName);
        return ResultDTO.ok().data("user",user);
    }
    
    @PostMapping("/create")
    @ResponseBody
    public ResultDTO addUser(@RequestBody User user){
        boolean save = userService.save(user);
        log.info("新增用户:" + save);
        return save ? ResultDTO.error().message("新增失败") : ResultDTO.ok().message("新增成功");
    }
    
    @PutMapping("/modify")
    @ResponseBody
    public ResultDTO updateUser(@RequestBody User user){
        boolean update = userService.updateById(user);
        log.info("修改用户:" + update);
        return update ? ResultDTO.ok().message("修改成功") :ResultDTO.error().message("修改失败");
    }
    
    @DeleteMapping("del/{userId}")
    @ResponseBody
    public ResultDTO deleteUser(@PathVariable Long userId){
        boolean remove = userService.removeById(userId);
        log.info("删除用户:" + remove);
        return remove ? ResultDTO.ok().message("删除成功") :ResultDTO.error().message("删除失败");
    }
```
### 2.8 Junit测试
> UserControllerTest.java
```java
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerTest {


    
    @Autowired
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;
    
    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @Test
    public void testQueryUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list").contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.records.size()").value(10));
    }
    
    @Test
    public void testQueryUserByName() throws Exception {
        String username = "F3860565";
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/search").contentType(MediaType.APPLICATION_JSON_UTF8)
                                                         .param("userName", username))
                                         .andExpect(MockMvcResultMatchers.status().isOk())
                                         .andExpect(MockMvcResultMatchers.jsonPath("$.data.user").doesNotExist())
                                         .andReturn().getResponse().getContentAsString();
        log.info("查询用户名为[{}]的结果:{}" ,username,result);
    }
    
    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/get").contentType(MediaType.APPLICATION_JSON_UTF8)
                                .param("userName","admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.userName").value("admin"));
    }
    
    
    @Test
    public void testInsert() throws Exception {
        User user = new User();
        user.setDeptId(5L)
                .setNickName("倔强的啥子")
                .setAvatarUrl("default.jpg")
                .setUserName("F3860565")
                .setRealName("宝海一")
                .setPassword("123456")
                .setSalt("adadadda")
                .setToken(UUID.randomUUID().toString())
                .setBirth(LocalDate.parse("1996-09-19", DateTimeFormatter.ISO_LOCAL_DATE))
                .setEmail("baohaiyi@qq.com")
                .setPhone("13576637348")
                .setGender(GenderEnum.MALE.getGenderNum());
        String content = JSON.toJSONString(user);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                 .content(content))
                                 .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
			.andReturn().getResponse().getContentAsString();
        log.info("新增用户返回的结果:" + result);
    }
    @Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setUserId(13L)
                .setToken(UUID.randomUUID().toString())
                .setVersion(1);
        String content = JSON.toJSONString(user);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/modify").contentType(MediaType.APPLICATION_JSON_UTF8)
                                                .content(content))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                                .andReturn().getResponse().getContentAsString();
        log.info("修改用户返回的结果:" + result);
    }
    @Test
    public void testDelete() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/user/del/14").contentType(MediaType.APPLICATION_JSON_UTF8))
                                         .andExpect(MockMvcResultMatchers.status().isOk())
                                         .andReturn().getResponse().getContentAsString();
        log.info("删除用户结果:" + result);
    }
}
```

