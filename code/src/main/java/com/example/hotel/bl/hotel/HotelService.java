package com.example.hotel.bl.hotel;

import com.example.hotel.vo.HotelInfoVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface HotelService {

    /**
     * 维护酒店基本信息
     * @param hotelInfoVO
     * @param id
     */
    ResponseVO updateHotelInfo(HotelInfoVO hotelInfoVO,Integer id);

    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId
     * @param roomType
     * @param rooms 指房间数量变化，减少为正数，增加应为负数
     */
    void updateRoomInfo(Integer hotelId, String roomType,Integer rooms);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels();

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 获取某家酒店详细信息,通过酒店工作人员ID
     * @param managerId
     * @return
     */
    HotelVO retrieveHotelDetailByManager(Integer managerId);

    /**
     * 查看酒店剩余某种房间数量(根据roomId选择)
     * @param hotelId
     * @param roomId
     * @return
     */
    int getRoomCurNum(Integer hotelId,Integer roomId);

    /**
     * 给酒店打分
     * @param hotelId
     * @param rate
     */
    ResponseVO rate(Integer hotelId, Double rate);

}
