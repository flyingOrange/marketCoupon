package com.market.coupon.service;

import java.util.List;

import com.market.coupon.model.JoinInfo;
import com.market.coupon.model.Order;
import com.market.coupon.model.RedRecordInfo;
import com.market.coupon.model.WeUserinfo;
import com.market.coupon.repschema.GetOrderInfoByIdRep;
import com.market.coupon.repschema.OrderListRep;
import com.market.coupon.repschema.RedPackageRep;
import com.market.coupon.repschema.StatistcsRep;
import com.market.coupon.repschema.UpdateUserInfoRep;

public interface CommonService {
	
	void addUser(WeUserinfo info);
	
	void addJoinInfo(JoinInfo info);
	
	int makeOrder(Order order);
	
	void orderCallback(String orderId);
	
	UpdateUserInfoRep updateUserInfo(String openId,int lianmengId);
	
	RedPackageRep redPackage(String openId,int lianmengId);
	
	GetOrderInfoByIdRep getOrderById(String orderId);
	
	List<GetOrderInfoByIdRep> getOrderByOidAndLid(String openId,int lianmengId);
	
	void addRedPackRecordInfo(RedRecordInfo redRecord);

	OrderListRep orderList(int lianmengId);
	
	boolean ifSentRedPackByOrderId(int orderId);
	
	StatistcsRep statistcs(int lianmengId);
	
}
