package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.*;

/**
 * @Author: Qxy
 * @Date: 2020-05-25
 */
public interface AccountService {

    /**
     * 注册账号
     * @Param userVO 用户信息
     * @return 返回注册结果，成功返回true，失败返回失败信息
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     * @Param userForm 用户表单
     * @return 返回登录结果
     */
    UserVO login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id 用户id
     * @return 返回用户个人信息
     */
    UserVO getUserInfo(int id);

    /**
     * 获取会员信息
     * @param id 用户id
     * @return 返回获取的用户会员信息
     */
    Vip getVipInfo(int id);

    /**
     * 更新用户个人信息
     * @param id 用户id
     * @param password  密码
     * @param username 用户名
     * @param phonenumber 用户电话
     * @return 返回更新结果，成功返回true，失败返回失败信息
     */
    ResponseVO updateUserInfo(int id, String password,String username,String phonenumber);

    /**
     * 更改用户的信用值
     * @param id 用户id
     * @param credit 此处的credit需要改变信用值，增加为正数，减少为负数
     * @return 返回更新用户信用值结果，成功返回true，失败返回失败信息
     */
    ResponseVO updateUserCredit(int id, double credit);

    /**
     * 根据电子邮箱修改用户信用值
     * @param email 用户邮箱
     * @param credit 此处的credit需要改变信用值，增加为正数，减少为负数
     * @return 返回更改信用值结果，成功返回true，失败返回失败信息
     */
    ResponseVO updateUserCreditByEmail(String email, double credit);

    /**
     * 注册个人会员
     * @param vipPersonVO 个人会员注册信息
     * @return 返回个人会员注册结果，成功返回true，失败返回失败信息
     */
    ResponseVO registerPersonalVIP(VipPersonVO vipPersonVO);

    /**
     * 注册企业会员
     * @param vipCompanyVO 企业会员注册信息
     * @return 返回企业会员注册结果，成功返回true，失败返回失败信息
     */
    ResponseVO registerCompanyVIP(VipCompanyVO vipCompanyVO);

    /**
     * 检查邮箱是否已注册
     * @param email 用户邮箱
     * @return  已注册返回true，未注册返回false
     */
    boolean checkEmail(String email);

    /**
     * 检查电话是否已注册
     * @param phoneNumber 用户电话
     * @return 已注册返回true，未注册返回false
     */
    boolean checkPhone(String phoneNumber);
}
