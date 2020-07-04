package com.example.hotel.bl.hotel;

import com.example.hotel.vo.HotelInfoVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: Zzn
 * @Date: 2020-06-13
 */
public interface HotelService {

    /**
     * 维护酒店基本信息
     * @param hotelInfoVO 更新的酒店信息
     * @param id 对应酒店的id
     * @return 返回更新结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO updateHotelInfo(HotelInfoVO hotelInfoVO,Integer id);

    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId 对应酒店的id
     * @param roomId 要修改的客房id
     * @param rooms 指房间数量变化，减少为正数，增加应为负数
     * @return 返回更新结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO updateRoomInfo(Integer hotelId, Integer roomId,Integer rooms);

    /**
     * 列表获取酒店信息
     * @return 返回酒店列表
     */
    List<HotelVO> retrieveHotels();

    /**
     * 获取某家酒店详细信息
     * @param hotelId 酒店id
     * @return 返回对应酒店的详细信息
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 获取某家酒店详细信息,通过酒店工作人员ID
     * @param managerId 酒店工作人员id
     * @return 返回根据酒店工作人员id对应的酒店详细信息
     */
    HotelVO retrieveHotelDetailByManager(Integer managerId);

    /**
     * 查看酒店剩余房间数量(根据roomId选择)
     * @param hotelId 酒店id
     * @param roomId 房间id
     * @return 返回酒店指定房间剩余数量
     */
    int getRoomCurNum(Integer hotelId,Integer roomId);

    /**
     * 给酒店打分
     * @param hotelId 酒店id
     * @param rate 评分值
     * @return 返回评分结果，成功则返回true，失败则返回评分失败
     */
    ResponseVO rate(Integer hotelId, Double rate);

}
