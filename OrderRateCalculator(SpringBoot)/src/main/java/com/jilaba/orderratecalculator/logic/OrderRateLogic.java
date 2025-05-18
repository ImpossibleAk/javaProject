package com.jilaba.orderratecalculator.logic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jilaba.orderratecalculator.service.OrderRateService;

@Component
public class OrderRateLogic {
	@Autowired
	private OrderRateService orderRateService;

	public Map<String, Object> getTagNoDetails(String tagNo) throws Exception {
		return this.orderRateService.getTagNoDetails(tagNo);
	}
}
