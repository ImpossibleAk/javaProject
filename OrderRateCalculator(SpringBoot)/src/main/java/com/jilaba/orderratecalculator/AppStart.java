package com.jilaba.orderratecalculator;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;
import javax.swing.BorderFactory;
import javax.swing.UIManager;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.formdev.flatlaf.FlatLightLaf;
import com.jilaba.orderratecalculator.common.AppMain;
import com.jilaba.orderratecalculator.config.AppConfig;
import com.jilaba.orderratecalculator.form.FrmOrderRateCalc;

@Configuration
@ComponentScan("com.jilaba.orderratecalculator.*")
public class AppStart {

	private static String dbName = "", user = "", pass = "", portNo = "", serverName = "";

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("Button.arc", Integer.valueOf(5));
			UIManager.put("Component.arc", Integer.valueOf(20));
			UIManager.put("TextComponent.arc", Integer.valueOf(20));
			UIManager.put("Button.border", BorderFactory.createLineBorder(Color.BLACK, 2, true));
			UIManager.put("Button.focusedBorderColor", Color.white);

			fileReader();

			AppConfig.context = (AbstractApplicationContext) new AnnotationConfigApplicationContext(AppStart.class);

			FrmOrderRateCalc calc = AppConfig.context.getBean(FrmOrderRateCalc.class);
			calc.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			AppMain.errorMessage(e.getMessage());
			System.exit(0);
		}
	}

	private static void fileReader() throws IOException {
		Properties prop = new Properties();
		try {
			prop.load(AppStart.class.getClassLoader()
					.getResourceAsStream("dbname.properties"));
			dbName = prop.getProperty("dbname");
			serverName = prop.getProperty("server");
			portNo = prop.getProperty("port");
			user = prop.getProperty("user");
			pass = prop.getProperty("pass");
		} catch (NullPointerException e) {
			dbName = "mmtjwcompydb";
			serverName = "192.168.45.12";
			portNo = "1433";
			user = "sa";
			pass = "Coorg@3529";
		}
	}

	@Bean(name = "dataSource")
	private static DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
		dataSource.setUsername(user);
		dataSource.setPassword(pass);
		dataSource.setUrl("jdbc:jtds:sqlserver://" + serverName + ":" + portNo + ";databaseName=" + dbName
				+ ";appName=OrderRateCalculator;");
		return (DataSource) dataSource;
	}

	@Bean(name = "filemain")
	@DependsOn("dataSource")
	private static Map<String, Object> filemain() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select isnull(masterdb,'')masterdb,isnull(trandb,'')trandb from filemain \n");
		sb.append("where getdate() between fromdate and todate and compid=(select compid from company) \n");
		Map<String, Object> map = new HashMap<>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		map = jdbcTemplate.queryForMap(sb.toString());
		return map;
	}
}
