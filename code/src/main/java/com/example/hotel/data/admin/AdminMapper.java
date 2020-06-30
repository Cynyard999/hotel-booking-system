package com.example.hotel.data.admin;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface AdminMapper {

    int addUser(User user);

    List<User> getUserList(String type);

    int updateUserInfo(User user);


    int addHotel(Hotel hotel);

    int updateHotel(Hotel hotel);

    Hotel getHotelInfo(int hotelId);

    int deleteHotel(int hotelId);

    int deleteUser(int id);

    User getUserInfo(int id);



}
