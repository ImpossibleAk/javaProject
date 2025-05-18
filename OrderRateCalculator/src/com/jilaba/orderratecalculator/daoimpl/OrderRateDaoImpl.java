package com.jilaba.orderratecalculator.daoimpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jilaba.orderratecalculator.AppStart;
import com.jilaba.orderratecalculator.dao.OrderRateDao;
import com.jilaba.orderratecalculator.query.OrderRateQuery;

public class OrderRateDaoImpl implements OrderRateDao {

	private OrderRateQuery orderRateQuery;

	public Map<String, Object> getTagNoDetails(String tagNo) throws Exception {
		try {
			orderRateQuery = new OrderRateQuery();

			JdbcTemplate jdbcTemplate = new JdbcTemplate(AppStart.getDataSource());
			return jdbcTemplate.queryForMap(orderRateQuery.getTagNoDetails(), new Object[] { tagNo });
		} catch (EmptyResultDataAccessException e) {
			return new HashMap<>();
		}
	}
}
