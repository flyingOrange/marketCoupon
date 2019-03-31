package com.market.coupon.repschema;

public class OrderListInfo {

	String buyer_name;
	String buyer_phone;
	String buyer_nickname;
	String buyer_headpic;
	public String getBuyer_name() {
		return buyer_name;
	}
	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}
	public String getBuyer_phone() {
		return buyer_phone;
	}
	public void setBuyer_phone(String buyer_phone) {
		this.buyer_phone = buyer_phone;
	}
	public String getBuyer_nickname() {
		return buyer_nickname;
	}
	public void setBuyer_nickname(String buyer_nickname) {
		this.buyer_nickname = buyer_nickname;
	}
	public String getBuyer_headpic() {
		return buyer_headpic;
	}
	public void setBuyer_headpic(String buyer_headpic) {
		this.buyer_headpic = buyer_headpic;
	}
	@Override
	public String toString() {
		return "OrderListInfo [buyer_name=" + buyer_name + ", buyer_phone=" + buyer_phone + ", buyer_nickname="
				+ buyer_nickname + ", buyer_headpic=" + buyer_headpic + "]";
	}
	
}
