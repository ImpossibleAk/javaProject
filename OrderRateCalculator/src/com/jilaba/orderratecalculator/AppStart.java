package com.jilaba.orderratecalculator;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.BorderFactory;
import javax.swing.UIManager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.formdev.flatlaf.FlatLightLaf;
import com.jilaba.common.ReturnStatus;
import com.jilaba.fileresource.FileRead;
import com.jilaba.fileresource.JilabaFile;
import com.jilaba.fileresource.Register;
import com.jilaba.fileresource.Server;
import com.jilaba.orderratecalculator.common.AppMain;
import com.jilaba.orderratecalculator.form.FrmOrderRateCalc;

public class AppStart {

	private static String dbName = "", user = "", pass = "", portNo = "", serverName = "";
	private static Map<String, Object> filemain;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("Button.arc", Integer.valueOf(5));
			UIManager.put("Component.arc", Integer.valueOf(20));
			UIManager.put("TextComponent.arc", Integer.valueOf(20));
			UIManager.put("Button.border", BorderFactory.createLineBorder(Color.BLACK, 2, true));
			UIManager.put("Button.focusedBorderColor", Color.white);

			fileReader();

			FrmOrderRateCalc calc = new FrmOrderRateCalc();
			calc.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			AppMain.errorMessage(e.getMessage());
			System.exit(0);
		}
	}

	private static void fileReader() throws Exception {
		FileRead fileRead = new FileRead();
		ReturnStatus status = new ReturnStatus();

		status = fileRead.read(AppStart.class, JilabaFile.REGISTER);
		if (!status.isStatus()) {
			throw new Exception(status.getDescription());
		}

		status = fileRead.read(AppStart.class, JilabaFile.SERVER);
		if (!status.isStatus()) {
			throw new Exception(status.getDescription());
		}

		dbName = Register.getCompanyId() + "compydb";
		serverName = Server.getServerName();
		portNo = String.valueOf(Server.getPortNo());
		user = Server.getUserName();
		pass = Server.getPassword();

		getDataSource();
		filemain();
	}

	public static DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
		dataSource.setUsername(user);
		dataSource.setPassword(pass);
		dataSource.setUrl("jdbc:jtds:sqlserver://" + serverName + ":" + portNo + ";databaseName=" + dbName
				+ ";appName=OrderRateCalculator;");
		return (DataSource) dataSource;
	}

	private static Map<String, Object> filemain() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select isnull(masterdb,'')masterdb,isnull(trandb,'')trandb from filemain \n");
		sb.append("where getdate() between fromdate and todate \n");
		Map<String, Object> map = new HashMap<>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		map = jdbcTemplate.queryForMap(sb.toString());
		filemain = map;
		return map;
	}

	public static Map<String, Object> getFilemain() {
		return filemain;
	}
}
