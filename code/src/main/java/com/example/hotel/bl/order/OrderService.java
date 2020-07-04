package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: Czh
 * @Date: 2020-05-27
 */
public interface OrderService {

    /**
     * 添加订单
     * @param orderVO 订单信息
     * @return 返回添加订单结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return 返回所有订单信息列表
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userId 用户id
     * @return 返回用户订单列表
     */
    List<Order> getUserOrders(int userId);

    /**
     * 查看酒店的所有订单
     * @param hotelId 酒店id
     * @return 返回酒店订单列表
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 获取酒店的所有评价
     * @param hotelId 酒店id
     * @return 返回酒店评价列表（已做出评价的订单列表）
     */
    List<Order> getEvaluations(Integer hotelId);

    /**
     * 撤销订单
     * @param orderId 订单id
     * @return 返回撤销结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO annulOrder(int orderId);

    /**
     * 获得某一订单的具体信息
     * @param orderId 订单id
     * @return 返回订单详情
     */
    OrderVO getOrderById(int orderId);

    /**
     * 执行订单,包括执行异常订单
     * @param orderId 订单id
     * @return 返回执行结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO executeOrder(int orderId);

    /**
     * 订单退房操作
     * @param orderId 订单id
     * @return 返回退房结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO checkOutOrder(int orderId);

    /**
     * 获得所有的已申诉异常订单信息
     * @return 返回已申诉异常订单列表
     */
    List<Order> getExceptionOrders();

    /**
     * 处理申诉的异常订单
     * @param orderId 订单id
     * @return 返回处理结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO processExceptionOrder(int orderId);

    /**
     * 对异常订单进行申诉
     * @param orderId 订单id
     * @return 返回申诉结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO appealOrder(int orderId);

    /**
     * 将超时订单置为异常，并扣除信用值
     * @return 返回设置异常结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO processAllLateOrders();

    /**
     * 订单完成后进行评价
     * @param orderId 订单id
     * @param evaluation 评价内容
     * @return 返回订单评价结果，成功则返回true，失败则返回失败信息
     */
    ResponseVO makeEvaluation(int orderId, String evaluation);
}
