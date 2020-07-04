package com.example.hotel.blImpl.order;

import com.example.hotel.bl.creditRecord.CreditRecordService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.UserVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceImplTest {
    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private HotelService hotelService;
    @Mock
    private AccountService accountService;
    @Mock
    private CreditRecordService creditRecordService;

    @Before
    public void setUp(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        long current = System.currentTimeMillis();
        Order order = new Order();
        order.setHotelId(0);
        order.setUserId(0);
        order.setPrice(100.0);
        order.setRoomType("Family");
        order.setRoomNum(1);
        order.setOrderState("已预订");
        order.setCheckInDate(sf.format(new Date(current)));
        List<Order> list = new ArrayList<>();
        list.add(order);
        Order order1 = new Order();
        order1.setCheckInDate(sf.format(new Date(current+24*60*60*1000)));
        order1.setHotelId(0);
        order1.setRoomNum(1);
        order1.setRoomType("Family");
        order1.setOrderState("异常");
        order1.setPrice(100.0);
        list.add(order1);
        Order order2 = new Order();
        order2.setUserId(0);
        order2.setPrice(100.0);
        order2.setUserId(0);
        order2.setCheckInDate(sf.format(new Date(current-25*60*60*1000)));
        order2.setOrderState("已预订");
        order2.setId(2);
        list.add(order2);
        Mockito.when(orderMapper.getAllOrders()).thenReturn(list);
        Mockito.when(orderMapper.getUserOrders(0)).thenReturn(list);
        Mockito.when(orderMapper.getHotelOrders(0)).thenReturn(list);
        Mockito.when(orderMapper.getOrderById(0)).thenReturn(order);
        Mockito.when(orderMapper.getOrderById(1)).thenReturn(order1);
        Mockito.when(orderMapper.getExceptionOrders()).thenReturn(list);
    }
    @Test
    public void addOrder() {
        UserVO userVO1 = new UserVO();
        userVO1.setCredit(0);
        UserVO userVO2 = new UserVO();
        userVO2.setCredit(100);
        Mockito.when(hotelService.getRoomCurNum(0,0)).thenReturn(1);
        Mockito.when(hotelService.getRoomCurNum(0,1)).thenReturn(2);
        Mockito.when(accountService.getUserInfo(0)).thenReturn(userVO1);
        Mockito.when(accountService.getUserInfo(1)).thenReturn(userVO2);
        OrderVO orderVO = new OrderVO();
        orderVO.setRoomNum(2);
        orderVO.setHotelId(0);
        orderVO.setRoomId(0);
        orderVO.setUserId(1);
        Assert.assertEquals("预订房间数量剩余不足",orderService.addOrder(orderVO).getMessage());
        orderVO.setRoomId(1);
        orderVO.setUserId(0);
        Assert.assertEquals("信用值不足,无法预订酒店，请充值信用值",orderService.addOrder(orderVO).getMessage());
        orderVO.setUserId(1);
        Assert.assertTrue(orderService.addOrder(orderVO).getSuccess());
    }

    @Test
    public void getAllOrders() {
        List<Order> list = orderService.getAllOrders();
        Assert.assertEquals("已预订",list.get(0).getOrderState());
    }

    @Test
    public void getUserOrders() {
        Assert.assertEquals("已预订",orderService.getUserOrders(0).get(0).getOrderState());
    }

    @Test
    public void getHotelOrders() {
        Assert.assertEquals("已预订",orderService.getUserOrders(0).get(0).getOrderState());
    }

    @Test
    public void annulOrder() {
        UserVO tempUser = new UserVO();
        tempUser.setCredit(0);
        Mockito.when(accountService.getUserInfo(0)).thenReturn(tempUser);
        Assert.assertTrue(orderService.annulOrder(0).getSuccess());
        Assert.assertTrue(orderService.annulOrder(1).getSuccess());
    }

    @Test
    public void getOrderById() {
        Order order = new Order();
        order.setOrderState("已预订");
        Mockito.when(orderMapper.getOrderById(0)).thenReturn(order);
        Assert.assertEquals("已预订",orderService.getOrderById(0).getOrderState());
    }

    @Test
    public void executeOrder() {
        UserVO tempUser = new UserVO();
        tempUser.setCredit(100);
        Order order = new Order();
        order.setPrice(100.0);
        order.setOrderState("异常");
        order.setCheckInDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        order.setUserId(0);
        Mockito.when(accountService.getUserInfo(0)).thenReturn(tempUser);
        Mockito.when(orderMapper.getOrderById(2)).thenReturn(order);
        Assert.assertTrue(orderService.executeOrder(0).getSuccess());
        Assert.assertEquals("入住时间还未到，无法入住",orderService.executeOrder(1).getMessage());
        Assert.assertTrue(orderService.executeOrder(2).getSuccess());
    }

    @Test
    public void checkOutOrder() {
        Assert.assertTrue(orderService.checkOutOrder(0).getSuccess());
    }

    @Test
    public void getExceptionOrders() {
        Assert.assertEquals("Family",orderService.getExceptionOrders().get(0).getRoomType());
    }

    @Test
    public void processExceptionOrder() {
        UserVO temp = new UserVO();
        temp.setCredit(100.0);
        Mockito.when(accountService.getUserInfo(0)).thenReturn(temp);
        Assert.assertTrue(orderService.processExceptionOrder(0).getSuccess());
    }

    @Test
    public void appealOrder() {
        Assert.assertTrue(orderService.appealOrder(0).getSuccess());
    }

    @Test
    public void processAllLateOrders() {
        UserVO temp = new UserVO();
        temp.setCredit(100);
        Mockito.when(accountService.getUserInfo(0)).thenReturn(temp);
        Assert.assertTrue(orderService.processAllLateOrders().getSuccess());
    }
}