package com.market.coupon.repschema;

public class StatistcsInfo {
	
	String shop_name;
	int push_count;
	int order_count;
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public int getPush_count() {
		return push_count;
	}
	public void setPush_count(int push_count) {
		this.push_count = push_count;
	}
	public int getOrder_count() {
		return order_count;
	}
	public void setOrder_count(int order_count) {
		this.order_count = order_count;
	}
	@Override
	public String toString() {
		return "StatistcsInfo [shop_name=" + shop_name + ", push_count=" + push_count + ", order_count=" + order_count
				+ "]";
	}

}
