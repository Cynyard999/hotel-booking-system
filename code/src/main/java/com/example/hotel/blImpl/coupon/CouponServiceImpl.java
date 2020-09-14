package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());
    List<Coupon> websiteCoupons = getWebsiteAllCoupon();
    List<Coupon> availAbleCoupons = new ArrayList<>();
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

    if (accountService.getVipInfo(orderVO.getUserId()).getVipType()==2){
      return availAbleCoupons;
    }


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

    return availAbleCoupons;
  }

  @Override
  public List<Coupon> getHotelAllCoupon(Integer hotelId) {
    return couponMapper.selectByHotelId(hotelId);
  }

  @Override
  public List<Coupon> getWebsiteAllCoupon() {
    return couponMapper.selectWebsiteCoupon();
  }

  @Override
  public ResponseVO addCoupon(CouponVO couponVO) {
    Coupon coupon = new Coupon();
    coupon.setDescription(couponVO.getDescription());
    coupon.setHotelId(couponVO.getHotelId());
    coupon.setCouponType(couponVO.getCouponType());
    coupon.setCouponName(couponVO.getCouponName());
    coupon.setRoomNum(couponVO.getRoomNum());
    coupon.setTargetMoney(couponVO.getTargetMoney());
    coupon.setDiscount(couponVO.getDiscount());
    coupon.setStatus(1);
    coupon.setStartTime(couponVO.getStartTime());
    coupon.setEndTime(couponVO.getEndTime());
    coupon.setDiscountMoney(couponVO.getDiscountMoney());
    int result = couponMapper.insertCoupon(coupon);
    if(result==0)return ResponseVO.buildFailure(ADD_Fall);
    return ResponseVO.buildSuccess(true);
  }

  @Override
  public ResponseVO deleteCoupon(int couponId) {
    int result = couponMapper.deleteCoupon(couponId);
    if(result==0) {
      return ResponseVO.buildFailure(DELETE_Fall);
    }
    return ResponseVO.buildSuccess(true);
  }

}
