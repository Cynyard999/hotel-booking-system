package com.example.hotel.controller.admin;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.UserVO;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private AdminController adminController;
    @Autowired
    private HotelService hotelService;

    @Test
    @Transactional
    @Rollback
    public void addUser() {
        UserVO user = new UserVO();
        user.setEmail("user1@qq.com");
        user.setPhoneNumber("12345678911");
        user.setPassword("123456");
        user.setUserType(UserType.Client);
        user.setUserName("test");
        Assert.assertEquals("邮箱已被注册",adminController.addUser(user).getMessage());
        user.setEmail("user4@qq.com");
        Assert.assertEquals("手机号已被注册",adminController.addUser(user).getMessage());
        user.setPhoneNumber("13382405535");
        Assert.assertTrue(adminController.addUser(user).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void getUserList() {
        Assert.assertEquals(4, ((List<UserVO>) adminController.getUserList("HotelManager").getContent()).size());
        Assert.assertEquals(3,((List<UserVO>) adminController.getUserList("Client").getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void updateUserInfo() {
        UserVO user = new UserVO();
        user.setId(9);
        user.setEmail("test@qq.com");
        user.setUserName("testUser");
        user.setPhoneNumber("13382405535");
        user.setPassword("1234567");
        Assert.assertTrue(adminController.updateUserInfo(user).getSuccess());
        UserVO result = (UserVO)adminController.getUserInfo(9).getContent();
        Assert.assertEquals("test@qq.com",result.getEmail());
        Assert.assertEquals("testUser",result.getUserName());
        Assert.assertEquals("13382405535",result.getPhoneNumber());
    }

    @Test
    @Transactional
    @Rollback
    public void addHotel() {
        HotelVO hotel = new HotelVO();
        hotel.setBizRegion("HeXi");
        hotel.setHotelStar("Four");
        hotel.setHotelName("test");
        Assert.assertTrue(adminController.addHotel(hotel).getSuccess());
        Assert.assertEquals(5,hotelService.retrieveHotels().size());
    }

    @Test
    @Transactional
    @Rollback
    public void updateHotel() {
        HotelVO hotelVO = new HotelVO();
        hotelVO.setId(1);
        hotelVO.setManagerId(1);
        Assert.assertTrue(adminController.updateHotel(hotelVO).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteHotel() {
        Assert.assertTrue(adminController.deleteHotel(1).getSuccess());
        Assert.assertEquals(3,hotelService.retrieveHotels().size());
        HotelVO hotel = new HotelVO();
        hotel.setBizRegion("HeXi");
        hotel.setHotelStar("Four");
        hotel.setHotelName("test");
        adminController.addHotel(hotel);
        Assert.assertTrue(adminController.deleteHotel(4).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUser() {
        Assert.assertTrue(adminController.deleteUser(9).getSuccess());
        Assert.assertEquals(2,((List<UserVO>) adminController.getUserList("Client").getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void getUserInfo() {
        Assert.assertEquals("查询失败",adminController.getUserInfo(15).getMessage());
        UserVO user = (UserVO)adminController.getUserInfo(9).getContent();
        Assert.assertEquals("测试用户1号",user.getUserName());
    }
}