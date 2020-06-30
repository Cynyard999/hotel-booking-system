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
public class TargetMoneyCouponStrategyImplTest {
    @InjectMocks
    private TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy = new TargetMoneyCouponStrategyImpl();

    @Mock
    AccountService accountService;

    @Test
    public void isMatch(){
        Coupon coupon = new Coupon();
        OrderVO orderVO = new OrderVO();
        coupon.setTargetMoney(300);
        //验证未达到满减门槛
        orderVO.setPrice(100.0);
        Assert.assertFalse(targetMoneyCouponStrategy.isMatch(orderVO,coupon));
        //验证达到满减门槛
        orderVO.setPrice(400.0);
        Assert.assertTrue(targetMoneyCouponStrategy.isMatch(orderVO,coupon));
    }
}
