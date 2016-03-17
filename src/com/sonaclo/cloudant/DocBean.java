package com.sonaclo.cloudant;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DocBean {

	private String category;
	private String stopId;
	private int sales;

	private Date saleDate;
	private long createDt;
	/**
	 * 날짜 매출액
	 * @param day
	 * @param sales
	 */
	public DocBean(String shopId,int day, int sales){

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, day);


		this.createDt = c.getTimeInMillis();
		this.sales = sales;
		this.saleDate = c.getTime();
		this.stopId = shopId;

	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStopId() {
		return stopId;
	}
	public void setStopId(String stopId) {
		this.stopId = stopId;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public long getCreateDt() {
		return createDt;
	}
	public void setCreateDt(long createDt) {
		this.createDt = createDt;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}


}
