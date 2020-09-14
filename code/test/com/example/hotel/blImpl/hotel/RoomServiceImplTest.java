package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
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
public class RoomServiceImplTest {
    @InjectMocks
    RoomService roomService = new RoomServiceImpl();
    @Mock
    RoomMapper roomMapper;

    @Before
    public void setUp(){
        HotelRoom hotelRoom1 = new HotelRoom();
        hotelRoom1.setHotelId(1);
        hotelRoom1.setId(1);
        hotelRoom1.setCurNum(10);
        hotelRoom1.setPrice(799);
        hotelRoom1.setRoomType(RoomType.valueOf("Family"));
        hotelRoom1.setTotal(10);
        List<HotelRoom> rooms = new ArrayList<>();
        rooms.add(hotelRoom1);
        Mockito.when(roomMapper.selectRoomsByHotelId(1)).thenReturn(rooms);
        Mockito.when(roomMapper.getRoomCurNum(1,1)).thenReturn(10);
    }
    @Test
    public void retrieveHotelRoomInfo() {
        List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfo(1);
        Assert.assertEquals(10,hotelRooms.get(0).getCurNum());
    }

    @Test
    public void insertRoomInfo() {
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setTotal(10);
        hotelRoom.setPrice(199);
        hotelRoom.setCurNum(10);
        hotelRoom.setRoomType(RoomType.valueOf("DoubleBed"));
        hotelRoom.setHotelId(1);
        roomService.insertRoomInfo(hotelRoom);
        Assert.assertTrue(true);
    }

    @Test
    public void updateRoomInfo() {
        roomService.updateRoomInfo(1,"Family",10);
        Assert.assertTrue(true);
    }

    @Test
    public void getRoomCurNum() {
        Assert.assertEquals(10,roomService.getRoomCurNum(1,1));
    }
}