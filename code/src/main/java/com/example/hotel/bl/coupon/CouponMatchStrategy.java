package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;

/**
 * @Author: Zzn
 * @Date: 2020-06-14
 */
public interface CouponMatchStrategy {
    /**
     * 判断订单是否满足使用某优惠券
     * @param orderVO 订单信息
     * @param coupon 优惠券信息
     * @return 如果满足则返回true，否则返回false
     */
    boolean isMatch(OrderVO orderVO, Coupon coupon);

}
