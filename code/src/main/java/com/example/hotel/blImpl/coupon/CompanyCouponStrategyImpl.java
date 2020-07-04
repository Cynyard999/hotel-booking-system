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
public class CompanyCouponStrategyImpl  implements CouponMatchStrategy {
  @Autowired
  private AccountService accountService;

  @Override
  public boolean isMatch(OrderVO orderVO, Coupon coupon) {
    //根据userId获取vipType判断是否是企业会员
    int userId = orderVO.getUserId();
    UserVO userVO = accountService.getUserInfo(userId);
    User user = new User();
    BeanUtils.copyProperties(userVO,user);
    Vip vip = accountService.getVipInfo(userId);
    //如果既是企业会员并且满足信用值大于200，则可以进一步判断是否可用企业
    if (vip.getVipType() == 2 && user.getCredit() >= 200) {
      if (orderVO.getRoomNum()>=coupon.getRoomNum()){
        return true;
      }
    }
    return false;
  }
}
