package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FestivalCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种节日优惠策略(跟入住时间做比较！)
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        LocalDate checkInDate = LocalDate.parse(orderVO.getCheckInDate());
        LocalDate festivalStartDate = LocalDate.parse(coupon.getStartTime());
        LocalDate festivalEndDate = LocalDate.parse(coupon.getEndTime());
        if (    (checkInDate.isAfter(festivalStartDate)||checkInDate.isEqual(festivalStartDate))
                && (checkInDate.isBefore(festivalEndDate)||checkInDate.isEqual(festivalEndDate))) {
            return true;
        }
        return false;
    }
}
