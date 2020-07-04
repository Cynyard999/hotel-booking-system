package com.example.hotel.controller.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.vo.CouponVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Zzn
 * @Date: 2020-06-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponControllerTest {
    @Autowired
    private CouponController couponController;

    @Test
    @Transactional
    @Rollback
    public void addCoupon(){
        CouponVO couponVO = new CouponVO();
        couponVO.setDescription("测试coupon！");
        couponVO.setHotelId(1);
        couponVO.setCouponName("");
        couponVO.setCouponType(1);

        Assert.assertTrue(couponController.addCoupon(couponVO).getSuccess());
        List<Coupon> coupons = (List<Coupon>)couponController.getHotelAllCoupons(1).getContent();
        Assert.assertEquals("测试coupon！",coupons.get(coupons.size()-1).getDescription());
    }

    @Test
    @Transactional
    @Rollback
    public void getHotelAllCoupons() {
        Assert.assertEquals(4,((List<Coupon>)couponController.getHotelAllCoupons(2).getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void getWebsiteAllCoupon(){
        Assert.assertEquals(2,((List<Coupon>)couponController.getWebsiteAllCoupon().getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void getOrderMatchCoupons() {
        List<Coupon> coupons = (List<Coupon>)couponController.getOrderMatchCoupons(1,2,299.0,3,"2020-10-05","2020-10-06").getContent();
        Assert.assertEquals("多间优惠券", coupons.get(0).getCouponName());
        Assert.assertEquals("节日优惠券", coupons.get(1).getCouponName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteCoupon(){
        Assert.assertTrue(couponController.deleteCoupon(1).getSuccess());
        Assert.assertEquals(3,((List<Coupon>)couponController.getHotelAllCoupons(2).getContent()).size());
    }
}
