package com.market.coupon.model;

import java.util.Date;

public class OrderRepInfo {
	
	String orderSerial;
	String orderBuyerName;
	String orderBuyerPhone;
	String orderPayState;
	Date orderBuyTime;
	String orderPrice;
	
	
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getOrderSerial() {
		return orderSerial;
	}
	public void setOrderSerial(String orderSerial) {
		this.orderSerial = orderSerial;
	}
	public String getOrderBuyerName() {
		return orderBuyerName;
	}
	public void setOrderBuyerName(String orderBuyerName) {
		this.orderBuyerName = orderBuyerName;
	}
	public String getOrderBuyerPhone() {
		return orderBuyerPhone;
	}
	public void setOrderBuyerPhone(String orderBuyerPhone) {
		this.orderBuyerPhone = orderBuyerPhone;
	}
	public String getOrderPayState() {
		return orderPayState;
	}
	public void setOrderPayState(String orderPayState) {
		this.orderPayState = orderPayState;
	}
	public Date getOrderBuyTime() {
		return orderBuyTime;
	}
	public void setOrderBuyTime(Date orderBuyTime) {
		this.orderBuyTime = orderBuyTime;
	}
	
	

}
