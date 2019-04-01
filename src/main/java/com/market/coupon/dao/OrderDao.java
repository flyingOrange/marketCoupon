package com.market.coupon.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.market.coupon.model.Order;
import com.market.coupon.model.OrderRepInfo;
import com.market.coupon.repschema.StatistcsRep;

public interface OrderDao {
    int add(Order order);
    
    void update(Order order);
    
    Order get(String openId);
    
    Order selectOrderById(int orderId);
    
    List<OrderRepInfo> selectOrderByOidLid(@Param("openId")String openId,@Param("lianmengId")int lianmengId);
    
    List<Order> getByLianmengId(int lianmengId);
    
    int statistcs(int lianmengId,int shopId);
    
    
}
