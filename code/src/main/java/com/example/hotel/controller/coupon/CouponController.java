package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Zzn
 * @Date: 2020-06-14
 */
@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/addCoupon")
    public ResponseVO addCoupon(@RequestBody CouponVO couponVO){
        return ResponseVO.buildSuccess(couponService.addCoupon(couponVO));
    }

    @GetMapping("/{hotelId}/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/websiteAllCoupon")
    public ResponseVO getWebsiteAllCoupon(){
        return ResponseVO.buildSuccess(couponService.getWebsiteAllCoupon());
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut) {
        OrderVO requestOrderVO = new OrderVO();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        requestOrderVO.setCreateDate(curdate);
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }


    @PostMapping("/{couponId}/deleteCoupon")
    public ResponseVO deleteCoupon(@PathVariable Integer couponId){
        return couponService.deleteCoupon(couponId);
    }
}
