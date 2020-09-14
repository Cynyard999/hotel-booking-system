package com.example.hotel.data.user;

import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class AccountMapperTest {
  User user;
  @Autowired
  AccountMapper accountMapper;

  @Before
  public void init(){
    user = new User();
    user.setEmail("111@qq.com");
    user.setUserType(UserType.Client);
    user.setPassword("123456");
    user.setUserName("Test1");
    user.setPhoneNumber("111");
    user.setCredit(100);
  }


  @Test
  public void createNewAccount() {
    accountMapper.createNewAccount(user);
    assertEquals(user.getPhoneNumber(),accountMapper.getAccountByName(user.getEmail()).getPhoneNumber());
  }

  @Test
  public void getAccountByName() {
    accountMapper.createNewAccount(user);
    assertEquals(user.getId(),accountMapper.getAccountByName(user.getEmail()).getId());
  }

  @Test
  public void getAccountById() {
    accountMapper.createNewAccount(user);
    User res = accountMapper.getAccountByName("111@qq.com");
    assertEquals(user.getEmail(),accountMapper.getAccountById(res.getId()).getEmail());
  }

  @Test
  public void updateAccount() {
    accountMapper.createNewAccount(user);
    User res = accountMapper.getAccountByName("111@qq.com");
    accountMapper.updateAccount(res.getId(),"123456","Test1","222");
    assertEquals("222",accountMapper.getAccountById(res.getId()).getPhoneNumber());
  }

  @Test
  public void updateCredit() {
    accountMapper.createNewAccount(user);
    User res =accountMapper.getAccountByName("111@qq.com");
    accountMapper.updateCredit(res.getId(),200);
    assertEquals(300,accountMapper.getAccountByName("111@qq.com").getCredit(),0.0);
  }

  @Test
  public void registerPersonalVIP() {
    Vip vip = new Vip();
    vip.setVipType(1);
    vip.setUserId(1);
    vip.setBirthday("2020-01-01");
    accountMapper.registerPersonalVIP(vip);
    assertEquals("2020-01-01",accountMapper.getVipInfoById(1).getBirthday());
  }

  @Test
  public void registerCompanyVIP() {
    Vip vip = new Vip();
    vip.setVipType(2);
    vip.setUserId(1);
    vip.setCompanyName("NJU");
    accountMapper.registerCompanyVIP(vip);
    assertEquals("NJU",accountMapper.getVipInfoById(1).getCompanyName());
  }

  @Test
  public void getVipInfoById() {
    Vip vip = new Vip();
    vip.setVipType(1);
    vip.setUserId(1);
    vip.setBirthday("2020-01-01");
    accountMapper.registerPersonalVIP(vip);
    assertEquals("2020-01-01",accountMapper.getVipInfoById(1).getBirthday());
  }

  @Test
  public void checkEmail() {
    accountMapper.createNewAccount(user);
    assertTrue(accountMapper.checkEmail("111@qq.com"));
  }

  @Test
  public void checkPhone() {
    accountMapper.createNewAccount(user);
    assertTrue(accountMapper.checkPhone("111"));
  }
}