package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.HotelInfoVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Zzn
 * @Date: 2020-06-13
 */
@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/addRoomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @GetMapping("/{managerId}/managerHotelDetail")
    public ResponseVO retrieveHotelDetailByManager(@PathVariable Integer managerId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetailByManager(managerId));
    }


    @PostMapping("/{id}/hotelInfo/update")
    public ResponseVO updateHotelInfo(@RequestBody HotelInfoVO hotelInfoVO,@PathVariable int id){
        return hotelService.updateHotelInfo(hotelInfoVO,id);

    }
    @PostMapping("/{hotelId}/rate")
    public ResponseVO rate(@PathVariable Integer hotelId,@RequestBody Map<Object,Double> map){
        return hotelService.rate(hotelId,map.get("rate"));
    }



}
