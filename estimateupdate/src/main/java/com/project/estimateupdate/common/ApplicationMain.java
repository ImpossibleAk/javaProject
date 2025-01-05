package com.project.estimateupdate.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.project.estimateupdate.AppStart;

public class ApplicationMain {

	private static String mySqlDriver = "com.mysql.cj.jdbc.Driver";
	private static String mySqlUrl = "jdbc:mysql://";
	private static String mySqlSetting = "?applicationName=";
	private static String appName = "EstimateUpdate";
	private static String serverIp = "";
	private static String appVersion = "1.24.12.21.01";
	private static String loginOperator = "";

	private static Font lblFont = new Font("Arial", Font.BOLD, 14);

	private static Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

	private static int appWidth = screenDimension.width, appHeight = screenDimension.height, internalFrameWidth,
			internalFrameHeight;

	private static Map<String, Object> fileMain;

	private static Color bgColor = Color.decode("#eae6ff"), fgColor = Color.decode("#4b27ff"),
			btnColor = Color.decode("#6442c2");

	public static int verticalGap(JPanel pan, Component comp, double inc) {
		return comp.getY() + comp.getHeight() + (int) ((pan.getHeight() * inc) / 100);
	}

	public static int horizontalGap(JPanel pan, Component comp, double inc) {
		return comp.getX() + comp.getWidth() + (int) ((pan.getWidth() * inc) / 100);
	}

	public static void errorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, appName, JOptionPane.ERROR_MESSAGE);
	}

	public static void errorMessage(String message, Component comp) {
		JOptionPane.showMessageDialog(comp, message, appName, JOptionPane.ERROR_MESSAGE);
	}

	public static String getMySqlDriver() {
		return mySqlDriver;
	}

	public static String getMySqlUrl() {
		return mySqlUrl;
	}

	public static String getMySqlSetting() {
		return mySqlSetting;
	}

	public static String getAppName() {
		return appName;
	}

	public static int getInternalFrameWidth() {
		return internalFrameWidth;
	}

	public static void setInternalFrameWidth(int internalFrameWidth) {
		ApplicationMain.internalFrameWidth = internalFrameWidth;
	}

	public static int getInternalFrameHeight() {
		return internalFrameHeight;
	}

	public static void setInternalFrameHeight(int internalFrameHeight) {
		ApplicationMain.internalFrameHeight = internalFrameHeight;
	}

	public static int getAppWidth() {
		return appWidth;
	}

	public static int getAppHeight() {
		return appHeight;
	}

	public static Map<String, Object> getFileMain() {
		return fileMain;
	}

	public static void setFileMain(Map<String, Object> fileMain) {
		ApplicationMain.fileMain = fileMain;
	}

	public static Font getLblFont() {
		return lblFont;
	}

	public static boolean checkComboBox(JComboBox<?> cmb) {
		if (!String.valueOf(cmb.getSelectedItem()).isEmpty()) {
			return true;
		}
		for (int i = 0; i < cmb.getItemCount(); i++) {
			if (String.valueOf(cmb.getModel().getElementAt(i))
					.equalsIgnoreCase(String.valueOf(cmb.getSelectedItem()))) {
				return true;
			}
		}

		return false;
	}

	public static BufferedImage getBufferImage(String imgpath) throws Exception {
		BufferedImage bufimg = null;
		try {
			ClassLoader classLoader = AppStart.class.getClassLoader();
			if (null == classLoader.getResource(imgpath)) {
				return bufimg;
			}
			bufimg = ImageIO.read(classLoader.getResource(imgpath));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return bufimg;
	}

	public static int getVerticalGap(Panel pnl, Component comp, double inc) {
		return (int) (comp.getY() + comp.getHeight() + ((pnl.getHeight() * inc) / 100));
	}

	public static int getHorizontallGap(Panel pnl, Component comp, double inc) {
		return (int) (comp.getX() + comp.getWidth() + ((pnl.getWidth() * inc) / 100));
	}

	public static Color getBgColor() {
		return bgColor;
	}

	public static Color getFgColor() {
		return fgColor;
	}

	public static String getServerIp() {
		return serverIp;
	}

	public static void setServerIp(String serverIp) {
		ApplicationMain.serverIp = serverIp;
	}

	public static String getAppVersion() {
		return appVersion;
	}

	public static String getLoginOperator() {
		return loginOperator;
	}

	public static void setLoginOperator(String loginOperator) {
		ApplicationMain.loginOperator = loginOperator;
	}

	public static Color getBtnColor() {
		return btnColor;
	}

}
