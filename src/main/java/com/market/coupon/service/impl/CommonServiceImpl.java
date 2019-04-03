package com.market.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.market.coupon.dao.JoinInfoDao;
import com.market.coupon.dao.LianmengInfoDao;
import com.market.coupon.dao.OrderDao;
import com.market.coupon.dao.RedRecordDao;
import com.market.coupon.dao.ShopInfoDao;
import com.market.coupon.dao.WeUserDao;
import com.market.coupon.model.JoinInfo;
import com.market.coupon.model.LianmengInfo;
import com.market.coupon.model.Order;
import com.market.coupon.model.OrderRepInfo;
import com.market.coupon.model.RedRecordInfo;
import com.market.coupon.model.ShopInfo;
import com.market.coupon.model.WeUserinfo;
import com.market.coupon.repschema.GetOrderInfoByIdRep;
import com.market.coupon.repschema.OrderListInfo;
import com.market.coupon.repschema.OrderListRep;
import com.market.coupon.repschema.RedPackageRep;
import com.market.coupon.repschema.StatistcsInfo;
import com.market.coupon.repschema.StatistcsRep;
import com.market.coupon.repschema.UpdateUserInfoRep;
import com.market.coupon.service.CommonService;

@Service("CommonService")
public class CommonServiceImpl implements CommonService {

    @Resource
    private WeUserDao weUserDao;
    @Resource
    private JoinInfoDao joinInfoDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private LianmengInfoDao lianmengInfoDao;
    @Resource
    private RedRecordDao redRecordDao;
    @Resource
    private ShopInfoDao shopInfoDao;

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
        // 需要更新order表的order_pay_state、order_serial、order_time字段
        Date orderTime = new Date();
        FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
        String customDateTime = fdf.format(orderTime);

        // 下单时间（yyyymmdd）+自增序列号（6位）+ 6位随机数
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
        // 用openid和联盟id查询shopid
        String openid = order.getOrderBuyerOpenid();
        int lianmengid = order.getOrderLianmengId();
        WeUserinfo weUserinfo = weUserDao.selectByOpenIdLianmengId(openid, lianmengid);
        // 根据lianmengid查price
        // int lianmengid = weUserinfo.getLianmengid();
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
    public UpdateUserInfoRep updateUserInfo(String openId, int lianmengId) {
        UpdateUserInfoRep rep = new UpdateUserInfoRep();
        rep.setOpenid(openId);
        rep.setLianmeng_id(lianmengId);
        WeUserinfo weUserinfo = weUserDao.selectByOpenIdLianmengId(openId, lianmengId);
        // 0 code--0:该联盟没有该用户
        if (weUserinfo == null) {
            rep.setCode(0);
            return rep;
        }
        // 3 如果该用户front_two字段已经更新，则返回值补全（包括front_two）
        if (!StringUtils.isEmpty(weUserinfo.getFrontTwoOpenid())) {
            rep.setCode(3);
            rep.setFront_two_id(weUserinfo.getFrontTwoOpenid());
            return rep;
        }
        // 2
        // 该联盟有该用户，但是front_two字段通过计算为空，则front_two_id设为“end”，返回值补全（front_two为end）
        String frontOneOpenId = weUserinfo.getFrontOneOpenid();
        if (StringUtils.isEmpty(frontOneOpenId)) {
            // 更新front_two_id设为“end”
            weUserDao.update(openId, lianmengId, "end");
            rep.setCode(2);
            rep.setFront_two_id("end");
            return rep;
        }
        WeUserinfo userinfo = weUserDao.selectByOpenIdLianmengId(frontOneOpenId, lianmengId);
        if ((userinfo == null) || (userinfo.getFrontOneOpenid() == null)) {
            weUserDao.update(openId, lianmengId, "end");
            rep.setCode(2);
            rep.setFront_two_id("end");
            return rep;
        }
        // 1 可以找到front_two,并更新成功，返回值补全
        weUserDao.update(openId, lianmengId, userinfo.getFrontOneOpenid());
        rep.setCode(1);
        rep.setFront_two_id(userinfo.getFrontOneOpenid());
        return rep;
    }

