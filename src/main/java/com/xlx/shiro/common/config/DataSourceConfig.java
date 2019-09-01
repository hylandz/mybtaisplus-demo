package com.xlx.shiro.common.config;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据源配置 druid
 *
 * @author xielx on 2019/7/24
 */
//@Configuration
public class DataSourceConfig {

  private final String DB_TYPE = "mysql";
	private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost:3306/springbootdb?useUnicode=true&characterEncoding=utf8";
	private final String USERNAME = "mango";
	private final String PASSWORD = "root5.7.22";


	//@Bean
	public DruidDataSource dataSource(){
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDbType(DB_TYPE);
		druidDataSource.setDriverClassName(DRIVER_CLASS_NAME);
		druidDataSource.setUrl(URL);
		druidDataSource.setUsername(USERNAME);
		druidDataSource.setPassword(PASSWORD);

		return druidDataSource;
	}
}
