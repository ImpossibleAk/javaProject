package com.project.estimateupdate.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.project.estimateupdate.query.OperatorQuery;
import com.project.estimateupdate.service.OperatorService;

@Component
public class OperatorServiceImpl implements OperatorService {

	@Autowired
	private OperatorQuery query;

	@Autowired
	private DataSource compyDbDataSource;

	@Override
	public List<Map<String, Object>> getOperator() throws Exception{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(compyDbDataSource);
		return jdbcTemplate.queryForList(query.getOperator());
	}

	@Override
	public String checkLogin(String user, String pass) throws Exception {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(compyDbDataSource);
			return jdbcTemplate.queryForObject(query.checkLogin(), String.class,new Object[] {user,pass});			
		} catch (EmptyResultDataAccessException e) {
			return "";
		}
	}

}
