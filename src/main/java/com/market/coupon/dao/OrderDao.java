package com.market.coupon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.market.coupon.model.Order;
import com.market.coupon.model.OrderRepInfo;

public interface OrderDao {
    int add(Order order);
    
    void update(Order order);
    
    Order get(String openId);
    
    Order selectOrderById(int orderId);
    
    List<OrderRepInfo> selectOrderByOidLid(@Param("openId")String openId,@Param("lianmengId")int lianmengId);
    
    List<Order> getByLianmengId(int lianmengId);
    
    List<Map<Integer,Object>> statistcs(@Param("lianmengId")int lianmengId,@Param("shopIds")List<Integer> shopIds);
    
    
}
