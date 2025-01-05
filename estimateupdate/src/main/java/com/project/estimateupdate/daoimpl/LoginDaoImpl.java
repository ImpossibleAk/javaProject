package com.project.estimateupdate.daoimpl;

import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.estimateupdate.dao.LoginDao;
import com.project.estimateupdate.service.OperatorService;

@Component
public class LoginDaoImpl implements LoginDao {

	@Autowired
	private OperatorService operatorService;

	@Override
	public void getOperator(JComboBox<String> cmbUser) throws Exception {
		cmbUser.removeAllItems();

		List<Map<String, Object>> lstMap = operatorService.getOperator();

		for (Map<String, Object> map : lstMap) {
			cmbUser.addItem(String.valueOf(map.get("opername")));
		}

	}

	@Override
	public boolean checkLogin(String user, String pass) throws Exception {
		String name=operatorService.checkLogin( user, pass);
		if(name.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

}
