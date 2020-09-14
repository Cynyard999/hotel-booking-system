package com.example.hotel.blImpl.favorite;

import com.example.hotel.bl.favorite.FavoriteService;
import com.example.hotel.data.favorite.FavoriteMapper;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author cynyard
 */

@Service
public class FavoriteServiceImpl implements FavoriteService {

  @Autowired
  private FavoriteMapper favoriteMapper;


  @Override
  public ResponseVO favorHotel(Integer userId, Integer hotelId) {
    favoriteMapper.favorHotel(userId,hotelId);
    return ResponseVO.buildSuccess(true);
  }

  @Override
  public ResponseVO disfavorHotel(Integer userId, Integer hotelId) {
    favoriteMapper.disfavorHotel(userId,hotelId);
    return ResponseVO.buildSuccess(true);
  }

  @Override
  public List<HotelVO> retrieveFavoriteList(Integer userId) {
    return favoriteMapper.retrieveFavoriteList(userId);
  }

  @Override
  public int isFavored(Integer userId, Integer hotelId) {
    return favoriteMapper.isFavored(userId,hotelId);
  }


}
