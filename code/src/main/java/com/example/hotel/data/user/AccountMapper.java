package com.example.hotel.data.user;

import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface AccountMapper {

  /**
   * 创建一个新的账号
   *
   * @return
   */
  int createNewAccount(User user);

  /**
   * 根据用户名查找账号
   *
   * @param email
   * @return
   */
  User getAccountByName(@Param("email") String email);

  User getAccountById(@Param("id") int id);


  /**
   * 更新用户信息
   *
   * @param id
   * @param password
   * @param username
   * @param phonenumber
   * @return
   */
  void updateAccount(@Param("id") int id, @Param("password") String password,
      @Param("userName") String username, @Param("phoneNumber") String phonenumber);

  /**
   * 更新用户信用值
   *
   * @param id
   * @param credit
   */
  void updateCredit(@Param("id") int id, @Param("credit") double credit);

  /**
   * @param vip
   * @return
   */
  void registerPersonalVIP(Vip vip);

  /**
   * @param vip
   * @return
   */
  void registerCompanyVIP(Vip vip);

  Vip getVipInfoById(@Param("id") int id);

  /**
   * 检查邮箱是否已经存在
   * @param email
   * @return
   */
  boolean checkEmail(@Param("email") String email);

  /**
   * 检查手机号是否已经存在
   * @param phoneNumber
   * @return
   */
  boolean checkPhone(@Param("phoneNumber") String phoneNumber);

}
