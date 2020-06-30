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
public class RoomNumCouponStrategyImplTset {
    @InjectMocks
    private RoomNumCouponStrategyImpl roomNumCouponStrategy = new RoomNumCouponStrategyImpl();

    @Mock
    AccountService accountService;

    @Test
    public void isMatch(){
        Coupon coupon = new Coupon();
        OrderVO orderVO = new OrderVO();
        coupon.setRoomNum(3);
        //验证房间数未到
        orderVO.setRoomNum(1);
        Assert.assertFalse(roomNumCouponStrategy.isMatch(orderVO,coupon));
        //验证房间数达到
        orderVO.setRoomNum(4);
        Assert.assertTrue(roomNumCouponStrategy.isMatch(orderVO,coupon));
    }
}
