package com.example.hotel.controller.favorite;

import com.example.hotel.vo.HotelVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zzn
 * @Date: 2020-06-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FavoriteControllerTest {

    @Autowired
    private FavoriteController favoriteController;

    @Test
    @Transactional
    @Rollback
    public void favorHotel() {
        Map<Object, Integer> map = new HashMap<>();
        map.put("userId",9);
        map.put("hotelId",1);
        Assert.assertTrue(favoriteController.favorHotel(map).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void disfavorHotel() {
        Map<Object, Integer> map = new HashMap<>();
        map.put("userId",9);
        map.put("hotelId",1);
        favoriteController.favorHotel(map);
        Assert.assertTrue(favoriteController.disfavorHotel(map).getSuccess());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveFavoriteList() {
        List<HotelVO> favorites = (List<HotelVO>)favoriteController.retrieveFavoriteList(9).getContent();
        Assert.assertEquals(0,favorites.size());
    }

    @Test
    @Transactional
    @Rollback
    public void isFavored() {
        Map<Object, Integer> map = new HashMap<>();
        map.put("userId",9);
        map.put("hotelId",1);
        favoriteController.favorHotel(map);
        Assert.assertEquals(0,favoriteController.isFavored(9,3).getContent());
        Assert.assertNotEquals(0,favoriteController.isFavored(9,1).getContent());
    }

}
