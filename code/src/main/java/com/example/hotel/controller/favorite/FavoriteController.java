package com.example.hotel.controller.favorite;

import com.example.hotel.bl.favorite.FavoriteService;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Qxy
 * @Date: 2020-05-26
 */
@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

  @Autowired
  private FavoriteService favoriteService;


  @PostMapping("/favorHotel")
  public ResponseVO favorHotel(@RequestBody Map<Object,Integer> map) {
    int userId = map.get("userId");
    int hotelId = map.get("hotelId");
    return favoriteService.favorHotel(userId, hotelId);
  }

  @PostMapping("/disfavorHotel")
  public ResponseVO disfavorHotel(@RequestBody Map<Object,Integer> map) {
    int userId = map.get("userId");
    int hotelId = map.get("hotelId");
    return favoriteService.disfavorHotel(userId, hotelId);
  }

  @GetMapping("/{id}/retrieveFavoriteList")
  public ResponseVO retrieveFavoriteList(@PathVariable Integer id) {
    return ResponseVO.buildSuccess(favoriteService.retrieveFavoriteList(id));
  }

  @GetMapping("/{id}/{hotelId}/isFavored")
  public ResponseVO isFavored(@PathVariable Integer id,@PathVariable Integer hotelId) {
    Integer res = favoriteService.isFavored(id, hotelId);
    return ResponseVO.buildSuccess(res);
  }
}
