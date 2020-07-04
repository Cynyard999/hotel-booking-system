package com.example.hotel.bl.favorite;

import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @author Qxy
 * @Date: 2020-05-26
 */
public interface FavoriteService {

  /**
   * 向对应用户的收藏夹中添加酒店
   * @param userId 用户id
   * @param hotelId 酒店id
   * @return 返回添加结果，如果成功返回true，失败则返回失败信息
   */
  ResponseVO favorHotel(Integer userId, Integer hotelId);


  /**
   * 向对应用户的收藏夹中删除酒店
   * @param userId 用户id
   * @param hotelId 酒店id
   * @return 返回删除结果，如果成功返回true，失败则返回失败信息
   */
  ResponseVO disfavorHotel(Integer userId, Integer hotelId);

  /**
   * 搜索对应用户的收藏夹列表
   * @param userId 用户id
   * @return 返回收藏夹列表
   */
  List<HotelVO> retrieveFavoriteList(Integer userId);

  /**
   *判断对应酒店是否在对应用户的收藏夹里
   * @param userId 用户id
   * @param hotelId 酒店id
   * @return 如果在则返回1，如果不在则返回0
   */
  int isFavored(Integer userId, Integer hotelId);
}
