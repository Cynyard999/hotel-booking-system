package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.UserVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminServiceImplTest {
    @InjectMocks
    private AdminService adminService = new AdminServiceImpl();
    @Mock
    private AccountService accountService;
    @Mock
    private AdminMapper adminMapper;

    @Before
    public void setUp(){
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setEmail("123@qq.com");
        user.setPhoneNumber("123456");
        user.setPassword("123456");
        user.setUserType(UserType.Client);
        list.add(user);
        Mockito.when(adminMapper.getUserList("Client")).thenReturn(list);
        Mockito.when(accountService.checkEmail("123@qq.com")).thenReturn(true);
        Mockito.when(accountService.checkPhone("123456")).thenReturn(true);

    }

    @Test
    public void addUser() {
        Assert.assertTrue(adminService.addUser(new UserVO()).getSuccess());
    }

    @Test
    public void getUserList() {
        List<UserVO> list = adminService.getUserList("Client");
        UserVO user1 = list.get(0);
        Assert.assertEquals("123@qq.com",user1.getEmail());
    }

    @Test
    public void updateUserInfo() {
        Assert.assertTrue(adminService.updateUserInfo(new UserVO()).getSuccess());
    }

    @Test
    public void addHotel() {
        HotelVO hotel = new HotelVO();
        hotel.setHotelName("test");
        hotel.setBizRegion("HeXi");
        hotel.setHotelStar("Five");
        Assert.assertTrue(adminService.addHotel(hotel).getSuccess());
    }

    @Test
    public void updateHotel() {
        Assert.assertTrue(adminService.updateHotel(new HotelVO()).getSuccess());
    }

    @Test
    public void getUserInfo() {
        User user = new User();
        user.setId(5);
        user.setEmail("123@qq.com");
        Mockito.when(adminMapper.getUserInfo(0)).thenReturn(user);
        UserVO result = (UserVO) adminService.getUserInfo(0).getContent();
        Assert.assertEquals("123@qq.com",result.getEmail());
    }

    @Test
    public void deleteHotel(){
        Hotel hotel0 = new Hotel();
        Hotel hotel1 = new Hotel();
        hotel0.setManagerId(0);
        Mockito.when(adminMapper.getHotelInfo(0)).thenReturn(hotel0);
        Mockito.when(adminMapper.getHotelInfo(1)).thenReturn(hotel1);
        Assert.assertTrue(adminService.deleteHotel(0).getSuccess());
        Assert.assertTrue(adminService.deleteHotel(1).getSuccess());
    }

    @Test
    public void deleteUser(){
        Assert.assertTrue(adminService.deleteUser(0).getSuccess());
    }

}