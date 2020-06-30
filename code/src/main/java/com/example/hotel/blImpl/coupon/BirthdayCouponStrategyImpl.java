package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author cynyard
 */
@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {

  @Autowired
  private AccountService accountService;

  @Override
  public boolean isMatch(OrderVO orderVO, Coupon coupon) {
    int userId = orderVO.getUserId();
    User user = accountService.getUserInfo(userId);
    Vip vip = accountService.getVipInfo(userId);
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
