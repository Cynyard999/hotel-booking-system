package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Zzn
 * @Date: 2020-06-14
 */
@Service
public class CouponServiceImpl implements CouponService {

  private final static String ADD_Fall = "优惠添加失败";
  private final static String DELETE_Fall = "优惠删除失败";

  @Autowired
  private BirthdayCouponStrategyImpl birthdayCouponStrategy;
  @Autowired
  private TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;
  @Autowired
  private TimeCouponStrategyImpl timeCouponStrategy;
  @Autowired
  private RoomNumCouponStrategyImpl roomNumCouponStrategy;
  @Autowired
  private FestivalCouponStrategyImpl festivalCouponStrategy;
  @Autowired
  private CompanyCouponStrategyImpl companyCouponStrategy;
  @Autowired
  private CouponMapper couponMapper;

  @Autowired
  private AccountService accountService;

  @Override
  public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {
    List<Coupon> availAbleCoupons = new ArrayList<>();
    //首先获取对应酒店的优惠列表以及网站的优惠列表
    List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());
    List<Coupon> websiteCoupons = getWebsiteAllCoupon();
    //判断是否满足某一条会员优惠
    for (int i = 0; i < websiteCoupons.size(); i++) {
      if (websiteCoupons.get(i).getCouponType() == 1) {
        //生日特惠
        if (birthdayCouponStrategy.isMatch(orderVO, websiteCoupons.get(i))) {
          availAbleCoupons.add(websiteCoupons.get(i));
        }
      } else if (websiteCoupons.get(i).getCouponType() == 6) {
        //企业特惠
        if (companyCouponStrategy.isMatch(orderVO, websiteCoupons.get(i))) {
          availAbleCoupons.add(websiteCoupons.get(i));
        }
      } else {
        websiteCoupons.get(i)
            .setCouponName("异常优惠券，类型错误：" + String.valueOf(websiteCoupons.get(i).getCouponType()));
        availAbleCoupons.add(websiteCoupons.get(i));
      }
    }
    //如果是企业会员，则无法使用其他优惠券，直接返回企业会员部分匹配的优惠列表
    if (accountService.getVipInfo(orderVO.getUserId()).getVipType()==2){
      return availAbleCoupons;
    }
    //判断是否满足某一条酒店优惠
    for (int i = 0; i < hotelCoupons.size(); i++) {
      if (hotelCoupons.get(i).getCouponType() == 2) {
        //多间特惠
        if (roomNumCouponStrategy.isMatch(orderVO, hotelCoupons.get(i))) {
          availAbleCoupons.add(hotelCoupons.get(i));
        }
      } else if (hotelCoupons.get(i).getCouponType() == 3) {
        //满减特惠
        if (targetMoneyCouponStrategy.isMatch(orderVO, hotelCoupons.get(i))) {
          availAbleCoupons.add(hotelCoupons.get(i));
        }
      } else if (hotelCoupons.get(i).getCouponType() == 4) {
        //限时特惠
        if (timeCouponStrategy.isMatch(orderVO, hotelCoupons.get(i))) {
          availAbleCoupons.add(hotelCoupons.get(i));
        }
      } else if (hotelCoupons.get(i).getCouponType() == 5) {
        //节日特惠
        if (festivalCouponStrategy.isMatch(orderVO, hotelCoupons.get(i))) {
          availAbleCoupons.add(hotelCoupons.get(i));
        }
      } else {
        hotelCoupons.get(i)
            .setCouponName("异常优惠券，类型错误：" + String.valueOf(hotelCoupons.get(i).getCouponType()));
        availAbleCoupons.add(hotelCoupons.get(i));
      }
    }
    //返回所有符合条件的优惠列表
    return availAbleCoupons;
  }

  @Override
  public List<Coupon> getHotelAllCoupon(Integer hotelId) {
    //返回酒店对应的优惠政策
    return couponMapper.selectByHotelId(hotelId);
  }

  @Override
  public List<Coupon> getWebsiteAllCoupon() {
    //获取网站的优惠政策
    return couponMapper.selectWebsiteCoupon();
  }

  @Override
  public ResponseVO addCoupon(CouponVO couponVO) {
    //coupon,VO->PO
    Coupon coupon = new Coupon();
    BeanUtils.copyProperties(couponVO,coupon);
    coupon.setStatus(1);
    //添加优惠，如果成功便返回true，否则返回优惠添加失败
    try{
      couponMapper.insertCoupon(coupon);
    } catch(Exception e){
      e.printStackTrace();
      return ResponseVO.buildFailure(ADD_Fall);
    }
    return ResponseVO.buildSuccess(true);
  }

  @Override
  public ResponseVO deleteCoupon(int couponId) {
    //删除优惠，如果成功便返回true，否则返回优惠删除失败
    try{
      couponMapper.deleteCoupon(couponId);
    } catch(Exception e){
      e.printStackTrace();
      return ResponseVO.buildFailure(DELETE_Fall);
    }
    return ResponseVO.buildSuccess(true);
  }

}
