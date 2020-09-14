package com.example.hotel.data.hotel;

import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
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
public class RoomMapperTest {
    @Autowired
    private RoomMapper roomMapper;

    @Test
    @Transactional
    @Rollback
    public void updateRoomInfo(){
        roomMapper.updateRoomInfo(1,"Family",10);
        Assert.assertTrue(true);
    }
    @Test
    @Transactional
    @Rollback
    public void insertRoom(){
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setTotal(10);
        hotelRoom.setPrice(199);
        hotelRoom.setCurNum(10);
        hotelRoom.setRoomType(RoomType.valueOf("DoubleBed"));
        hotelRoom.setHotelId(1);
        roomMapper.insertRoom(hotelRoom);
        Assert.assertTrue(true);
    }
    @Test
    @Transactional
    @Rollback

    public void selectRoomsByHotelId(){
        Assert.assertEquals(2,roomMapper.selectRoomsByHotelId(2).size());
    }
    @Test
    @Transactional
    @Rollback
    public void getRoomCurNum(){
    Assert.assertEquals(10,roomMapper.getRoomCurNum(1,1));
    }

}