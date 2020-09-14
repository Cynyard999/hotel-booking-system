package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种限时优惠策略(跟下单时间做比较！)
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        LocalDate createDate = LocalDate.parse(orderVO.getCreateDate());
        LocalDate festivalStartDate = LocalDate.parse(coupon.getStartTime());
        LocalDate festivalEndDate = LocalDate.parse(coupon.getEndTime());
        if ((createDate.isAfter(festivalStartDate)||createDate.isEqual(festivalStartDate)) && (createDate.isBefore(festivalEndDate)||createDate.isEqual(festivalEndDate))) {
            return true;
        }
        return false;
    }
}
