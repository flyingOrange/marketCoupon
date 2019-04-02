package com.market.coupon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.market.coupon.model.RedRecordInfo;
import com.market.coupon.model.WeUserinfo;

public interface WeUserDao {
    
   void add(WeUserinfo info);
   
   WeUserinfo selectByOpenId(String openid);
   
   WeUserinfo selectByOpenIdLianmengId(@Param("openId")String openId,@Param("lianmengId")int lianmengid);
   
   void update(@Param("openId") String openId,@Param("lianmengId") int lianmengId,@Param("frontTwo") String frontTwo);
   
   List<Map<Integer,Object>> statistcs(@Param("lianmengId")int lianmengId,@Param("shopIds")List<Integer> shopIds);
   
}
