package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.UserVO;
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
public class CompanyCouponStrategyImplTest {
    @InjectMocks
    private CompanyCouponStrategyImpl companyCouponStrategy = new CompanyCouponStrategyImpl();

    @Mock
    AccountService accountService;

    @Test
    public void isMatch(){
        Coupon coupon = new Coupon();
        coupon.setRoomNum(3);
        UserVO userVO = new UserVO();
        userVO.setCredit(0);
        Vip vip = new Vip();
        vip.setVipType(0);
        when(accountService.getUserInfo(1)).thenReturn(userVO);
        when(accountService.getVipInfo(1)).thenReturn(vip);
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(1);

        //验证既不是会员，信用值也不满200
        Assert.assertFalse(companyCouponStrategy.isMatch(orderVO,coupon));
        //验证是会员但信用值不满200
        vip.setVipType(2);
        Assert.assertFalse(companyCouponStrategy.isMatch(orderVO,coupon));
        //验证信用值满200但不是会员
        vip.setVipType(0);
        userVO.setCredit(300);
        Assert.assertFalse(companyCouponStrategy.isMatch(orderVO,coupon));
        //验证房间数不够不能使用优惠
        vip.setVipType(2);
        orderVO.setRoomNum(1);
        Assert.assertFalse(companyCouponStrategy.isMatch(orderVO,coupon));
        //验证房间数足够使用优惠
        orderVO.setRoomNum(4);
        Assert.assertTrue(companyCouponStrategy.isMatch(orderVO,coupon));




    }
}