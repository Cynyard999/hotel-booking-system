package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lxz
 * @Date: 2020-06-22
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addUser")
    public ResponseVO addUser(@RequestBody UserVO userVO){
        return adminService.addUser(userVO);
    }

    @GetMapping("/{type}/getUserList")
    public ResponseVO getUserList(@PathVariable String type){
        return ResponseVO.buildSuccess(adminService.getUserList(type));
    }

    @PostMapping("/updateUserInfo")
    public ResponseVO updateUserInfo(@RequestBody UserVO userVO){
        return adminService.updateUserInfo(userVO);
    }

    @PostMapping("/addHotel")
    public ResponseVO addHotel(@RequestBody HotelVO hotelVO){
        return adminService.addHotel(hotelVO);
    }

    @PostMapping("/updateManagerOfHotel")
    public ResponseVO updateHotel(@RequestBody HotelVO hotelVO){
        return adminService.updateManagerOfHotel(hotelVO);
    }

    @GetMapping("/deleteHotel/{hotelId}")
    public ResponseVO deleteHotel(@PathVariable int hotelId){
        return adminService.deleteHotel(hotelId);
    }

    @GetMapping("/deleteUser/{userId}")
    public ResponseVO deleteUser(@PathVariable int userId){
        return adminService.deleteUser(userId);
    }

    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id){
        return ResponseVO.buildSuccess(adminService.getUserInfo(id));
    }

    @PostMapping("/saveFile")
    public ResponseVO uploadFile(@RequestParam("hotelId") String hotelId,@RequestParam("file") MultipartFile file) {
        return adminService.uploadFile(hotelId,file);
    }
}
