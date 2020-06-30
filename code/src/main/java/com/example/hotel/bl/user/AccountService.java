package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.*;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    /**
     * 获取会员信息
     * @param id
     * @return
     */
    Vip getVipInfo(int id);

    /**
     * 更新用户个人信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id, String password,String username,String phonenumber);

    /**
     * 更改用户的信用值
     * @param id
     * @param credit 此处的credit需要改变信用值，增加为正数，减少为负数
     * @return
     */
    ResponseVO updateUserCredit(int id, double credit);

    /**
     * 根据电子邮箱修改用户信用值
     * @param email
     * @param credit
     * @return
     */
    ResponseVO updateUserCreditByEmail(String email, double credit);

    /**
     *
     * @param vipPersonVO
     * @return
     */
    ResponseVO registerPersonalVIP(VipPersonVO vipPersonVO);

    /**
     *
     * @param vipCompanyVO
     * @return
     */
    ResponseVO registerCompanyVIP(VipCompanyVO vipCompanyVO);

    /**
     *
     * @param email
     * @return
     */
    boolean checkEmail(String email);

    /**
     *
     * @param phoneNumber
     * @return
     */
    boolean checkPhone(String phoneNumber);
}
