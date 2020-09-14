package com.example.hotel.bl.admin;

import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface AdminService {

    /**
     * 添加所有类型的用户账号
     * @param userVO
     * @return
     */
    ResponseVO addUser(UserVO userVO);

    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<UserVO> getUserList(String type);

    /**
     * 更新用户信息
     * @param userVO
     * @return
     */
    ResponseVO updateUserInfo(UserVO userVO);


    /**
     * 添加酒店
     * @param hotelVO
     * @return
     */
    ResponseVO addHotel(HotelVO hotelVO);

    /**
     * 更新酒店的managerId
     * @param hotelVO
     * @return
     */

    ResponseVO updateHotel(HotelVO hotelVO);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    ResponseVO getUserInfo(int id);

    /**
     * 上传图片
     * @param hotelId
     * @param file
     * @return
     */
    ResponseVO uploadFile(String hotelId,MultipartFile file);

    /**
     * 删除hotelId的酒店
     * @param hotelId
     * @return
     */
    ResponseVO deleteHotel(int hotelId);

    /**
     * 删除userId的用户
     * @param userId
     * @return
     */
    ResponseVO deleteUser(int userId);
}
