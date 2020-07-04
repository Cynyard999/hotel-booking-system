package com.example.hotel.controller.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.HotelInfoVO;
import com.example.hotel.vo.HotelVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zzn
 * @Date: 2020-06-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelControllerTest {
    @Autowired
    private HotelController hotelController;

    @Test
    @Transactional
    @Rollback
    public void retrieveAllHotels(){
        List<HotelVO> hotels = (List<HotelVO>)hotelController.retrieveAllHotels().getContent();
        Assert.assertEquals(4,hotels.size());
    }

    @Test
    @Transactional
    @Rollback
    public void addRoomInfo() {
        HotelRoom hotelRoom = new HotelRoom();
        Assert.assertTrue(hotelController.addRoomInfo(hotelRoom).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveHotelDetail() {
        Assert.assertEquals("桔子水晶南京新街口酒店",((HotelVO)hotelController.retrieveHotelDetail(1).getContent()).getHotelName());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveHotelDetailByManager() {
        Assert.assertEquals("桔子水晶南京新街口酒店",((HotelVO)hotelController.retrieveHotelDetailByManager(5).getContent()).getHotelName());
    }

    @Test
    @Transactional
    @Rollback
    public void updateHotelInfo(){
        HotelVO hotelVO = (HotelVO)hotelController.retrieveHotelDetail(1).getContent();
        HotelInfoVO hotelInfoVO = new HotelInfoVO();
        hotelInfoVO.setHotelName("testHotel");
        hotelInfoVO.setHotelStar(hotelVO.getHotelStar());
        hotelInfoVO.setBizRegion(hotelVO.getBizRegion());
        hotelInfoVO.setDescription(hotelVO.getDescription());
        hotelInfoVO.setDetail(hotelVO.getDetail());
        hotelInfoVO.setAddress(hotelVO.getAddress());
        hotelInfoVO.setPhoneNum(hotelVO.getPhoneNum());
        Assert.assertTrue(hotelController.updateHotelInfo(hotelInfoVO,1).getSuccess());
        Assert.assertEquals("testHotel",((HotelVO)hotelController.retrieveHotelDetail(1).getContent()).getHotelName());
    }

    @Test
    @Transactional
    @Rollback
    public void rate(){
        Map<Object,Double> map = new HashMap<>();
        map.put("rate",1.0);
        Assert.assertTrue(hotelController.rate(1,map).getSuccess());
        Assert.assertEquals((Double) 2.5,((HotelVO)hotelController.retrieveHotelDetail(1).getContent()).getRate());
    }



}
