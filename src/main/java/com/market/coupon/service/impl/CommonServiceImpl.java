package com.market.coupon.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.market.coupon.model.WeUserinfo;
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
		orderDao.update(order);
		
	}

	
    @Override
    @Transactional
    public int makeOrder(Order order) {
        //查询shopid
        String openid = order.getOrderBuyerOpenid();
        WeUserinfo weUserinfo = weUserDao.selectByOpenId(openid);
        //根据openId从we_userinfo查到lianmengid
        //查price
        int lianmengid = weUserinfo.getLianmengid();
        LianmengInfo lianmengInfo = lianmengInfoDao.selectById(lianmengid);
        
        order.setOrderLianmengId(lianmengid);
        
        order.setOrderShopid(weUserinfo.getShopid());
        order.setOrderPrice(lianmengInfo.getPrice());
        order.setOrderPayState("weizhifu");
        order.setOrderTime(new Date());
        int id = orderDao.add(order);
        return id;
        
    }

	@Override
	public void updateUserInfo(String openId,int lianmengId) {
		weUserDao.update(openId,lianmengId);
	}



	
}
