package com.market.coupon.dao;

import java.util.List;

import com.market.coupon.model.ShopInfo;

public interface ShopInfoDao {
    
    List<ShopInfo> selectByLianmengId(int lianmengId);

}
