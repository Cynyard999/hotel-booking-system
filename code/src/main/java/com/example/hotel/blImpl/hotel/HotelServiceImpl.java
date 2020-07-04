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

/**
 * @Author: Zzn
 * @Date: 2020-06-13
 */
@Service
public class HotelServiceImpl implements HotelService {
    private final static String HOTEL_UPDATE_ERROR = "酒店信息修改失败";
    private final static String ROOM_UPDATE_ERROR = "房间变更失败";
    private final static String RATE_ERROR = "评分失败";

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RoomService roomService;

    @Override
    public ResponseVO updateHotelInfo(HotelInfoVO hotelInfoVO, Integer id){
        //hotelInfoVO->po.hotel(注意还有id值！)
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setHotelName(hotelInfoVO.getHotelName());
        hotel.setAddress(hotelInfoVO.getAddress());
        hotel.setBizRegion(hotelInfoVO.getBizRegion());
        hotel.setHotelStar(hotelInfoVO.getHotelStar());
        hotel.setDescription(hotelInfoVO.getDescription());
        hotel.setDetail(hotelInfoVO.getDetail());
        hotel.setPhoneNum(hotelInfoVO.getPhoneNum());
        //更新酒店信息，如果成功则返回true，失败则返回酒店信息修改失败
        try {
            hotelMapper.updateHotel(hotel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(HOTEL_UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }


    @Override
    public ResponseVO updateRoomInfo(Integer hotelId, Integer roomId, Integer rooms) {
        //更新房间信息，如果成功则返回true，失败则返回房间变更失败
        try {
            roomService.updateRoomInfo(hotelId,roomId,rooms);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ROOM_UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, Integer roomId) {
        return roomService.getRoomCurNum(hotelId,roomId);
    }

    @Override
    public ResponseVO rate(Integer hotelId, Double rate) {
        //对相应酒店进行评分，成功则返回true，失败则返回评分失败
        try {
            hotelMapper.rate(hotelId,rate);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(RATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<HotelVO> retrieveHotels() {
        List<Hotel> hotels =  hotelMapper.selectAllHotel();
        List<HotelVO> hotelVOs = new ArrayList<>();
        //hotel.PO->VO
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
        //hotel.PO->VO
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
        //room，PO->VO
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
        //hotel.PO->VO
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
        //room，PO->VO
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
