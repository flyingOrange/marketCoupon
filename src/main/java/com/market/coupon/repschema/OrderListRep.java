package com.market.coupon.repschema;

import java.util.List;

public class OrderListRep {
	
	int code;
	List<OrderListInfo> list;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<OrderListInfo> getList() {
		return list;
	}
	public void setList(List<OrderListInfo> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "OrderListRep [code=" + code + ", list=" + list + "]";
	}

}
