package com.market.coupon.reqschema;

public class OrderSchema {
	String order_buyer_name;// 购买者姓名
	String order_buyer_phone;// 购买者电话
	String order_buyer_openid;// 购买者微信openid
	int order_buyer_lianmengid;
	public String getOrder_buyer_name() {
		return order_buyer_name;
	}

	public void setOrder_buyer_name(String order_buyer_name) {
		this.order_buyer_name = order_buyer_name;
	}

	public String getOrder_buyer_phone() {
		return order_buyer_phone;
	}

	public void setOrder_buyer_phone(String order_buyer_phone) {
		this.order_buyer_phone = order_buyer_phone;
	}

	public String getOrder_buyer_openid() {
		return order_buyer_openid;
	}

	public void setOrder_buyer_openid(String order_buyer_openid) {
		this.order_buyer_openid = order_buyer_openid;
	}

    public int getOrder_buyer_lianmengid() {
        return order_buyer_lianmengid;
    }

    public void setOrder_buyer_lianmengid(int order_buyer_lianmengid) {
        this.order_buyer_lianmengid = order_buyer_lianmengid;
    }

    @Override
    public String toString() {
        return "OrderSchema [order_buyer_name=" + order_buyer_name + ", order_buyer_phone=" + order_buyer_phone
                + ", order_buyer_openid=" + order_buyer_openid + ", order_buyer_lianmengid=" + order_buyer_lianmengid
                + "]";
    }

}
