package com.example.hotel.controller.user;

import com.example.hotel.po.Vip;
import com.example.hotel.vo.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Zzn
 * @Date: 2020-06-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    private final static String CHARGE_ERROR = "此类型用户无法充值信用值";
    private final static String EMAIL_EXIST = "邮箱已被注册";
    private final static String PHONE_EXIST = "手机号已被注册";

    @Autowired
    private AccountController accountController;

    @Test
    @Transactional
    @Rollback
    public void login() {
        UserForm userForm = new UserForm();
        userForm.setEmail("user1@qq.com");
        userForm.setPassword("wrong");
        Assert.assertEquals(ACCOUNT_INFO_ERROR,accountController.login(userForm).getMessage());
        userForm.setPassword("123456");
        Assert.assertEquals("测试用户1号",((UserVO)accountController.login(userForm).getContent()).getUserName());
    }

    @Test
    @Transactional
    @Rollback
    public void registerAccount() {
        UserVO userVO = new UserVO();
        userVO.setUserName("测试账号");
        userVO.setPassword("");
        //邮箱已注册
        userVO.setEmail("user1@qq.com");
        Assert.assertEquals(EMAIL_EXIST,accountController.registerAccount(userVO).getContent());
        userVO.setEmail("test@qq.com");
        //手机号已注册
        userVO.setPhoneNumber("12345678911");
        Assert.assertEquals(PHONE_EXIST,accountController.registerAccount(userVO).getContent());
        userVO.setPhoneNumber("7777");
        //正常注册
        Assert.assertTrue(accountController.registerAccount(userVO).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void getUserInfo() {
        Assert.assertEquals(ACCOUNT_INFO_ERROR,accountController.getUserInfo(0).getMessage());
        Assert.assertEquals("管理员",((UserVO)accountController.getUserInfo(1).getContent()).getUserName());
    }

    @Test
    @Transactional
    @Rollback
    public void getVipInfo() {
        Assert.assertEquals((Integer) 0,((Vip)accountController.getVipInfo(1).getContent()).getVipType());
        VipPersonVO vipPersonVO = new VipPersonVO();
        vipPersonVO.setBirthday("");
        accountController.registerPersonalVIP(vipPersonVO,1);
        Assert.assertEquals((Integer) 1,((Vip)accountController.getVipInfo(1).getContent()).getVipType());
    }

    @Test
    @Transactional
    @Rollback
    public void updateInfo() {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setPhoneNumber("");
        userInfoVO.setUserName("测试号");
        userInfoVO.setPassword("");
        Assert.assertTrue(accountController.updateInfo(userInfoVO,1).getSuccess());
        Assert.assertEquals("测试号",((UserVO)accountController.getUserInfo(1).getContent()).getUserName());
    }

    @Test
    @Transactional
    @Rollback
    public void registerPersonalVIP() {
        VipPersonVO vipPersonVO = new VipPersonVO();
        vipPersonVO.setBirthday("");
        Assert.assertTrue(accountController.registerPersonalVIP(vipPersonVO,1).getSuccess());
        Assert.assertEquals((Integer) 1,((Vip)accountController.getVipInfo(1).getContent()).getVipType());
    }

    @Test
    @Transactional
    @Rollback
    public void registerCompanyVIP() {
        VipCompanyVO vipCompanyVO = new VipCompanyVO();
        vipCompanyVO.setCompanyName("");
        Assert.assertTrue(accountController.registerCompanyVIP(vipCompanyVO,1).getSuccess());
        Assert.assertEquals((Integer) 2,((Vip)accountController.getVipInfo(1).getContent()).getVipType());
    }

    @Test
    @Transactional
    @Rollback
    public void chargeCredit() {
        Assert.assertEquals(CHARGE_ERROR,accountController.chargeCredit("admin@qq.com",100).getMessage());
        Assert.assertTrue(accountController.chargeCredit("user1@qq.com",100).getSuccess());
    }
}
