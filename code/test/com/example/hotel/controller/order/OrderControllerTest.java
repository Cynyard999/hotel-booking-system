package com.example.hotel.controller.order;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Czh
 * @Date: 2020-06-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {
    @Autowired
    private OrderController orderController;

    @Autowired
    private AdminService adminService;
    @Autowired
    private AccountService accountService;

    @Test
    @Transactional
    @Rollback
    public void reserveHotel() {
        accountService.updateUserCredit(11,-100);
        OrderVO orderVO = new OrderVO();
        orderVO.setHotelId(1);
        orderVO.setRoomId(1);
        orderVO.setRoomType("Family");
        orderVO.setRoomNum(100);
        orderVO.setUserId(11);
        Assert.assertEquals("预订房间数量剩余不足",orderController.reserveHotel(orderVO).getMessage());
        orderVO.setRoomNum(1);
        Assert.assertEquals("信用值不足,无法预订酒店，请充值信用值",orderController.reserveHotel(orderVO).getMessage());
        List<Order> orders = (List<Order>)orderController.retrieveAllOrders().getContent();
        orderVO.setUserId(9);
        Assert.assertTrue(orderController.reserveHotel(orderVO).getSuccess());
        Assert.assertEquals(orders.size()+1, ((List<Order>)orderController.retrieveAllOrders().getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveAllOrders() {
        Assert.assertEquals(5,((List<Order>)orderController.retrieveAllOrders().getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveUserOrders() {
        Assert.assertEquals(5,((List<Order>)orderController.retrieveUserOrders(9).getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveHotelOrders() {
        Assert.assertEquals(5,((List<Order>)orderController.retrieveHotelOrders(2).getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void getEvaluations() {
        Assert.assertEquals(1,((List<Order>)orderController.getEvaluations(2).getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void annulOrder() {
        Assert.assertTrue(orderController.annulOrder(1).getSuccess());
        Assert.assertEquals("已撤销",((OrderVO)orderController.retrieveOrderById(1).getContent()).getOrderState());

    }

    @Test
    @Transactional
    @Rollback
    public void executeOrder() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        OrderVO order1 = new OrderVO();
        order1.setRoomNum(1);
        order1.setUserId(9);
        order1.setHotelId(1);
        order1.setRoomId(1);
        order1.setRoomType("Family");
        order1.setCheckInDate(sf.format(new Date(System.currentTimeMillis())));
        order1.setOrderState("已预订");
        order1.setPrice(299.0);
        orderController.reserveHotel(order1);
        order1.setOrderState("异常");
        orderController.reserveHotel(order1);
        List<Order> allOrders = (List<Order>)orderController.retrieveAllOrders().getContent();
        int length = allOrders.size();
        Assert.assertTrue(orderController.executeOrder(allOrders.get(length-2).getId()).getSuccess());
        Assert.assertEquals("已入住",((OrderVO)orderController.retrieveOrderById(allOrders.get(length-2).getId()).getContent()).getOrderState());
        Assert.assertTrue(orderController.executeOrder(allOrders.get(length-1).getId()).getSuccess());
        Assert.assertEquals("已入住",((OrderVO)orderController.retrieveOrderById(allOrders.get(length-1).getId()).getContent()).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void checkOutOrder() {
        Assert.assertTrue(orderController.checkOutOrder(4).getSuccess());
        Assert.assertEquals("已完成",((OrderVO)orderController.retrieveOrderById(4).getContent()).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveExceptionOrders() {
        Assert.assertEquals(1,((List<Order>)orderController.retrieveExceptionOrders().getContent()).size());
    }

    @Test
    @Transactional
    @Rollback
    public void processExceptionOrder() {
        Assert.assertTrue(orderController.processExceptionOrder(5).getSuccess());
        Assert.assertEquals("已撤销",((OrderVO)orderController.retrieveOrderById(5).getContent()).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void appealOrder() {
        Assert.assertTrue(orderController.appealOrder(4).getSuccess());
        Assert.assertEquals("已申诉",((OrderVO)orderController.retrieveOrderById(4).getContent()).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void processAllLateOrders() {
        OrderVO orderVO = new OrderVO();
        orderVO.setId(0);
        orderVO.setUserId(9);
        orderVO.setRoomNum(1);
        orderVO.setRoomId(1);
        orderVO.setHotelId(1);
        orderVO.setPrice(1000.0);
        orderVO.setCheckInDate("2019-01-01");
        orderController.reserveHotel(orderVO);
        Assert.assertTrue(orderController.processAllLateOrders().getSuccess());
        List<Order> orders = (List<Order>)orderController.retrieveAllOrders().getContent();
        Assert.assertEquals("异常",(orders.get(orders.size()-1)).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void retrieveOrderById() {
        Assert.assertEquals("已完成",((OrderVO)orderController.retrieveOrderById(1).getContent()).getOrderState());
    }

}