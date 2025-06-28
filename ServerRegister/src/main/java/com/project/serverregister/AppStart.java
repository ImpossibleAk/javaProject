package com.project.serverregister;

import java.io.File;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;
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

	private static final Logger logs = Logger.getGlobal();

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		try {

			UIManager.setLookAndFeel(new FlatIntelliJLaf());
			UIManager.put("Button.arc", 15); // Rounded corners for buttons
			UIManager.put("Component.arc", 20); // Rounded corners for other components
			UIManager.put("TextComponent.arc", 20); // Rounded corners for text fields

			String jarPath = new File(new File("ServerRegister").getAbsolutePath()).getParent().concat(File.separator)
					.concat("Log").concat(File.separator).concat("ServerRegister").concat(File.separator)
					.concat("ServerRegister").concat("-").concat(LocalDate.now().toString()).concat(".txt");
			System.out.println(jarPath);
			File f = new File(new File(new File("ServerRegister").getAbsolutePath()).getParent().concat(File.separator)
					.concat("Log"));
			File f1 = new File(f.getAbsolutePath().concat(File.separator).concat("ServerRegister"));
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

			AppConfig.context = new AnnotationConfigApplicationContext(AppStart.class);

			logs.info("Application Started");

			FrmMainScreen frmMain = AppConfig.context.getBean(FrmMainScreen.class);
			frmMain.setVisible(true);

			logs.info("Application Ended");
		} catch (Exception e) {
			logs.log(Level.ALL, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage(), "ServerRegister", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static Logger getLogs() {
		return logs;
	}
}
