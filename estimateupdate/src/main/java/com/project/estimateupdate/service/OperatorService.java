package com.project.estimateupdate.service;

import java.util.List;
import java.util.Map;

public interface OperatorService {

	public List<Map<String, Object>> getOperator() throws Exception;

	public String checkLogin(String user, String pass) throws Exception;

}
