package com.jilaba.orderratecalculator.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jilaba.orderratecalculator.AppStart;

public class AppMain {
	private static String appName = "Order Rate Calculator";

	private static int ScreenWidth = (Toolkit.getDefaultToolkit().getScreenSize()).width;

	private static int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height;

	private static Color bgColor = Color.decode("#eae6ff"), fgColor = Color.decode("#3400cf");

	private static Color btnColor = Color.decode("#7050cf");

	private static Font lblFont = new Font("Arial", 1, 18);

	private static NumberFormat nt = new DecimalFormat("#0"), amt = new DecimalFormat("#0.00"),
			wt = new DecimalFormat("#0.000");

	public static int getScreenWidth() {
		return ScreenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void errorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, appName, 0);
	}

	public static void errorMessage(String message, Component comp) {
		JOptionPane.showMessageDialog(comp, message, appName, 0);
	}

	public static String getAppName() {
		return appName;
	}

	public static BufferedImage getBufferImage(String imgpath) throws Exception {
		BufferedImage bufimg = null;
		try {
			ClassLoader classLoader = AppStart.class.getProtectionDomain().getClassLoader();
			if (null == classLoader.getResource(imgpath))
				return bufimg;
			bufimg = ImageIO.read(classLoader.getResource(imgpath));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return bufimg;
	}

	public static int getVerticalGap(JPanel pnl, Component comp, double inc) {
		return (int) ((comp.getY() + comp.getHeight()) + pnl.getHeight() * inc / 100.0D);
	}

	public static int getHorizontallGap(JPanel pnl, Component comp, double inc) {
		return (int) ((comp.getX() + comp.getWidth()) + pnl.getWidth() * inc / 100.0D);
	}

	public static Color getBgColor() {
		return bgColor;
	}

	public static Color getFgColor() {
		return fgColor;
	}

	public static Color getBtnColor() {
		return btnColor;
	}

	public static Font getLblFont() {
		return lblFont;
	}

	public static double getDoubleValue(String val) {
		return val.isEmpty() ? 0.0D : Double.parseDouble(val);
	}

	public static NumberFormat getAmt() {
		return amt;
	}

	public static NumberFormat getWt() {
		return wt;
	}

	public static NumberFormat getNt() {
		return nt;
	}
}
