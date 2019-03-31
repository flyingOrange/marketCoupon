package com.market.coupon.model;

public class RedRecordInfo {
	
	int redRecordId;
	String openId;
	int redNum;
	int redType;
	int redLianmengId;
	int fromOrderId;
	
	
	public int getFromOrderId() {
		return fromOrderId;
	}
	public void setFromOrderId(int fromOrderId) {
		this.fromOrderId = fromOrderId;
	}
	public int getRedRecordId() {
		return redRecordId;
	}
	public void setRedRecordId(int redRecordId) {
		this.redRecordId = redRecordId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getRedNum() {
		return redNum;
	}
	public void setRedNum(int redNum) {
		this.redNum = redNum;
	}
	public int getRedType() {
		return redType;
	}
	public void setRedType(int redType) {
		this.redType = redType;
	}
	public int getRedLianmengId() {
		return redLianmengId;
	}
	public void setRedLianmengId(int redLianmengId) {
		this.redLianmengId = redLianmengId;
	}

	
}
