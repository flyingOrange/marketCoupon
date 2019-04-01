package com.market.coupon.repschema;

import java.util.List;

public class StatistcsRep {
	
	int code;
	List<StatistcsInfo> list;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<StatistcsInfo> getList() {
		return list;
	}
	public void setList(List<StatistcsInfo> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "StatistcsRep [code=" + code + ", list=" + list + "]";
	}

}
