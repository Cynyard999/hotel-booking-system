package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    @Transactional
    @Rollback
    public void addOrder() {
        Order order = new Order();
        order.setOrderState("已预订");
        order.setHotelId(1);
        int temp = orderMapper.getAllOrders().size();
        orderMapper.addOrder(order);
        Assert.assertEquals(temp+1,orderMapper.getAllOrders().size());
    }

    @Test
    @Transactional
    @Rollback
    public void getAllOrders() {
        Assert.assertEquals(5,orderMapper.getAllOrders().size());
    }

    @Test
    @Transactional
    @Rollback
    public void getUserOrders() {
        Assert.assertEquals(5,orderMapper.getUserOrders(9).size());
    }

    @Test
    @Transactional
    @Rollback
    public void annulOrder() {
        orderMapper.annulOrder(1);
        Assert.assertEquals("已撤销",orderMapper.getOrderById(1).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void getOrderById() {
        Assert.assertEquals("已撤销",orderMapper.getOrderById(2).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void executeOrder() {
        orderMapper.executeOrder(1,new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        Assert.assertEquals("已入住",orderMapper.getOrderById(1).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void checkoutOrder() {
        orderMapper.checkoutOrder(3,new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
    }

    @Test
    @Transactional
    @Rollback
    public void getExceptionOrders() {
        Assert.assertEquals(1,orderMapper.getExceptionOrders().size());
    }

    @Test
    @Transactional
    @Rollback
    public void getHotelOrders() {
        Assert.assertEquals(5,orderMapper.getHotelOrders(2).size());
    }

    @Test
    @Transactional
    @Rollback
    public void appealOrder() {
        orderMapper.appealOrder(4);
        Assert.assertEquals("已申诉",orderMapper.getOrderById(4).getOrderState());
    }

    @Test
    @Transactional
    @Rollback
    public void changeOrderStateToException() {
        orderMapper.changeOrderStateToException(1);
        Assert.assertEquals("异常",orderMapper.getOrderById(1).getOrderState());
    }
}