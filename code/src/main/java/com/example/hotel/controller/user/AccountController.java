package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Qxy
 * @Date: 2020-05-25
 */
@RestController()
@RequestMapping("/api/user")
public class AccountController {

  private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
  @Autowired
  private AccountService accountService;

  @PostMapping("/login")
  public ResponseVO login(@RequestBody UserForm userForm) {
    UserVO userVO = accountService.login(userForm);
    if (userVO == null) {
      return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
    }
    return ResponseVO.buildSuccess(userVO);
  }

  @PostMapping("/register")
  public ResponseVO registerAccount(@RequestBody UserVO userVO) {
    return accountService.registerAccount(userVO);
  }

  @GetMapping("/{id}/getUserInfo")
  public ResponseVO getUserInfo(@PathVariable int id) {
    UserVO userVO = accountService.getUserInfo(id);
    if (userVO == null) {
      return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
    }
    return ResponseVO.buildSuccess(userVO);
  }

  @GetMapping("/{id}/getVipInfo")
  public ResponseVO getVipInfo(@PathVariable int id) {
    Vip vip = accountService.getVipInfo(id);
    return ResponseVO.buildSuccess(vip);
  }

  @PostMapping("/{id}/userInfo/update")
  public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO, @PathVariable int id) {
    return accountService.updateUserInfo(id, userInfoVO.getPassword(), userInfoVO.getUserName(),
        userInfoVO.getPhoneNumber());
  }

  @PostMapping("/{id}/userInfo/registerPersonalVIP")
  public ResponseVO registerPersonalVIP(@RequestBody VipPersonVO vipPersonVO,
      @PathVariable int id) {
    vipPersonVO.setUserId(id);
    return accountService.registerPersonalVIP(vipPersonVO);
  }

  @PostMapping("/{id}/userInfo/registerCompanyVIP")
  public ResponseVO registerCompanyVIP(@RequestBody VipCompanyVO vipCompanyVO,
      @PathVariable int id) {
    vipCompanyVO.setUserId(id);
    return accountService.registerCompanyVIP(vipCompanyVO);
  }

  @PostMapping("/{email}/{credit}/chargeCredit")
  public ResponseVO chargeCredit(@PathVariable String email, @PathVariable double credit) {
    return accountService.updateUserCreditByEmail(email, credit);
  }

}
