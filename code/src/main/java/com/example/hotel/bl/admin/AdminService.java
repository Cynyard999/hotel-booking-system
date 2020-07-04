package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: lxz
 * @Date: 2020-06-22
 */
public interface AdminService {

    /**
     * 添加用户账号并返回对应的id值
     * @param userVO 新注册user信息
     * @return 添加结果，如果成功返回userId，失败则返回失败信息
     */
    ResponseVO addUser(UserVO userVO);

    /**
     * 根据type返回不同身份的用户列表
     * @param type 用户身份类型，Client/HotelManager/Manager/SaleMen
     * @return 相应类型的用户列表
     */
    List<UserVO> getUserList(String type);

    /**
     * 更新用户信息
     * @param userVO 需要更新的user信息
     * @return 返回更新结果，如果成功就返回true，失败则返回失败信息
     */
    ResponseVO updateUserInfo(UserVO userVO);

    /**
     * 添加酒店
     * @param hotelVO 要添加的hotel信息
     * @return 添加结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO addHotel(HotelVO hotelVO);

    /**
     * 为酒店添加/变更酒店工作人员
     * @param hotelVO 包含hotelId和ManagerId
     * @return 返回更新结果，成功则返回true，失败返回失败信息
     */
    ResponseVO updateManagerOfHotel(HotelVO hotelVO);

    /**
     * 获取用户个人信息
     * @param id user的id
     * @return 成功则返回对应id的用户信息，失败则返回失败信息
     */
    UserVO getUserInfo(int id);

    /**
     * 上传图片
     * @param hotelId 用hotelId作为图片上传至OSS的文件名（这样就可以直接使用前缀+hotelId的url来访问酒店图片了）
     * @param file 上传的图片文件
     * @return 返回上传结果，成功返回true，失败则返回失败信息
     */
    ResponseVO uploadFile(String hotelId,MultipartFile file);

    /**
     * 删除hotelId的酒店
     * @param hotelId 要删除的酒店id值
     * @return 返回删除结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO deleteHotel(int hotelId);

    /**
     * 删除userId的用户
     * @param userId 要删除的用户id值
     * @return 返回删除结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO deleteUser(int userId);
}
