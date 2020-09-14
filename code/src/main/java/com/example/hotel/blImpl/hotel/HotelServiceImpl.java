package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.HotelInfoVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final static String UPDATE_ERROR = "修改失败";
    private final static String RATE_ERROR = "评分失败";

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RoomService roomService;


    @Override
    public ResponseVO updateHotelInfo(HotelInfoVO hotelInfoVO, Integer id){
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setHotelName(hotelInfoVO.getHotelName());
        hotel.setAddress(hotelInfoVO.getAddress());
        hotel.setBizRegion(hotelInfoVO.getBizRegion());
        hotel.setHotelStar(hotelInfoVO.getHotelStar());
        hotel.setDescription(hotelInfoVO.getDescription());
        hotel.setDetail(hotelInfoVO.getDetail());
        hotel.setPhoneNum(hotelInfoVO.getPhoneNum());
        try {
            System.out.println("调了");
            System.out.println(hotel.getId());
            int result = hotelMapper.updateHotel(hotel);
            if(result==0){
                System.out.println("找不到需要更新的hotelId");
                return ResponseVO.buildFailure(UPDATE_ERROR);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }


    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, Integer roomId) {
        return roomService.getRoomCurNum(hotelId,roomId);
    }

    @Override
    public ResponseVO rate(Integer hotelId, Double rate) {
        try {
            hotelMapper.rate(hotelId,rate);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<HotelVO> retrieveHotels() {
        List<Hotel> hotels =  hotelMapper.selectAllHotel();
        List<HotelVO> hotelVOs = new ArrayList<>();
        for(Hotel hotel:hotels){
            HotelVO hotelVO = new HotelVO();
            hotelVO.setId(hotel.getId());
            hotelVO.setHotelName(hotel.getHotelName());
            hotelVO.setAddress(hotel.getAddress());
            hotelVO.setBizRegion(hotel.getBizRegion());
            hotelVO.setHotelStar(hotel.getHotelStar());
            hotelVO.setRate(hotel.getRate());
            hotelVO.setDescription(hotel.getDescription());
            hotelVO.setDetail(hotel.getDetail());
            hotelVO.setPhoneNum(hotel.getPhoneNum());
            hotelVO.setManagerId(hotel.getManagerId());
            hotelVO.setEvaluatorNum(hotel.getEvaluatorNum());
            //这里不需要rooms，因为这个返回的是酒店列表，rooms自有retrieveHotelDetails(Integer hotelId)处理
            hotelVOs.add(hotelVO);
        }
        return hotelVOs;
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        Hotel hotel = hotelMapper.selectById(hotelId);
        HotelVO hotelVO = new HotelVO();
        hotelVO.setId(hotel.getId());
        hotelVO.setHotelName(hotel.getHotelName());
        hotelVO.setAddress(hotel.getAddress());
        hotelVO.setBizRegion(hotel.getBizRegion());
        hotelVO.setHotelStar(hotel.getHotelStar());
        hotelVO.setRate(hotel.getRate());
        hotelVO.setDescription(hotel.getDescription());
        hotelVO.setDetail(hotel.getDetail());
        hotelVO.setPhoneNum(hotel.getPhoneNum());
        hotelVO.setManagerId(hotel.getManagerId());
        hotelVO.setEvaluatorNum(hotel.getEvaluatorNum());
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelVO.getId());
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);
        return hotelVO;
    }

    @Override
    public HotelVO retrieveHotelDetailByManager(Integer managerId){
        Hotel hotel = hotelMapper.selectByManagerId(managerId);
        HotelVO hotelVO = new HotelVO();
        hotelVO.setId(hotel.getId());
        hotelVO.setHotelName(hotel.getHotelName());
        hotelVO.setAddress(hotel.getAddress());
        hotelVO.setBizRegion(hotel.getBizRegion());
        hotelVO.setHotelStar(hotel.getHotelStar());
        hotelVO.setRate(hotel.getRate());
        hotelVO.setDescription(hotel.getDescription());
        hotelVO.setDetail(hotel.getDetail());
        hotelVO.setPhoneNum(hotel.getPhoneNum());
        hotelVO.setManagerId(hotel.getManagerId());
        hotelVO.setEvaluatorNum(hotel.getEvaluatorNum());
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelVO.getId());
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);
        return hotelVO;
    }

}
