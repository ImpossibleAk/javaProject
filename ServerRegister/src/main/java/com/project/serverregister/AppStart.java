package com.project.serverregister;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.project.serverregister.config.AppConfig;
import com.project.serverregister.form.FrmMainScreen;

@Configuration
@ComponentScan({ "com.project.serverregister.*,com.project.utility.*" })
public class AppStart {

	public static void main(String[] args) throws UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(new FlatIntelliJLaf());
		UIManager.put("Button.arc", 15); // Rounded corners for buttons
		UIManager.put("Component.arc", 20); // Rounded corners for other components
		UIManager.put("TextComponent.arc", 20); // Rounded corners for text fields

		AppConfig.context = new AnnotationConfigApplicationContext(AppStart.class);

		FrmMainScreen frmMain = AppConfig.context.getBean(FrmMainScreen.class);
		frmMain.setVisible(true);
	}

}
