package com.jilaba.orderratecalculator.serviceimpl;

import com.jilaba.orderratecalculator.dao.OrderRateDao;
import com.jilaba.orderratecalculator.service.OrderRateService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdeRateServiceImpl implements OrderRateService {
	@Autowired
	private OrderRateDao orderRateDao;

	public Map<String, Object> getTagNoDetails(String tagNo) throws Exception {
		return this.orderRateDao.getTagNoDetails(tagNo);
	}
}
