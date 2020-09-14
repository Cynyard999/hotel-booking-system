package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }

    @GetMapping("/{hotelId}/evaluations")
    public ResponseVO getEvaluations(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getEvaluations(hotelId));
    }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid){
        return orderService.annulOrder(orderid);
    }

    @PostMapping("/{orderid}/executeOrder")
    public ResponseVO executeOrder(@PathVariable int orderid){
        return orderService.executeOrder(orderid);
    }

    @PostMapping("/{orderid}/checkOutOrder")
    public ResponseVO checkOutOrder(@PathVariable int orderid){
        return orderService.checkOutOrder(orderid);
    }

    @GetMapping("/getExceptionOrders")
    public ResponseVO retrieveExceptionOrders(){
        return ResponseVO.buildSuccess(orderService.getExceptionOrders());
    }

    @PostMapping("/{orderid}/processExceptionOrder")
    public ResponseVO processExceptionOrder(@PathVariable int orderid){
        return orderService.processExceptionOrder(orderid);
    }

    @PostMapping("/{orderid}/appealOrder")
    public ResponseVO appealOrder(@PathVariable int orderid){
        return orderService.appealOrder(orderid);
    }

    @PostMapping("/processAllLateOrders")
    public ResponseVO processAllLateOrders(){
        return orderService.processAllLateOrders();
    }

    @GetMapping("/{orderid}/getOrderById")
    public ResponseVO retrieveOrderById(@PathVariable int orderid){
        return ResponseVO.buildSuccess(orderService.getOrderById(orderid));
    }
    @PostMapping("/{orderId}/evaluate")
    public ResponseVO makeEvaluation(@PathVariable int orderId,@RequestBody Map<Object,String> map){
        return orderService.makeEvaluation(orderId,map.get("evaluation"));
    }
}
