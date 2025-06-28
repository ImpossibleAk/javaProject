package com.project.estimateupdate;

import java.io.File;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.UIManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.AbstractApplicationContext;

import com.formdev.flatlaf.FlatLightLaf;
import com.project.estimateupdate.common.ApplicationMain;
import com.project.estimateupdate.config.ApplicationConfig;

@SpringBootApplication
public class AppStart {

	private static final Logger logs = Logger.getGlobal(); // getLogger(AppStart.class.getClass().getName());

	public static void main(String[] args) {
		try {

			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("Button.arc", 15); // Rounded corners for buttons
			UIManager.put("Component.arc", 20); // Rounded corners for other components
			UIManager.put("TextComponent.arc", 20); // Rounded corners for text fields

			String jarPath = new File(new File(ApplicationMain.getAppName()).getAbsolutePath()).getParent()
					.concat(File.separator).concat("Log").concat(File.separator).concat(ApplicationMain.getAppName())
					.concat(File.separator).concat(ApplicationMain.getAppName()).concat("-")
					.concat(LocalDate.now().toString()).concat(".txt");

			File f = new File(new File(new File(ApplicationMain.getAppName()).getAbsolutePath()).getParent()
					.concat(File.separator).concat("Log").concat(File.separator).concat(ApplicationMain.getAppName()));

			File f1 = new File(f.getAbsolutePath().concat(File.separator).concat(ApplicationMain.getAppName()));

			if (!f.exists()) {
				f.mkdir();
			}
			
			if (!f1.exists()) {
				f1.mkdir();
			}

			FileHandler fileHandler = new FileHandler(jarPath, true);
			fileHandler.setFormatter(new SimpleFormatter());
			fileHandler.setLevel(Level.ALL);
			for (Handler h : logs.getHandlers()) {
				System.out.println(h.toString());

			}
			logs.setUseParentHandlers(false);
			logs.addHandler(fileHandler);
			logs.setLevel(Level.ALL);

			SpringApplication app = new SpringApplication(AppStart.class);
			app.setHeadless(false);
			ApplicationConfig.context = (AbstractApplicationContext) app.run(args);
			

			logs.info("Application Started");

			FrmLogin frmLogin = ApplicationConfig.context.getBean(FrmLogin.class);
			frmLogin.loadInitialize();
			frmLogin.setVisible(true);

			logs.info("Application Ended");
		} catch (Exception e) {
			if (e.getMessage() != null) {
				logs.log(Level.SEVERE, e.getMessage(), e);
				ApplicationMain.errorMessage(e.getMessage());
			}
		}
	}

	

	public static Logger getLogger() {
		return logs;
	}
}
