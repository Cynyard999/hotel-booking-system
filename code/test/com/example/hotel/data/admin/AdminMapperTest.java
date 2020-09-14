package com.example.hotel.data.admin;

import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;

    @Test
    @Transactional
    @Rollback
    public void addUser() {
        User user = new User();
        user.setEmail("123@qq.com");
        user.setPassword("123456");
        user.setUserType(UserType.Client);
        adminMapper.addUser(user);
        Assert.assertTrue(true);
    }

    @Test
    @Transactional
    @Rollback
    public void getUserList() {
        Assert.assertEquals(1,adminMapper.getUserList("Manager").size());
        Assert.assertEquals(3,adminMapper.getUserList("SaleMen").size());
        Assert.assertEquals(4,adminMapper.getUserList("HotelManager").size());
        Assert.assertEquals(3,adminMapper.getUserList("Client").size());
    }

    @Test
    @Transactional
    @Rollback
    public void updateUserInfo() {
        User temp = new User();
        temp.setId(10);
        temp.setEmail("123456");
        temp.setPassword("123456");
        adminMapper.updateUserInfo(temp);
    }

    @Test
    @Transactional
    @Rollback
    public void addHotel() {
        Hotel hotel = new Hotel();
        hotel.setHotelName("testHotel");
        hotel.setBizRegion("FuZiMiao");
        hotel.setHotelStar("Five");
        adminMapper.addHotel(hotel);
    }

    @Test
    @Transactional
    @Rollback
    public void updateHotel() {
        Hotel hotel = new Hotel();
        hotel.setId(2);
        hotel.setManagerId(0);
        adminMapper.updateHotel(hotel);
    }

    @Test
    @Transactional
    @Rollback
    public void getUserInfo() {
        Assert.assertEquals("测试用户1号",adminMapper.getUserInfo(9).getUserName());
    }

    @Test
    @Transactional
    @Rollback
    public void getHotelInfo(){
        Assert.assertEquals("乐优居精品公寓",adminMapper.getHotelInfo(2).getHotelName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteHotel(){
        adminMapper.deleteHotel(2);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUser(){
        adminMapper.deleteUser(1);
    }
}