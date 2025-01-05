package com.project.estimateupdate.dao;

import javax.swing.JComboBox;

public interface LoginDao {

	public void getOperator(JComboBox<String> cmbUser) throws Exception;

	public boolean checkLogin(String user, String pass) throws Exception;

}
