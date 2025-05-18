package com.jilaba.orderratecalculator.serviceimpl;

import java.util.Map;

import com.jilaba.orderratecalculator.dao.OrderRateDao;
import com.jilaba.orderratecalculator.daoimpl.OrderRateDaoImpl;
import com.jilaba.orderratecalculator.service.OrderRateService;

public class OrdeRateServiceImpl implements OrderRateService {
	private OrderRateDao orderRateDao;

	public Map<String, Object> getTagNoDetails(String tagNo) throws Exception {
		orderRateDao = new OrderRateDaoImpl();
		return this.orderRateDao.getTagNoDetails(tagNo);
	}
}
