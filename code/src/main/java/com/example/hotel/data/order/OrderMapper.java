package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

  int addOrder(Order order);

  List<Order> getAllOrders();

  List<Order> getUserOrders(@Param("userid") int userid);

  int annulOrder(@Param("orderid") int orderid);

  Order getOrderById(@Param("orderid") int orderid);

  int executeOrder(@Param("orderid") int orderid, @Param("checkInDate") String checkInDate);

  int checkoutOrder(@Param("orderid") int orderid, @Param("checkOutDate") String checkOutDate);

  List<Order> getExceptionOrders();

  List<Order> getHotelOrders(@Param("hotelid") int hotelid);

  List<Order> getEvaluations(@Param("hotelid") int hotelid);

  int appealOrder(@Param("orderId") int orderId);

  int changeOrderStateToException(@Param("orderId") int orderId);

  void makeEvaluation(@Param("orderId") int orderId, @Param("evaluation") String evaluation);
}
