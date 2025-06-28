package com.project.serverregister.common;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.project.serverregister.AppStart;

public class AppMain {

	private static String appName = "Server Register";
	private static int screenWid = Toolkit.getDefaultToolkit().getScreenSize().width,
			screenHgt = Toolkit.getDefaultToolkit().getScreenSize().height;

	public static void errorMessage(String error) {
		JOptionPane.showMessageDialog(null, error, appName, JOptionPane.ERROR_MESSAGE);
	}

	public static void errorMessage(String error, Component comp) {
		JOptionPane.showMessageDialog(comp, error, appName, JOptionPane.ERROR_MESSAGE);
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

	public static int verticalGap(JPanel pan, Component comp, double inc) {
		return comp.getY() + comp.getHeight() + (int) ((pan.getHeight() * inc) / 100);
	}

	public static int horizontalGap(JPanel pan, Component comp, double inc) {
		return comp.getX() + comp.getWidth() + (int) ((pan.getWidth() * inc) / 100);
	}

	public static String getAppName() {
		return appName;
	}

	public static int getScreenWid() {
		return screenWid;
	}

	public static int getScreenHgt() {
		return screenHgt;
	}

}
