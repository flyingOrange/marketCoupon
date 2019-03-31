package com.market.coupon.reqschema;

public class InsertRedPackInfoSchema {

	String openid;
	String red_num;
	String red_type;
	String lianmeng_id;
	String order_id;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getLianmeng_id() {
		return lianmeng_id;
	}
	public void setLianmeng_id(String lianmeng_id) {
		this.lianmeng_id = lianmeng_id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
    public String getRed_num() {
		return red_num;
	}
	public void setRed_num(String red_num) {
		this.red_num = red_num;
	}
	public String getRed_type() {
		return red_type;
	}
	public void setRed_type(String red_type) {
		this.red_type = red_type;
	}
	
	
	@Override
    public String toString() {
        return "InsertRedPackInfoSchema [openid=" + openid + ", red_num=" + red_num + 
        		"red_type" + red_type + "lianmeng_id" + lianmeng_id + "order_id" + order_id +"]";
    }
}
