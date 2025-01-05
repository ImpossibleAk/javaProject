package com.project.estimateupdate.query;

import org.springframework.stereotype.Component;

@Component
public class OperatorQuery {

	public String getOperator() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select opername,opercode from operator;\n");
		return sb.toString();
	}

	public String checkLogin() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select ifnull(opername,'')opername from operator where opername=? and password=?;\n");
		return sb.toString();
	}

}