    @Override
    public RedPackageRep redPackage(String openId, int lianmengId) {
        RedPackageRep response = new RedPackageRep();
        // frontId
        WeUserinfo weUserinfo = weUserDao.selectByOpenId(openId);
        response.setFront_one_openid(weUserinfo.getFrontOneOpenid());
        response.setFront_two_openid(weUserinfo.getFrontTwoOpenid());
        // 计算随机数
        LianmengInfo lianmengInfo = lianmengInfoDao.selectById(lianmengId);
        int oneMin = lianmengInfo.getOneMin();
        int oneMax = lianmengInfo.getOneMax();
        int random_num_one = RandomUtils.nextInt(oneMin, oneMax);
        response.setRandom_num_one(random_num_one);

        int twoMin = lianmengInfo.getTwoMin();
        int twoMax = lianmengInfo.getTwoMax();
        int random_num_two = RandomUtils.nextInt(twoMin, twoMax);
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
        System.out.println("openid: " + openId + "  lianmengid: " + lianmengId);
        orderList = orderDao.selectOrderByOidLid(openId, lianmengId);
        System.out.println(orderList);

        for (OrderRepInfo order : orderList) {

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

    @Override
    public void addRedPackRecordInfo(RedRecordInfo redRecord) {
        // TODO Auto-generated method stub
        redRecordDao.insertRedRecord(redRecord);
    }

    @Override
    public OrderListRep orderList(int lianmengId) {
        OrderListRep rep = new OrderListRep();
        List<Order> orders = orderDao.getByLianmengId(lianmengId);
        if (CollectionUtils.isEmpty(orders)) {
            rep.setCode(0);
            return rep;
        }
        List<OrderListInfo> list = orders.stream().map(elem -> {
            OrderListInfo info = new OrderListInfo();
            String openId = elem.getOrderBuyerOpenid();
            int lianmengid = elem.getOrderLianmengId();
            WeUserinfo weUserinfo = weUserDao.selectByOpenIdLianmengId(openId, lianmengid);
            info.setBuyer_headpic(weUserinfo.getuHeadPic());
            info.setBuyer_name(elem.getOrderBuyerName());
            info.setBuyer_nickname(weUserinfo.getNickname());
            info.setBuyer_phone(elem.getOrderBuyerPhone());

            return info;
        }).collect(Collectors.toList());
        rep.setList(list);
        return rep;
    }

    @Override
    public boolean ifSentRedPackByOrderId(int orderId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public StatistcsRep statistcs(int lianmengId) {
        StatistcsRep rep = new StatistcsRep();
        // 联盟不存在
        LianmengInfo lianmengInfo = lianmengInfoDao.selectById(lianmengId);
        if (lianmengInfo == null) {
            rep.setCode(0);
            return rep;
        }
        // 从shop_info中查询中此联盟中的所有shiop
        List<ShopInfo> shopInfos = shopInfoDao.selectByLianmengId(lianmengId);
        if (CollectionUtils.isEmpty(shopInfos)) {
            rep.setCode(0);
            return rep;
        }
        List<Integer> shopIds = shopInfos.stream().map(info -> {
            return info.getShopId();
        }).collect(Collectors.toList());
        // 根据shipId和lianmengId联合查询
        Map<Integer,Integer> orderCountMap = new HashMap<Integer,Integer>();
        Map<Integer,Integer> pushCountMap = new HashMap<Integer,Integer>();
        List<Map<Integer,Object>> orderCountList = orderDao.statistcs(lianmengId,shopIds);
        List<Map<Integer,Object>> pushCountList = weUserDao.statistcs(lianmengId,shopIds);
        if(!CollectionUtils.isEmpty(orderCountList)) {
            orderCountList.forEach(elem->{
                Integer shopId = Integer.valueOf(String.valueOf(elem.get("shopId")));
                Integer totally = Integer.valueOf(String.valueOf(elem.get("totally")));
                orderCountMap.put(shopId,totally);
            });
        }
        if(!CollectionUtils.isEmpty(pushCountList)) {
            pushCountList.forEach(elem->{
                Integer shopId = Integer.valueOf(String.valueOf(elem.get("shopId")));
                Integer totally = Integer.valueOf(String.valueOf(elem.get("totally")));
                pushCountMap.put(shopId,totally);
            });
        }


        List<StatistcsInfo> list = shopInfos.stream().map(shipInfo -> {
            int shopId = shipInfo.getShopId();
            String shopName = shipInfo.getShopName();
            Integer orderCount = orderCountMap.get(shopId);
            Integer pushCount = pushCountMap.get(shopId);
            
            StatistcsInfo info = new StatistcsInfo();
            info.setPush_count((pushCount==null?0:pushCount));
            info.setOrder_count((orderCount==null?0:orderCount));
            info.setShop_name(shopName);

            return info;
        }).collect(Collectors.toList());
        rep.setCode(1);
        rep.setList(list);
        return rep;
    }

}
