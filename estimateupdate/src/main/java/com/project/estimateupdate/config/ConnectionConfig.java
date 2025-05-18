package com.project.estimateupdate.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.project.estimateupdate.common.ApplicationMain;

@Configuration
@PropertySource("classpath:dbName.properties")
public class ConnectionConfig {

	@Value("${dbname}")
	private String dbName;
	@Value("${trandb}")
	private String tranDb;
	@Value("${server}")
	private String serverName;
	@Value("${port}")
	private String portNo;
	@Value("${user}")
	private String user;
	@Value("${pass}")
	private String pass;

	private DataSource getDataSource(String name) {
		if (ApplicationMain.getServerIp().isEmpty()) {
			ApplicationMain.setServerIp(serverName);
		}
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(ApplicationMain.getMySqlDriver());
		dataSource.setUrl(ApplicationMain.getMySqlUrl().concat(serverName).concat(":").concat(portNo).concat("/")
				.concat(name).concat(ApplicationMain.getMySqlSetting()));
		dataSource.setUsername(user);
		dataSource.setPassword(pass);
		return dataSource;
	}

	@Bean(name = "compyDbDataSource")
	DataSource getCompyDbDataSource() {
		return getDataSource(dbName);
	}

}
