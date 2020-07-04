package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Zzn
 * @Date: 2020-06-13
 */
@Service
public class RoomServiceImpl implements RoomService {
    private final static String ROOM_UPDATE_ERROR = "房间变更失败";
    private final static String ROOM_ADD_FAIL = "房间添加失败";

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public ResponseVO insertRoomInfo(HotelRoom hotelRoom) {
        //添加房间，成功则返回true，失败则返回房间添加失败
        try{
            roomMapper.insertRoom(hotelRoom);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(ROOM_ADD_FAIL);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateRoomInfo(Integer hotelId, Integer roomId, Integer rooms) {
        //更新房间信息，如果成功则返回true，失败则返回房间变更失败
        try {
            roomMapper.updateRoomInfo(hotelId,roomId,rooms);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ROOM_UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, Integer roomId) {
        return roomMapper.getRoomCurNum(hotelId,roomId);
    }


}
