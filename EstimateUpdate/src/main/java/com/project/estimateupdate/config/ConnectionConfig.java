package com.project.estimateupdate.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.project.estimateupdate.common.ApplicationMain;
import com.project.utility.Encryption;

@Configuration
public class ConnectionConfig {

	private String dbName;
	private String serverName;
	private String portNo;
	private String user;
	private String pass;

	private void fileReader() throws Exception {
		File f = new File("server.common").getAbsoluteFile();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);

		String line = "";
		int count = 0;
		for (int i = count; i < 5; i++) {
			line=br.readLine();
			switch (count) {
			case 0: {
				serverName=line;
				break;
			}
			case 1: {
				dbName=Encryption.deCrypt(line) ;
				break;
			}
			case 2: {
				 user=Encryption.deCrypt(line) ;
				break;
			}
			case 3: {
				pass=Encryption.deCrypt(line) ;
				break;
			}
			case 4: {
				portNo=Encryption.deCrypt(line);
				break;
			}
			default:
				count++;
			}
			count++;
		}
		br.close();
	}

	private DataSource getDataSource(String name) throws Exception {
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
	DataSource getCompyDbDataSource() throws Exception {
		fileReader();
		return getDataSource(dbName);
	}

}
