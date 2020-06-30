package com.example.hotel.data.favorite;


import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cynyard
 */
@Mapper
@Repository
public interface FavoriteMapper {

  List<HotelVO> retrieveFavoriteList(@Param("userId") Integer userId);

  void favorHotel(@Param("userId") Integer userId, @Param("hotelId") Integer hotelId);

  void disfavorHotel(@Param("userId") Integer userId, @Param("hotelId") Integer hotelId);

  int isFavored(@Param("userId") Integer userId, @Param("hotelId") Integer hotelId);
}
