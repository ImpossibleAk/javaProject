package com.project.estimateupdate;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.AbstractApplicationContext;

import com.formdev.flatlaf.FlatLightLaf;
import com.project.estimateupdate.common.ApplicationMain;
import com.project.estimateupdate.config.ApplicationConfig;

@SpringBootApplication
public class AppStart {

	public static void main(String[] args) {
		Logger  logger = LogManager.getLogger(AppStart.class);
		try {

			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("Button.arc", 15); // Rounded corners for buttons
			UIManager.put("Component.arc", 20); // Rounded corners for other components
			UIManager.put("TextComponent.arc", 20); // Rounded corners for text fields

			SpringApplication app = new SpringApplication(AppStart.class);
			app.setHeadless(false);
			ApplicationConfig.context = (AbstractApplicationContext) app.run(args);

			FrmLogin frmLogin = ApplicationConfig.context.getBean(FrmLogin.class);
			frmLogin.loadInitialize();
			frmLogin.setVisible(true);

		} catch (Exception e) {
			if (e.getMessage() != null) {
				e.printStackTrace();
				logger.error(e);
				ApplicationMain.errorMessage(e.getMessage());
			}
		}
	}

}
