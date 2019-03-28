package com.market.coupon.repschema;

public class RedPackageRep {
	
	String front_one_openid;
	String front_two_openid;
	int random_num_one;
	int random_num_two;
	public String getFront_one_openid() {
		return front_one_openid;
	}
	public void setFront_one_openid(String front_one_openid) {
		this.front_one_openid = front_one_openid;
	}
	public String getFront_two_openid() {
		return front_two_openid;
	}
	public void setFront_two_openid(String front_two_openid) {
		this.front_two_openid = front_two_openid;
	}
	public int getRandom_num_one() {
		return random_num_one;
	}
	public void setRandom_num_one(int random_num_one) {
		this.random_num_one = random_num_one;
	}
	public int getRandom_num_two() {
		return random_num_two;
	}
	public void setRandom_num_two(int random_num_two) {
		this.random_num_two = random_num_two;
	}
	@Override
	public String toString() {
		return "RedPackageResponse [front_one_openid=" + front_one_openid + ", front_two_openid=" + front_two_openid
				+ ", random_num_one=" + random_num_one + ", random_num_two=" + random_num_two + "]";
	}

}
