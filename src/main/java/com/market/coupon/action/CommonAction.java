package com.market.coupon.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.coupon.model.JoinInfo;
import com.market.coupon.model.Order;
import com.market.coupon.model.WeUserinfo;
import com.market.coupon.repschema.RedPackageRep;
import com.market.coupon.repschema.UpdateUserInfoRep;
import com.market.coupon.reqschema.AddJoinInfoSchema;
import com.market.coupon.reqschema.AddUserSchema;
import com.market.coupon.reqschema.OrderCallbackSchema;
import com.market.coupon.reqschema.OrderSchema;
import com.market.coupon.reqschema.RedPackageSchema;
import com.market.coupon.reqschema.UpdateUserInfoSchema;
import com.market.coupon.service.CommonService;

@RestController
public class CommonAction {

	@Resource(name = "CommonService")
	private CommonService commonService;

	// add we_userinfo
	@RequestMapping("/addUser")
	Boolean addUser(@RequestBody AddUserSchema schema) {

		WeUserinfo info = new WeUserinfo();
		info.setOpenid(schema.getWe_openid());
		info.setFrontOneOpenid(schema.getWe_front_one());
		info.setLianmengid(schema.getWe_lianmengid());
		info.setShopid(schema.getWe_shopid());

		commonService.addUser(info);
		return true;
	}

	// add join_info
	@RequestMapping("/addJoinInfo")
	Boolean addJoinInfo(@RequestBody AddJoinInfoSchema schema) {
		if (StringUtils.isAnyBlank(schema.getJoin_hangye(), schema.getJoin_name(), schema.getJoin_phone())) {
			return false;
		}
		if (schema.getJoin_phone().length() != 11) {
			return false;
		}
		JoinInfo info = new JoinInfo();
		info.setJoinHangye(schema.getJoin_hangye());
		info.setJoinName(schema.getJoin_name());
		info.setJoinPhone(schema.getJoin_phone());
		commonService.addJoinInfo(info);
		return true;
	}

	// 接口编号10,用户点击提交订单
	@RequestMapping("/order")
	Integer buy(@RequestBody OrderSchema schema) {
		Order order = new Order();
		order.setOrderBuyerName(schema.getOrder_buyer_name());
		order.setOrderBuyerOpenid(schema.getOrder_buyer_openid());
		order.setOrderBuyerPhone(schema.getOrder_buyer_phone());
		order.setOrderLianmengId(schema.getOrder_buyer_lianmengid());
		int orderId = commonService.makeOrder(order);

		return orderId;
	}

	// 接口编号11,用户付完款之后生成订单的接口。更新order表
	@RequestMapping("/orderCallback")
	Boolean callback(@RequestBody OrderCallbackSchema schema) {
		String orderId = schema.getOrder_id();
		if (StringUtils.isBlank(orderId)) {
			return false;
		}
		commonService.orderCallback(orderId);
		return true;
	}

	// 接口编号12,更新we_userinfo表的front_two字段
	@RequestMapping("/updateUserInfo")
	UpdateUserInfoRep updateUserInfo(@RequestBody UpdateUserInfoSchema schema) {
		String openId = schema.getOrder_buyer_openid();
		int lianmengId = schema.getOrder_buyer_lianmengid();
		UpdateUserInfoRep rep = commonService.updateUserInfo(openId, lianmengId);
		return rep;
	}

	// 接口编号11,根据openid和lianmengid获取红包信息
	@RequestMapping("/redPackage")
	RedPackageRep redPackage(@RequestBody RedPackageSchema schema) {
		String openId = schema.getBuyer_openid();
		int lianmengId = schema.getLianmeng_id();
		RedPackageRep response = commonService.redPackage(openId, lianmengId);

		return response;
	}

}
