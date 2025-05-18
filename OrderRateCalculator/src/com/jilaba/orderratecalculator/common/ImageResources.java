package com.jilaba.orderratecalculator.common;

public enum ImageResources {
	appIcon("images/appicon.png"), calendar("images/calendar.png"), ip("images/ip.png"), version("images/version.png"),
	calc("images/calc.png"), operator("images/operator.png");

	private String value;

	ImageResources(String data) {
		this.value = data;
	}

	public String getValue() {
		return this.value;
	}
}
