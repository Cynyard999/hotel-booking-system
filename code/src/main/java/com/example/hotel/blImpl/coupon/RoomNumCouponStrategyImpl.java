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
public class RoomNumCouponStrategyImpl implements CouponMatchStrategy {

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        //判断订单是否满足某种多间优惠策略
        if (orderVO.getRoomNum() >= coupon.getRoomNum()) {
            return true;
        }
        return false;
    }
}
