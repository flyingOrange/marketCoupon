package com.market.coupon.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.coupon.model.JoinInfo;
import com.market.coupon.model.Order;
import com.market.coupon.model.RedRecordInfo;
import com.market.coupon.model.WeUserinfo;
import com.market.coupon.repschema.GetOrderInfoByIdRep;
import com.market.coupon.repschema.OrderListRep;
import com.market.coupon.repschema.RedPackageRep;
import com.market.coupon.repschema.UpdateUserInfoRep;
import com.market.coupon.reqschema.AddJoinInfoSchema;
import com.market.coupon.reqschema.AddUserSchema;
import com.market.coupon.reqschema.InsertRedPackInfoSchema;
import com.market.coupon.reqschema.OrderCallbackSchema;
import com.market.coupon.reqschema.OrderListSchema;
import com.market.coupon.reqschema.OrderSchema;
import com.market.coupon.reqschema.RedPackageSchema;
import com.market.coupon.reqschema.StatistcsSchema;
import com.market.coupon.reqschema.UpdateUserInfoSchema;
import com.market.coupon.service.CommonService;;

@RestController
public class CommonAction {

	@Resource(name = "CommonService")
	private CommonService commonService;
	
	// 接口编号3-获取商户推广量，用来统计信息
	@RequestMapping("/statistcs")
	void statistcs(@RequestBody StatistcsSchema schema) {
		int lianmengId = schema.getLianmengid();
		
		
	}
	
	// 接口编号5--获取订单列表，用来展示到活动首页
	@RequestMapping("/orderList")
	OrderListRep orderList(@RequestBody OrderListSchema schema) {
		int lianmengId = schema.getLianmengid();
		OrderListRep rep = commonService.orderList(lianmengId);
		return rep;
	}

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
	
	//根据订单id查询订单接口
	@RequestMapping("/getOrderById")
	GetOrderInfoByIdRep getOrderInfoById(@RequestBody OrderCallbackSchema schema) {
		
		String orderId = schema.getOrder_id();
		
		GetOrderInfoByIdRep response = commonService.getOrderById(orderId);
		return response;		
	}
	
	//根据lianmengid和openid查询订单
	@RequestMapping("/getOrderByOidLid")
	List<GetOrderInfoByIdRep> getOrderByOidAndLid(@RequestBody RedPackageSchema schema) {
		
		String openId = schema.getBuyer_openid();
		int lianmengId = schema.getLianmeng_id();
		//List<GetOrderInfoByIdRep> response = new ArrayList<GetOrderInfoByIdRep>();
		List<GetOrderInfoByIdRep> response = commonService.getOrderByOidAndLid(openId, lianmengId);
		
		return response;		
	}
	
	//存储发红包信息到数据库
	@RequestMapping("/saveRedPackInfo")
	boolean insertRedPackInfo(@RequestBody InsertRedPackInfoSchema schema) {
		
		if (StringUtils.isAnyBlank(schema.getOpenid(), schema.getLianmeng_id(), schema.getRed_num(), schema.getRed_type())) {
			return false;
		}

		String openid = schema.getOpenid();
		String lianmeng_id = schema.getLianmeng_id();
		String red_num = schema.getRed_num();
		String red_type = schema.getRed_type();
		String order_id = schema.getOrder_id();
		
		RedRecordInfo redRecord = new RedRecordInfo();
		redRecord.setOpenId(openid);
		redRecord.setRedLianmengId(Integer.parseInt(lianmeng_id));
		redRecord.setRedNum(Integer.parseInt(red_num));
		redRecord.setRedType(Integer.parseInt(red_type));
		redRecord.setFromOrderId(Integer.parseInt(order_id));
		
		commonService.addRedPackRecordInfo(redRecord);
		return true;
	}
	
	//根据订单号查询是否已经发过红包
	@RequestMapping("/ifSentHBByOrderId")
	boolean ifSentRedPackByOrderId(@RequestBody OrderCallbackSchema schema) {
		
		int orderId = Integer.parseInt(schema.getOrder_id());
		
		if(commonService.ifSentRedPackByOrderId(orderId)) {
			
			return true;
		}else {
			
			return false;
		}
	}
	

}
