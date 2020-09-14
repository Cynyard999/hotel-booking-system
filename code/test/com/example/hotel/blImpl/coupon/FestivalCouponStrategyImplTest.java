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
public class FestivalCouponStrategyImplTest {
    @InjectMocks
    private FestivalCouponStrategyImpl festivalCouponStrategy = new FestivalCouponStrategyImpl();

    @Mock
    AccountService accountService;

    @Test
    public void isMatch(){
        Coupon coupon = new Coupon();
        OrderVO orderVO = new OrderVO();

        //验证入住时间未到节日
        orderVO.setCheckInDate("2020-06-01");
        coupon.setStartTime("2020-06-05");
        coupon.setEndTime("2020-06-10");
        Assert.assertFalse(festivalCouponStrategy.isMatch(orderVO,coupon));
        //验证是入住时间已过节日
        orderVO.setCheckInDate("2020-06-01");
        coupon.setStartTime("2019-06-05");
        coupon.setEndTime("2019-06-10");
        Assert.assertFalse(festivalCouponStrategy.isMatch(orderVO,coupon));
        //验证满足节日优惠
        orderVO.setCheckInDate("2020-06-07");
        coupon.setStartTime("2020-06-05");
        coupon.setEndTime("2020-06-10");
        Assert.assertTrue(festivalCouponStrategy.isMatch(orderVO,coupon));
    }
}

