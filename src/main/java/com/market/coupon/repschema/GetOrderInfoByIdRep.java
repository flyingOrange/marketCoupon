package com.market.coupon.repschema;

public class GetOrderInfoByIdRep {

	String order_serial;
	String order_buyer_name;
	String order_buyer_phone;
	String order_pay_state;
	String order_buy_time;
	String order_price;
	

	public String getOrder_serial() {
		return order_serial;
	}

	public void setOrder_serial(String order_serial) {
		this.order_serial = order_serial;
	}

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

	public String getOrder_pay_state() {
		return order_pay_state;
	}

	public void setOrder_pay_state(String order_pay_state) {
		this.order_pay_state = order_pay_state;
	}

	public String getOrder_buy_time() {
		return order_buy_time;
	}

	public void setOrder_buy_time(String order_buy_time) {
		this.order_buy_time = order_buy_time;
	}
	public String getOrder_price() {
		return order_price;
	}

	public void setOrder_price(String order_price) {
		this.order_price = order_price;
	}

    @Override
    public String toString() {
        return "GetOrderInfoByIdRep [order_serial=" + order_serial + ", order_buyer_name=" + order_buyer_name +", order_buyer_phone="+ order_buyer_phone+ ", order_pay_state="+ order_pay_state+", order_buy_time="+order_buy_time+ "order_price"+order_price+ "]";
    }



}
