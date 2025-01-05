package com.project.estimateupdate.common;

public enum ImageResources {

	appIcon("images/appicon.png"), login("images/login.jpg"), calender("images/icons/calendar.png"),
	ip("images/icons/ip.png"), server("images/icons/server.png"), version("images/icons/version.png"),
	operator("images/icons/operator.png"), calc("images/icons/calculator.png"),
	mnuTransaction("images/menuicons/transaction.png"), mnuView("images/menuicons/view.png"),
	mnuExit("images/menuicons/exit.png");

	private String value;

	ImageResources(String data) {
		value = data;
	}

	public String getValue() {
		return value;
	}
}
