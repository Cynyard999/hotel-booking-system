package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompanyCouponStrategyImpl  implements CouponMatchStrategy {
  @Autowired
  private AccountService accountService;

  @Override
  public boolean isMatch(OrderVO orderVO, Coupon coupon) {
    int userId = orderVO.getUserId();
    User user = accountService.getUserInfo(userId);
    Vip vip = accountService.getVipInfo(userId);
    if (vip.getVipType() == 2 && user.getCredit() >= 200) {
      if (orderVO.getRoomNum()>=coupon.getRoomNum()){
        return true;
      }
    }
    return false;
  }
}
