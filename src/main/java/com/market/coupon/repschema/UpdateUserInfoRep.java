package com.market.coupon.repschema;

public class UpdateUserInfoRep {
    int code;
    String openid;
    int lianmeng_id;
    String front_two_id;
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public int getLianmeng_id() {
        return lianmeng_id;
    }
    public void setLianmeng_id(int lianmeng_id) {
        this.lianmeng_id = lianmeng_id;
    }
    public String getFront_two_id() {
        return front_two_id;
    }
    public void setFront_two_id(String front_two_id) {
        this.front_two_id = front_two_id;
    }
    @Override
    public String toString() {
        return "UpdateUserInfoRep [code=" + code + ", openid=" + openid + ", lianmeng_id=" + lianmeng_id
                + ", front_two_id=" + front_two_id + "]";
    }

}
