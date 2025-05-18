package com.jilaba.orderratecalculator.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRateQuery {
	@Autowired
	private Map<String, Object> filemain;

	public String getTagNoDetails() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select grosswt,netwt,lesswt,maxwastage,othercharge,studdiaamt,studstnamt,igstper as gstper, \n");
		sb.append("isnull(studdiawt/5,0)diawt,isnull(studstnwt,0)stnwt,maxwaspergrm from ")
				.append(this.filemain.get("trandb")).append(".dbo.tagreceipt t \n");
		sb.append("left join ").append(this.filemain.get("masterdb")).append(".dbo.taxmaster tm on tm.taxcode= \n");
		sb.append("(select saltaxcode from ").append(this.filemain.get("masterdb"))
				.append(".dbo.category where catcode=t.catcode) \n");
		sb.append("where tagno=? and issuetype not in('bt','sa','rp') \n");
		return sb.toString();
	}
}
