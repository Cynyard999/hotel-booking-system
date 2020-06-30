package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HotelMapperTest {
    @Autowired
    private HotelMapper hotelMapper;

    @Test
    @Transactional
    @Rollback
    public void updateHotel(){
        Hotel hotel = new Hotel();

        hotel.setId(1);
        hotel.setHotelName("测试");
        hotel.setAddress("测试");
        hotel.setBizRegion("测试");
        hotel.setHotelStar("测试");
        hotel.setDescription("测试");
        hotel.setDetail("测试");
        hotel.setPhoneNum("测试");
        int result = hotelMapper.updateHotel(hotel);
        Assert.assertEquals(1,result);
        Assert.assertEquals("测试",hotelMapper.selectById(1).getHotelName());


    }
    @Test
    @Transactional
    @Rollback
    public void selectAllHotel(){
        Assert.assertEquals(4,hotelMapper.selectAllHotel().size());
    }
    @Test
    @Transactional
    @Rollback
    public void selectById(){
        Hotel hotel = new Hotel();
        hotel.setHotelName("桔子水晶南京新街口酒店");
        Assert.assertEquals(hotel.getHotelName(),hotelMapper.selectById(1).getHotelName());
    }
    @Test
    @Transactional
    @Rollback
    public void selectByManagerId(){
        Hotel hotel = new Hotel();
        hotel.setHotelName("桔子水晶南京新街口酒店");
        Assert.assertEquals(hotel.getHotelName(),hotelMapper.selectByManagerId(5).getHotelName());
    }
    @Test
    @Transactional
    @Rollback
    public void rate(){
        hotelMapper.rate(1,3.0);
        Assert.assertTrue(true);
    }
}