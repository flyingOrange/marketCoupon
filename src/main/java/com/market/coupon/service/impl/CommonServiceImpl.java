package com.market.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.JPopupMenu.Separator;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.market.coupon.dao.JoinInfoDao;
import com.market.coupon.dao.LianmengInfoDao;
import com.market.coupon.dao.OrderDao;
import com.market.coupon.dao.WeUserDao;
import com.market.coupon.model.JoinInfo;
import com.market.coupon.model.LianmengInfo;
import com.market.coupon.model.Order;
import com.market.coupon.model.OrderRepInfo;
import com.market.coupon.model.WeUserinfo;
import com.market.coupon.repschema.GetOrderInfoByIdRep;
import com.market.coupon.repschema.RedPackageRep;
import com.market.coupon.service.CommonService;

@Service("CommonService")
public class CommonServiceImpl implements CommonService{

    @Resource
    private WeUserDao weUserDao;
    @Resource
    private JoinInfoDao joinInfoDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private LianmengInfoDao lianmengInfoDao;
    
    
	@Override
	public void addUser(WeUserinfo info) {
	    weUserDao.add(info);
	}

	@Override
	public void addJoinInfo(JoinInfo info) {
	    joinInfoDao.add(info);
	}
	
	@Override
	public void orderCallback(String orderId) {
		//需要更新order表的order_pay_state、order_serial、order_time字段
		Date orderTime = new Date();
		FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
		String customDateTime = fdf.format(orderTime);
		
		//下单时间（yyyymmdd）+自增序列号（6位）+ 6位随机数
		String random = RandomStringUtils.random(12, false, true);
		String orderSerial = customDateTime + random;
		
		Order order = new Order();
		order.setOrderPayState("payok");
		order.setOrderTime(orderTime);
		order.setOrderSerial(orderSerial);
		order.setOrderId(Integer.parseInt(orderId));
		orderDao.update(order);
		
	}

	
    @Override
    @Transactional
    public int makeOrder(Order order) {
        //用openid和联盟id查询shopid
        String openid = order.getOrderBuyerOpenid();
        int lianmengid = order.getOrderLianmengId();
        WeUserinfo weUserinfo = weUserDao.selectByOpenIdLianmengId(openid,lianmengid);
        //根据lianmengid查price
        //int lianmengid = weUserinfo.getLianmengid();
        LianmengInfo lianmengInfo = lianmengInfoDao.selectById(lianmengid);
        
        order.setOrderShopid(weUserinfo.getShopid());
        order.setOrderPrice(lianmengInfo.getPrice());
        order.setOrderPayState("weizhifu");
        order.setOrderTime(new Date());
        orderDao.add(order);
        int id = order.getOrderId();
        return id;
    }

	@Override
	public void updateUserInfo(String openId,int lianmengId) {
		weUserDao.update(openId,lianmengId);
	}

	@Override
	public RedPackageRep redPackage(String openId, int lianmengId) {
		RedPackageRep response = new RedPackageRep();
		//frontId
		WeUserinfo weUserinfo = weUserDao.selectByOpenId(openId);
		response.setFront_one_openid(weUserinfo.getFrontOneOpenid());
		response.setFront_two_openid(weUserinfo.getFrontTwoOpenid());
		//计算随机数
		LianmengInfo lianmengInfo = lianmengInfoDao.selectById(lianmengId);
	    int oneMin = lianmengInfo.getOneMin();
	    int oneMax = lianmengInfo.getOneMax();
	    int random_num_one = RandomUtils.nextInt(oneMin,oneMax);
	    response.setRandom_num_one(random_num_one);
	    
	    int twoMin = lianmengInfo.getTwoMin();
	    int twoMax = lianmengInfo.getTwoMax();
	    int random_num_two = RandomUtils.nextInt(twoMin,twoMax);
	    response.setRandom_num_two(random_num_two);
		
		return response;
	}

	@Override
	public GetOrderInfoByIdRep getOrderById(String orderId) {
		// TODO Auto-generated method stub
		
		GetOrderInfoByIdRep response = new GetOrderInfoByIdRep();
		Order order = new Order();
		order = orderDao.selectOrderById(Integer.parseInt(orderId));
		
		response.setOrder_serial(order.getOrderSerial());
		response.setOrder_buyer_name(order.getOrderBuyerName());
		
		String phone = order.getOrderBuyerPhone().trim();
		
		String repStr = "***";
		StringBuilder nPhone = new StringBuilder(phone);
		String rePhone = nPhone.replace(3, 8, repStr).toString();
		response.setOrder_buyer_phone(rePhone);
		
		FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd");
		String buy_time = fdf.format(order.getOrderTime());
		
		response.setOrder_buy_time(buy_time);
		response.setOrder_pay_state(order.getOrderPayState());
		return response;
	}

	@Override
	public List<GetOrderInfoByIdRep> getOrderByOidAndLid(String openId, int lianmengId) {
		// TODO Auto-generated method stub
		List<GetOrderInfoByIdRep> response = new ArrayList<GetOrderInfoByIdRep>();
		
		List<OrderRepInfo> orderList = new ArrayList<OrderRepInfo>();
		System.out.println("openid: "+ openId +"  lianmengid: " + lianmengId);
		orderList = orderDao.selectOrderByOidLid(openId, lianmengId);
		System.out.println(orderList);
		
		for(OrderRepInfo order : orderList) {
			
			GetOrderInfoByIdRep repOrder = new GetOrderInfoByIdRep();
			
			FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd");
			String buy_time = fdf.format(order.getOrderBuyTime());
			repOrder.setOrder_buy_time(buy_time);
			
			repOrder.setOrder_buyer_name(order.getOrderBuyerName());
			
			String phone = order.getOrderBuyerPhone().trim();
			
			String repStr = "***";
			StringBuilder nPhone = new StringBuilder(phone);
			String rePhone = nPhone.replace(3, 8, repStr).toString();
			repOrder.setOrder_buyer_phone(rePhone);
			
			repOrder.setOrder_pay_state(order.getOrderPayState());
			repOrder.setOrder_price(order.getOrderPrice());
			repOrder.setOrder_serial(order.getOrderSerial());
			
			response.add(repOrder);
		}
		
		return response;
	}


	
}
