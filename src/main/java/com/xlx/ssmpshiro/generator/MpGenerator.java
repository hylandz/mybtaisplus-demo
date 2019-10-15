package com.xlx.ssmpshiro.generator;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyBatis-Plus的代码生成器
 * MySQL
 *
 * @author xielx on 2019/7/16
 */
public class MpGenerator {


  public static void main(String[] args) {

    //自动生成器
    AutoGenerator mpg = new AutoGenerator();
    String projectPath = System.getProperty("user.dir");
    // 1.全局配置
    GlobalConfig gc = new GlobalConfig();
    gc.setOutputDir(projectPath + "/src/main/java");
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
    dsc.setTypeConvert(new MySqlTypeConvert() {
      // 自定义数据库表字段类型转换【可选】
      @Override
      public DbColumnType processTypeConvert(String fieldType) {
        System.out.println("转换类型：" + fieldType);
        // 将数据库的tinyint转换成自定义的[默认是Integer]
        if (fieldType.toLowerCase().contains("tinyint")){
          return DbColumnType.BASE_BOOLEAN;
        }
        // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
        return super.processTypeConvert(fieldType);
      }
    });
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("mango");
    dsc.setPassword("root5.7.22");
    dsc.setUrl("jdbc:mysql://localhost:3306/springbootdb?useUnicode=true&characterEncoding=utf8");
    mpg.setDataSource(dsc);

    // 3.策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
    strategy.setTablePrefix(new String[]{"sys_"});// 数据库表前缀,生成的实体类名会省略前缀sys_users,users
    strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略(下划线变驼峰式)
    strategy.setDbColumnUnderline(true);//数据库表名,字段名使用下划线
    strategy.setEntityLombokModel(true);//lombok注解
    //sys_users","sys_roles","sys_resources","sys_depts","sys_user_role","sys_role_resource
    strategy.setInclude(new String[] { "log_error","log_login" }); // 需要生成的表
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
    pc.setParent("com.xlx.ssmpshiro")//父路径
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

    // 自定义 xxList.jsp 生成
    List<FileOutConfig> focList = new ArrayList<>();
//    focList.add(new FileOutConfig("/template/list.jsp.vm") {
//      @Override
//     public String outputFile(TableInfo tableInfo) {
    // 自定义输入文件名称
//      return "D://my_" + tableInfo.getEntityName() + ".jsp";
//      }
//    });
//    cfg.setFileOutConfigList(focList);
//   mpg.setCfg(cfg);

    // 调整 xml 生成目录演示
    focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
      @Override
      public String outputFile(TableInfo tableInfo) {
        return projectPath +"/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
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
