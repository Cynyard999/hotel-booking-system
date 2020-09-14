package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BirthdayCouponStrategyImplTest {
    @InjectMocks
    private BirthdayCouponStrategyImpl birthdayCouponStrategy = new BirthdayCouponStrategyImpl();

    @Mock
    AccountService accountService;

    @Test
    public void isMatch(){
        Coupon coupon = new Coupon();
        User user = new User();
        user.setCredit(0);
        Vip vip = new Vip();
        vip.setVipType(0);
        when(accountService.getUserInfo(1)).thenReturn(user);
        when(accountService.getVipInfo(1)).thenReturn(vip);
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(1);

        //验证既不是会员，信用值也不满200
        Assert.assertFalse(birthdayCouponStrategy.isMatch(orderVO,coupon));
        //验证是会员但信用值不满200
        vip.setVipType(1);
        Assert.assertFalse(birthdayCouponStrategy.isMatch(orderVO,coupon));
        //验证信用值满200但不是会员
        vip.setVipType(0);
        user.setCredit(300);
        Assert.assertFalse(birthdayCouponStrategy.isMatch(orderVO,coupon));
        //验证不跨年可用生日
        vip.setVipType(1);
        orderVO.setCheckInDate("2020-06-01");
        orderVO.setCheckOutDate("2020-06-03");
        vip.setBirthday("2020-06-02");
        Assert.assertTrue(birthdayCouponStrategy.isMatch(orderVO,coupon));
        //验证跨年可用生日
        orderVO.setCheckInDate("2019-12-30");
        orderVO.setCheckOutDate("2020-01-05");
        vip.setBirthday("2020-01-02");
        Assert.assertTrue(birthdayCouponStrategy.isMatch(orderVO,coupon));
        //验证未到生日
        orderVO.setCheckInDate("2020-06-01");
        orderVO.setCheckOutDate("2020-06-03");
        vip.setBirthday("2020-06-10");
        Assert.assertFalse(birthdayCouponStrategy.isMatch(orderVO,coupon));




    }
}
