package com.project.serverregister.common;

public enum ImageResources {

	appIcon("images/appicon.png"), server("images/server.gif");

	private String value;

	ImageResources(String data) {
		value = data;
	}

	public String getValue() {
		return value;
	}
}
