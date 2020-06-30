package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.creditRecord.CreditRecordMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountServiceImplTest extends TestCase {

  private final static String UPDATE_ERROR = "修改失败";
  private final static String REGISTER_ERROR = "注册失败";
  private final static String CHARGE_ERROR = "此类型用户无法充值信用值";
  private final static String EMAIL_EXIST = "邮箱已被注册";
  private final static String PHONE_EXIST = "手机号已被注册";
  private final static String ACCOUNT_EXIST = "账号已存在";
  @InjectMocks
  private AccountService accountService = new AccountServiceImpl();

  @Mock
  private AccountMapper accountMapper;
  @Mock
  private CreditRecordMapper creditRecordMapper;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(accountMapper.checkEmail("111@qq.com")).thenReturn(true);
    when(accountMapper.checkPhone("222")).thenReturn(true);
  }

  @Test
  public void testRegisterAccount() {
    UserVO userVO1 = new UserVO();
    UserVO userVO2 = new UserVO();
    userVO1.setId(1);
    userVO1.setEmail("111@qq.com");
    userVO1.setPhoneNumber("111");
    userVO2.setId(2);
    userVO2.setEmail("222@qq.com");
    userVO2.setPhoneNumber("222");
    ResponseVO responseVO1 = ResponseVO.buildSuccess(EMAIL_EXIST);
    ResponseVO responseVO2 = ResponseVO.buildSuccess(PHONE_EXIST);
    ResponseVO responseVO3 = ResponseVO.buildFailure(ACCOUNT_EXIST);
    ResponseVO responseVO = ResponseVO.buildSuccess(123);

    //测试email已存在
    assertEquals(responseVO1.getContent(), accountService.registerAccount(userVO1).getContent());

    //测试电话已存在
    assertEquals(responseVO2.getContent(), accountService.registerAccount(userVO2).getContent());

    //测试账号已存在 使用参数匹配，满足id为3的user均throw报错
    UserVO userVO3 = new UserVO();
    userVO3.setPhoneNumber("333");
    userVO3.setId(3);
    userVO3.setEmail("333@qq.com");
    //when(accountMapper.createNewAccount(user)).thenThrow(new MockitoException("报错")); 不能直接传入user对象，因为在service中传入的user对象和这里的user对象不同
    when(accountMapper.createNewAccount(argThat(user1 -> user1.getId().equals(3))))
        .thenThrow(new MockitoException("mock模拟报错"));
    assertEquals(responseVO3.getMessage(), accountService.registerAccount(userVO3).getMessage());

    //测试注册成功
    UserVO userVO4 = new UserVO();
    userVO4.setPhoneNumber("444");
    userVO4.setId(4);
    userVO4.setEmail("444@qq.com");
    assertEquals(responseVO.getContent(), accountService.registerAccount(userVO4).getContent());

  }

  @Test
  public void testLogin() {
    User user = new User();
    user.setId(1);
    user.setEmail("111@qq.com");
    user.setPassword("123456");
    when(accountMapper.getAccountByName("111@qq.com")).thenReturn(user);
    when(accountMapper.getAccountByName("222@qq.com")).thenReturn(null);
    UserForm userForm = new UserForm();
    userForm.setEmail("111@qq.com");
    userForm.setPassword("123456");
    assertTrue(accountService.login(userForm).getId() == 1);
    userForm.setEmail("222@qq.com");
    assertNull(accountService.login(userForm));
  }

  @Test
  public void testGetUserInfo() {
    User user = new User();
    user.setId(1);
    user.setEmail("111@qq.com");
    user.setPassword("123456");
    when(accountMapper.getAccountById(1)).thenReturn(user);
    when(accountMapper.getAccountById(2)).thenReturn(null);
    assertTrue(accountService.getUserInfo(1).getEmail().equals("111@qq.com"));
    assertNull(accountService.getUserInfo(2));
  }

  @Test
  public void testGetVipInfo() {
    Vip vip = new Vip();
    vip.setVipType(1);
    when(accountMapper.getVipInfoById(1)).thenReturn(vip);
    assertEquals(1, (int) accountService.getVipInfo(1).getVipType());
    vip.setVipType(0);
    when(accountMapper.getVipInfoById(2)).thenReturn(null);
    assertEquals(0, (int) accountService.getVipInfo(2).getVipType());
  }

  @Test
  public void testUpdateUserInfo() {
    String password = "123456";
    String userName = "cynyard";
    String phoneNum = "111";
    //修改成功
    ResponseVO res = ResponseVO.buildSuccess(true);
    doNothing().when(accountMapper).updateAccount(1, password, userName, phoneNum);
    assertEquals(res.getContent(),
        accountService.updateUserInfo(1, password, userName, phoneNum).getContent());
    verify(accountMapper, times(1)).updateAccount(1, password, userName, phoneNum);
    //修改失败
    res = ResponseVO.buildFailure(UPDATE_ERROR);
    doThrow(new MockitoException("Mock 模拟报错")).when(accountMapper)
        .updateAccount(2, password, userName, phoneNum);
    assertEquals(res.getMessage(),
        accountService.updateUserInfo(2, password, userName, phoneNum).getMessage());
  }

  @Test
  public void testUpdateUserCredit() {
    ResponseVO res = ResponseVO.buildSuccess(true);
    doNothing().when(accountMapper).updateCredit(1, 100);
    assertEquals(res.getContent(), accountService.updateUserCredit(1, 100).getContent());
    res = ResponseVO.buildFailure(CHARGE_ERROR);
    doThrow(new MockitoException("Mock模拟报错")).when(accountMapper).updateCredit(2, 100);
    assertEquals(res.getMessage(), accountService.updateUserCredit(2, 100).getMessage());
  }

  @Test
  public void testUpdateUserCreditByEmail() {
    User user = new User();
    user.setEmail("111@qq.com");
    user.setId(1);
    user.setUserType(UserType.Client);
    ResponseVO res = ResponseVO.buildSuccess(true);
    when(accountMapper.getAccountByName("111@qq.com")).thenReturn(user);
    doNothing().when(creditRecordMapper).addCreditRecord(any());
    assertEquals(res.getContent(),
        accountService.updateUserCreditByEmail("111@qq.com", 100).getContent());
    res = ResponseVO.buildFailure(CHARGE_ERROR);
    user.setEmail("222@qq.com");
    user.setUserType(UserType.HotelManager);
    when(accountMapper.getAccountByName("222@qq.com")).thenReturn(user);
    assertEquals(res.getMessage(),
        accountService.updateUserCreditByEmail("222@qq.com", 100).getMessage());
  }


  @Test
  public void testRegisterPersonalVIP() {
    VipPersonVO vipPersonVO = new VipPersonVO();
    vipPersonVO.setBirthday("2020-01-01");
    vipPersonVO.setVipType(1);
    vipPersonVO.setUserId(1);
    ResponseVO res = ResponseVO.buildSuccess(true);
    doNothing().when(accountMapper).registerPersonalVIP(any());
    assertEquals(res.getContent(), accountService.registerPersonalVIP(vipPersonVO).getContent());

    doThrow(new MockitoException("Mock模拟报错")).when(accountMapper).registerPersonalVIP(argThat(
        new ArgumentMatcher<Vip>() {
          @Override
          public boolean matches(Vip vip) {
            return vip.getUserId() == 2;
          }
        }));
    res = ResponseVO.buildFailure(REGISTER_ERROR);
    vipPersonVO.setUserId(2);
    assertEquals(res.getMessage(), accountService.registerPersonalVIP(vipPersonVO).getMessage());
  }

  @Test
  public void testRegisterCompanyVIP() {
    VipCompanyVO vipCompanyVO = new VipCompanyVO();
    vipCompanyVO.setCompanyName("南京大学");
    vipCompanyVO.setVipType(2);
    vipCompanyVO.setUserId(1);
    ResponseVO res = ResponseVO.buildSuccess(true);
    doNothing().when(accountMapper).registerCompanyVIP(any());
    assertEquals(res.getContent(), accountService.registerCompanyVIP(vipCompanyVO).getContent());

    doThrow(new MockitoException("Mock模拟报错")).when(accountMapper).registerCompanyVIP(argThat(
        new ArgumentMatcher<Vip>() {
          @Override
          public boolean matches(Vip vip) {
            return vip.getUserId() == 2;
          }
        }));
    res = ResponseVO.buildFailure(REGISTER_ERROR);
    vipCompanyVO.setUserId(2);
    assertEquals(res.getMessage(), accountService.registerCompanyVIP(vipCompanyVO).getMessage());
  }
}