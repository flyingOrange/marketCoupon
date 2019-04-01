package com.market.coupon.model;

public class ShopInfo {
    int shopId;
    String shopName;
    String shopHangye;
    int shopLianmengId;
    public int getShopId() {
        return shopId;
    }
    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopHangye() {
        return shopHangye;
    }
    public void setShopHangye(String shopHangye) {
        this.shopHangye = shopHangye;
    }
    public int getShopLianmengId() {
        return shopLianmengId;
    }
    public void setShopLianmengId(int shopLianmengId) {
        this.shopLianmengId = shopLianmengId;
    }
    @Override
    public String toString() {
        return "ShopInfo [shopId=" + shopId + ", shopName=" + shopName + ", shopHangye=" + shopHangye
                + ", shopLianmengId=" + shopLianmengId + "]";
    }

}
