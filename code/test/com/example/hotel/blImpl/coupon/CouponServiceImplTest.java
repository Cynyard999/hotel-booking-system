package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CouponServiceImplTest {
    private final static String ADD_Fall = "优惠添加失败";
    private final static String DELETE_Fall = "优惠删除失败";
    private List<Coupon> list1;
    private List<Coupon> list2;

    @InjectMocks
    private CouponService couponService = new CouponServiceImpl();

    @Mock
    private CouponMapper couponMapper;
    @Mock
    private BirthdayCouponStrategyImpl birthdayCouponStrategy;
    @Mock
    private TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;
    @Mock
    private TimeCouponStrategyImpl timeCouponStrategy;
    @Mock
    private RoomNumCouponStrategyImpl roomNumCouponStrategy;
    @Mock
    private FestivalCouponStrategyImpl festivalCouponStrategy;
    @Mock
    private CompanyCouponStrategyImpl companyCouponStrategy;
    @Mock
    private AccountService accountService;



    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        list1 = new ArrayList<>();
        Coupon coupon = new Coupon();
        coupon.setDescription("满3间八折");
        coupon.setCouponType(2);
        list1.add(coupon);
        coupon = new Coupon();
        coupon.setDescription("满500-100优惠");
        coupon.setCouponType(3);
        list1.add(coupon);
        coupon = new Coupon();
        coupon.setDescription("软工二大作业DDL超级优惠");
        coupon.setCouponType(4);
        list1.add(coupon);
        coupon = new Coupon();
        coupon.setDescription("端午节优惠");
        coupon.setCouponType(5);
        list1.add(coupon);
        coupon = new Coupon();
        coupon.setDescription("酒店异常优惠用例");
        coupon.setCouponType(0);
        list1.add(coupon);
        list2 = new ArrayList<>();
        coupon = new Coupon();
        coupon.setDescription("会员生日8折优惠");
        coupon.setCouponType(1);
        list2.add(coupon);
        coupon = new Coupon();
        coupon.setDescription("NJUSE企业优惠");
        coupon.setCouponType(6);
        list2.add(coupon);
        coupon = new Coupon();
        coupon.setDescription("网站异常优惠用例");
        coupon.setCouponType(0);
        list2.add(coupon);
        when(couponMapper.selectByHotelId(1)).thenReturn(list1);
        when(couponMapper.selectWebsiteCoupon()).thenReturn(list2);
    }

    @Test
    public void getMatchOrderCoupon(){
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(1);
        orderVO.setHotelId(1);
        Vip vip = new Vip();
        vip.setVipType(2);
        when(accountService.getVipInfo(1)).thenReturn(vip);
        when(birthdayCouponStrategy.isMatch(orderVO,list2.get(0))).thenReturn(true).thenReturn(false);
        when(companyCouponStrategy.isMatch(orderVO,list2.get(1))).thenReturn(true).thenReturn(false);

        //在vip.setVipType==2时，验证websiteCoupons判断规则中的三个分支（个人、会员、异常），不到酒店优惠部分就return了
        List<Coupon> list = couponService.getMatchOrderCoupon(orderVO);
        Assert.assertEquals("会员生日8折优惠",list.get(0).getDescription());
        Assert.assertEquals("NJUSE企业优惠",list.get(1).getDescription());
        Assert.assertEquals("异常优惠券，类型错误：0",list.get(2).getCouponName());

        vip.setVipType(0);
        when(roomNumCouponStrategy.isMatch(orderVO,list1.get(0))).thenReturn(true);
        when(targetMoneyCouponStrategy.isMatch(orderVO,list1.get(1))).thenReturn(true);
        when(timeCouponStrategy.isMatch(orderVO,list1.get(2))).thenReturn(true);
        when(festivalCouponStrategy.isMatch(orderVO,list1.get(3))).thenReturn(true);

        //在vip.setVipType！=2时，验证hotelCoupons中的5个分支（注，list会有6个，第一个是websiteCoupons中的异常情况）
        list = couponService.getMatchOrderCoupon(orderVO);
        Assert.assertEquals("异常优惠券，类型错误：0",list.get(0).getCouponName());
        Assert.assertEquals("满3间八折",list.get(1).getDescription());
        Assert.assertEquals("满500-100优惠",list.get(2).getDescription());
        Assert.assertEquals("软工二大作业DDL超级优惠",list.get(3).getDescription());
        Assert.assertEquals("端午节优惠",list.get(4).getDescription());
        Assert.assertEquals("异常优惠券，类型错误：0",list.get(5).getCouponName());


    }

    @Test
    public void getHotelAllCoupon(){
        List<Coupon> list = couponService.getHotelAllCoupon(1);
        Assert.assertEquals("满3间八折",list.get(0).getDescription());
        Assert.assertEquals("满500-100优惠",list.get(1).getDescription());
        Assert.assertEquals("软工二大作业DDL超级优惠",list.get(2).getDescription());
        Assert.assertEquals("端午节优惠",list.get(3).getDescription());
        Assert.assertEquals("酒店异常优惠用例",list.get(4).getDescription());
    }

    @Test
    public void getWebsiteAllCoupon(){
        List<Coupon> list = couponService.getWebsiteAllCoupon();
        Assert.assertEquals("会员生日8折优惠",list.get(0).getDescription());
        Assert.assertEquals("NJUSE企业优惠",list.get(1).getDescription());
        Assert.assertEquals("网站异常优惠用例",list.get(2).getDescription());
    }

    @Test
    public void addCoupon(){
        CouponVO couponVO = new CouponVO();
        Coupon coupon = new Coupon();
        when(couponMapper.insertCoupon(coupon)).thenReturn(0).thenReturn(1);
        //第一次添加失败
        Assert.assertEquals(ADD_Fall,couponService.addCoupon(couponVO).getMessage());
        //之后都添加成功
        Assert.assertFalse(couponService.addCoupon(couponVO).getSuccess());
    }

    @Test
    public void deleteCoupon(){
        when(couponMapper.deleteCoupon(1)).thenReturn(1);
        when(couponMapper.deleteCoupon(0)).thenReturn(0);

        Assert.assertTrue(couponService.deleteCoupon(1).getSuccess());
        Assert.assertEquals(DELETE_Fall,couponService.deleteCoupon(0).getMessage());
    }


}
