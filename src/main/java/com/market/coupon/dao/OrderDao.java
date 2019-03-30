package com.market.coupon.dao;

import java.util.List;

import com.market.coupon.model.Order;

public interface OrderDao {
    int add(Order order);
    
    void update(Order order);
    
    List<Order> getByLianmengId(int lianmengId);
}
