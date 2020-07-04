package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Qxy
 * @Date: 2020-06-18
 */
@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {

  @Autowired
  private AccountService accountService;

  @Override
  public boolean isMatch(OrderVO orderVO, Coupon coupon) {
    //根据userId获取vipType判断是否是个人会员
    int userId = orderVO.getUserId();
    UserVO userVO = accountService.getUserInfo(userId);
    User user = new User();
    BeanUtils.copyProperties(userVO,user);
    Vip vip = accountService.getVipInfo(userId);
    //如果既是个人会员并且满足信用值大于200，则可以进一步判断是否可用生日优惠
    if (vip.getVipType() == 1 && user.getCredit() >= 200) {
      String checkInDate = orderVO.getCheckInDate().substring(5);
      String checkInYear = orderVO.getCheckInDate().substring(0, 4);
      String checkOutDate = orderVO.getCheckOutDate().substring(5);
      String checkOutYear = orderVO.getCheckOutDate().substring(0, 4);
      String birthday = vip.getBirthday().substring(5);
      // 不跨年
      if (birthday.compareTo(checkInDate) >= 0
          && birthday.compareTo(checkOutDate) <= 0
          && checkInYear.equals(checkOutYear)) {
        return true;
      }
      // 跨年
      if (birthday.compareTo(checkInDate) >= 0 && checkInYear.compareTo(checkOutYear) < 0) {
        return true;
      }
      if (birthday.compareTo(checkOutDate) <= 0 && checkInYear.compareTo(checkOutYear) < 0) {
        return true;
      }
    }
    return false;
  }
}
