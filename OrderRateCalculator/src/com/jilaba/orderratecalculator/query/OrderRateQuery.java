package com.jilaba.orderratecalculator.query;

import com.jilaba.orderratecalculator.AppStart;

public class OrderRateQuery {

	public String getTagNoDetails() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select grosswt,netwt,lesswt,maxwastage,othercharge,studdiaamt,studstnamt,igstper as gstper, \n");
		sb.append("isnull(studdiawt,0)diawt,isnull(studstnwt,0)stnwt,maxwaspergrm,narration,maxmc from ")
				.append(AppStart.getFilemain().get("trandb")).append(".dbo.tagreceipt t with(readcommitted) \n");
		sb.append("left join ").append(AppStart.getFilemain().get("masterdb"))
				.append(".dbo.taxmaster tm on tm.taxcode= \n");
		sb.append("(select saltaxcode from ").append(AppStart.getFilemain().get("masterdb"))
				.append(".dbo.category where catcode=t.catcode) \n");
		sb.append("where tagno=? and issuetype not in('bt','sa','rp') \n");
		return sb.toString();
	}
}
