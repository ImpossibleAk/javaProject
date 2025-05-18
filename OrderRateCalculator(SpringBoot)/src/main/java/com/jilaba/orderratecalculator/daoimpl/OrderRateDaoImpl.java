package com.jilaba.orderratecalculator.daoimpl;

import com.jilaba.orderratecalculator.dao.OrderRateDao;
import com.jilaba.orderratecalculator.query.OrderRateQuery;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderRateDaoImpl implements OrderRateDao {
  @Autowired
  private OrderRateQuery orderRateQuery;
  
  @Autowired
  private DataSource dataSource;
  
  public Map<String, Object> getTagNoDetails(String tagNo) throws Exception {
    try {
      JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
      return jdbcTemplate.queryForMap(this.orderRateQuery.getTagNoDetails(), new Object[] { tagNo });
    } catch (EmptyResultDataAccessException e) {
      return new HashMap<>();
    } 
  }
}
