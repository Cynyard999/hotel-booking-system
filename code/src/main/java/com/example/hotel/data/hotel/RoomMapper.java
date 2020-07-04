package com.example.hotel.data.hotel;

import com.example.hotel.po.HotelRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Zzn
 * @Date: 2020-06-13
 */
@Mapper
@Repository
public interface RoomMapper {

    int updateRoomInfo(@Param("hotelId") Integer hotelId,@Param("roomId") Integer roomId,@Param("curNum") Integer curNum);

    int insertRoom(HotelRoom hotelRoom);

    List<HotelRoom> selectRoomsByHotelId(@Param("hotelId") Integer hotelId);

    int getRoomCurNum(@Param("hotelId") Integer hotelId,@Param("roomId") Integer roomId);
}
