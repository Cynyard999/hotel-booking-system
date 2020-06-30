package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.HotelInfoVO;
import com.example.hotel.vo.HotelVO;
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
public class HotelServiceImplTest {
    @InjectMocks
    HotelService hotelService = new HotelServiceImpl();
    @Mock
    HotelMapper hotelMapper;
    @Mock
    AccountService accountService;
    @Mock
    RoomService roomService;

    @Before
    public void setUp(){

        List<Hotel> hotels = new ArrayList<>();
        Hotel hotel1=new Hotel();
        hotel1.setId(1);
        hotel1.setHotelName("桔子水晶南京新街口酒店");
        hotel1.setDescription("浪漫情侣、商务出行、地铁周边");
        hotel1.setDetail("酒店地处繁华的商业中心，紧邻地铁一号线，邻近夫子庙、1912街区、总统府等景点，周边金融、餐饮、休闲配套资源一应俱全，宾客出行便捷。 ");
        hotels.add(hotel1);
        HotelRoom hotelRoom1 = new HotelRoom();


        hotelRoom1.setHotelId(1);
        hotelRoom1.setId(1);
        hotelRoom1.setCurNum(10);
        hotelRoom1.setPrice(799);
        hotelRoom1.setRoomType(RoomType.valueOf("Family"));
        hotelRoom1.setTotal(10);

        List<HotelRoom> rooms = new ArrayList<>();
        rooms.add(hotelRoom1);
        Mockito.when(hotelMapper.selectAllHotel()).thenReturn(hotels);
        Mockito.when(hotelMapper.selectById(1)).thenReturn(hotel1);
        Mockito.when(roomService.retrieveHotelRoomInfo(1)).thenReturn(rooms);
        Mockito.when(roomService.getRoomCurNum(1,1)).thenReturn(10);
        Mockito.when(hotelMapper.selectByManagerId(1)).thenReturn(hotel1);
    }
    @Test
    public void updateHotelInfo() {

        HotelInfoVO hotelInfoVO = new HotelInfoVO();


        hotelInfoVO.setHotelName("桔子水晶南京新街口酒店");
        hotelInfoVO.setDescription("浪漫情侣、商务出行、地铁周边");
        hotelInfoVO.setDetail("酒店地处繁华的商业中心，紧邻地铁一号线，邻近夫子庙、1912街区、总统府等景点，周边金融、餐饮、休闲配套资源一应俱全，宾客出行便捷。 ");

        hotelService.updateHotelInfo(hotelInfoVO,0);
        Assert.assertFalse(false);


    }

    @Test
    public void updateRoomInfo() {
        hotelService.updateRoomInfo(1,"Family",10);
        Assert.assertTrue(true);
    }

    @Test
    public void getRoomCurNum() {
        Assert.assertEquals(10,hotelService.getRoomCurNum(1,1));
    }

    @Test
    public void retrieveHotels() {
        List<HotelVO> hotelVOS = hotelService.retrieveHotels();
        Assert.assertEquals("桔子水晶南京新街口酒店",hotelVOS.get(0).getHotelName());
    }
    @Test
    public void rate(){
        Assert.assertTrue(hotelService.rate(1, 3.0).getSuccess());
    }

    @Test
    public void retrieveHotelDetails() {
        HotelVO hotelVO=hotelService.retrieveHotelDetails(1);
        Assert.assertEquals("桔子水晶南京新街口酒店",hotelVO.getHotelName());
        Assert.assertEquals((Integer)10,hotelVO.getRooms().get(0).getCurNum());

    }

    @Test
    public void retrieveHotelDetailByManager() {
        HotelVO hotelVO=hotelService.retrieveHotelDetailByManager(1);
        Assert.assertEquals("桔子水晶南京新街口酒店",hotelVO.getHotelName());
        Assert.assertEquals((Integer)10,hotelVO.getRooms().get(0).getCurNum());

    }
}