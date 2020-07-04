package com.example.hotel.blImpl.favorite;

import com.example.hotel.bl.favorite.FavoriteService;
import com.example.hotel.data.favorite.FavoriteMapper;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Qxy
 * @Date: 2020-05-26
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
  private final static String ADD_FAIL = "收藏夹添加失败";
  private final static String DELETE_FAIL = "收藏夹添加失败";

  @Autowired
  private FavoriteMapper favoriteMapper;


  @Override
  public ResponseVO favorHotel(Integer userId, Integer hotelId) {
    //向收藏夹中添加酒店，如果成功便返回true，否则收藏夹添加失败
    try {
      favoriteMapper.favorHotel(userId,hotelId);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseVO.buildFailure(ADD_FAIL);
    }
    return ResponseVO.buildSuccess(true);
  }

  @Override
  public ResponseVO disfavorHotel(Integer userId, Integer hotelId) {
    //向收藏夹中删除酒店，如果成功便返回true，否则收藏夹删除失败
    try {
      favoriteMapper.disfavorHotel(userId,hotelId);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseVO.buildFailure(DELETE_FAIL);
    }
    return ResponseVO.buildSuccess(true);
  }

  @Override
  public List<HotelVO> retrieveFavoriteList(Integer userId) {
    return favoriteMapper.retrieveFavoriteList(userId);
  }

  @Override
  public int isFavored(Integer userId, Integer hotelId) {
    //返回mapper的影响行数，即搜索到的数据库行数，为0则不在，为1则在
    return favoriteMapper.isFavored(userId,hotelId);
  }


}
