package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 获取酒店的所有评价
     * @param hotelId
     * @return
     */
    List<Order> getEvaluations(Integer hotelId);

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    /**
     * 获得某一订单的具体信息
     * @param orderid
     * @return
     */
    Order getOrderById(int orderid);

    /**
     * 执行订单,包括执行异常订单
     * @param orderid
     * @return
     */
    ResponseVO executeOrder(int orderid);

    /**
     * 更新退房信息
     * @param orderid
     * @return
     */
    ResponseVO checkOutOrder(int orderid);

    /**
     * 获得所有的已申诉异常订单信息
     * @param
     * @return
     */
    List<Order> getExceptionOrders();

    /**
     * 处理申诉的异常订单
     * @param orderId
     * @return
     */
    ResponseVO processExceptionOrder(int orderId);

    /**
     * 对异常订单进行申诉
     * @param orderId
     * @return
     */
    ResponseVO appealOrder(int orderId);

    /**
     * 将超时订单置为异常，并扣除信用值
     * @param
     * @return
     */
    ResponseVO processAllLateOrders();

    /**
     * 订单完成后进行评价
     * @param orderId
     * @param evaluation
     * @return
     */
    ResponseVO makeEvaluation(int orderId, String evaluation);
}
