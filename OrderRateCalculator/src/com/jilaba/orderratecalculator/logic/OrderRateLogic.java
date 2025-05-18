package com.jilaba.orderratecalculator.logic;

import java.util.Map;

import com.jilaba.orderratecalculator.service.OrderRateService;
import com.jilaba.orderratecalculator.serviceimpl.OrdeRateServiceImpl;

public class OrderRateLogic {
	private OrderRateService orderRateService;

	public Map<String, Object> getTagNoDetails(String tagNo) throws Exception {
		orderRateService = new OrdeRateServiceImpl();
		return this.orderRateService.getTagNoDetails(tagNo);
	}
}
