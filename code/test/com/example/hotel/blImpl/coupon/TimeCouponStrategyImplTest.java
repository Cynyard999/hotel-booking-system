package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TimeCouponStrategyImplTest {
    @InjectMocks
    private TimeCouponStrategyImpl timeCouponStrategy = new TimeCouponStrategyImpl();

    @Mock
    AccountService accountService;

    @Test
    public void isMatch(){
        Coupon coupon = new Coupon();
        OrderVO orderVO = new OrderVO();

        //验证下单时间未到限时优惠范围
        orderVO.setCreateDate("2020-06-01");
        coupon.setStartTime("2020-06-05");
        coupon.setEndTime("2020-06-10");
        Assert.assertFalse(timeCouponStrategy.isMatch(orderVO,coupon));
        //验证下单时间已过限时优惠范围
        orderVO.setCreateDate("2020-06-01");
        coupon.setStartTime("2019-06-05");
        coupon.setEndTime("2019-06-10");
        Assert.assertFalse(timeCouponStrategy.isMatch(orderVO,coupon));
        //验证下单时间在限时优惠范围，优惠券可用
        orderVO.setCreateDate("2020-06-07");
        coupon.setStartTime("2020-06-05");
        coupon.setEndTime("2020-06-10");
        Assert.assertTrue(timeCouponStrategy.isMatch(orderVO,coupon));
    }
}
