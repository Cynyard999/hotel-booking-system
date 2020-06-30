package com.example.hotel.data.favorite;

import com.example.hotel.vo.HotelVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class FavoriteMapperTest {
  @Autowired
  private FavoriteMapper favoriteMapper;

  @Test
  public void retrieveFavoriteList() {
    List<HotelVO> hotelVOList = favoriteMapper.retrieveFavoriteList(1);
    assertEquals(0, hotelVOList.size());
  }

  @Test
  public void favorHotel() {
    favoriteMapper.favorHotel(1,1);
    List<HotelVO> hotelVOList = favoriteMapper.retrieveFavoriteList(1);
    assertEquals(1,hotelVOList.size());
  }

  @Test
  public void disfavorHotel() {
    favoriteMapper.favorHotel(1,1);
    List<HotelVO> hotelVOList = favoriteMapper.retrieveFavoriteList(1);
    assertEquals(1,hotelVOList.size());
    favoriteMapper.disfavorHotel(1,1);
    assertEquals(0,favoriteMapper.retrieveFavoriteList(1).size());
  }

  @Test
  public void isFavored() {
    favoriteMapper.favorHotel(1,1);
    assertTrue(favoriteMapper.isFavored(1,1)>0);
  }
}