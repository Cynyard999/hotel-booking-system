package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

/**
 * @Author: Zzn
 * @Date: 2020-05-31
 */
@Service
public class TargetMoneyCouponStrategyImpl implements CouponMatchStrategy {

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        //判断订单是否满足某种满减金额优惠策略
        if (orderVO.getPrice() >= coupon.getTargetMoney()) {
            return true;
        }
        return false;
    }
}
