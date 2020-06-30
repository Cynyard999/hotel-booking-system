package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api/user")
public class AccountController {

  private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
  @Autowired
  private AccountService accountService;

  @PostMapping("/login")
  public ResponseVO login(@RequestBody UserForm userForm) {
    User user = accountService.login(userForm);
    if (user == null) {
      return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
    }
    return ResponseVO.buildSuccess(user);

  }

  @PostMapping("/register")
  public ResponseVO registerAccount(@RequestBody UserVO userVO) {
    return accountService.registerAccount(userVO);
  }


  @GetMapping("/{id}/getUserInfo")
  public ResponseVO getUserInfo(@PathVariable int id) {
    User user = accountService.getUserInfo(id);
    if (user == null) {
      return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
    }
    return ResponseVO.buildSuccess(user);
  }

  //`${api.userPre}/${id}/getVipInfo`
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
    System.out.print(email);
    return accountService.updateUserCreditByEmail(email, credit);
  }

}
