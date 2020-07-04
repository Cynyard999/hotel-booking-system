package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: Zzn
 * @Date: 2020-06-13
 */
public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     * @param hotelId 酒店id
     * @return 房间列表
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    /**
     * 添加酒店客房信息
     * @param hotelRoom 房间信息
     * @return 返回添加结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 预订酒店后更新客房房间数量
     * @param hotelId 酒店id
     * @param roomId 房间id
     * @param rooms 指房间数量变化，减少为正数，增加应为负数
     * @return 返回更新结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO updateRoomInfo(Integer hotelId, Integer roomId, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     * @param hotelId 酒店id
     * @param roomId 房间id
     * @return 返回酒店指定房间剩余数量
     */
    int getRoomCurNum(Integer hotelId, Integer roomId);
}
