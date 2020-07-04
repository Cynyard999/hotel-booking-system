package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: Zzn
 * @Date: 2020-06-14
 */
public interface CouponService {
    /**
     * 返回某一订单可用的优惠策略列表
     * @param orderVO 订单信息
     * @return 可用的优惠政策列表
     */
    List<Coupon> getMatchOrderCoupon(OrderVO orderVO);

    /**
     * 查看某个酒店提供的所有优惠策略（包括失效的）
     * @param hotelId 酒店id
     * @return 酒店优惠政策列表
     */
    List<Coupon> getHotelAllCoupon(Integer hotelId);

    /**
     * 得到网站提供的所有优惠策略（包括失效的）
     * @return 网站优惠政策列表
     */
    List<Coupon> getWebsiteAllCoupon();

    /**
     * 添加优惠策略
     * @param couponVO 要添加的优惠策略信息
     * @return 返回添加结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO addCoupon(CouponVO couponVO);

    /**
     * 删除优惠策略
     * @param couponId 要删除的coupon的id
     * @return 返回删除结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO deleteCoupon(int couponId);
}
