package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
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
public interface HotelMapper {

    List<Hotel> selectAllHotel();

    Hotel selectById(@Param("id") Integer id);

    Hotel selectByManagerId(@Param("manager_id") Integer manager_id);

    int updateHotel(Hotel hotel);

    void rate(@Param("hotelId") Integer hotelId,@Param("rate") Double rate);
}
