package com.example.hotel.bl.favorite;

import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface FavoriteService {

  /**
   * @param userId
   * @param hotelId
   * @return
   */
  ResponseVO favorHotel(Integer userId, Integer hotelId);


  /**
   * @param userId
   * @param hotelId
   * @return
   */
  ResponseVO disfavorHotel(Integer userId, Integer hotelId);

  /**
   * @param userId
   * @return
   */
  List<HotelVO> retrieveFavoriteList(Integer userId);

  /**
   *
   * @param userId
   * @param hotelId
   * @return
   */
  int isFavored(Integer userId, Integer hotelId);
}
