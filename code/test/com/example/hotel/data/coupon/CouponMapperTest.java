package com.example.hotel.data.coupon;

import com.example.hotel.po.Coupon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class CouponMapperTest {

    private Coupon coupon;

    @Autowired
    private CouponMapper couponMapper;

    @Before
    public void init(){
        coupon = new Coupon();
        coupon.setDescription("满299减30");
        coupon.setHotelId(100);
        coupon.setCouponType(3);
        coupon.setCouponName("测试用优惠券");
        coupon.setTargetMoney(299);
        coupon.setStatus(1);
        coupon.setDiscountMoney(30);
    }

    @Test
    public void insertCoupon(){
        couponMapper.insertCoupon(coupon);
        Assert.assertEquals(1,couponMapper.selectByHotelId(100).size());

    }

    @Test
    public void selectByHotelId(){
        Assert.assertEquals(0,couponMapper.selectByHotelId(50).size());
    }

    @Test
    public void selectWebsiteCoupon(){
        Assert.assertEquals(2,couponMapper.selectWebsiteCoupon().size());
    }

    @Test
    public void deleteCoupon(){
        couponMapper.deleteCoupon(1);
    }
}
