package com.example.hotel.blImpl.order;

import com.example.hotel.bl.creditRecord.CreditRecordService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.CreditRecordVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service()
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String CREDIT_LACK = "信用值不足,无法预订酒店，请充值信用值";
    private final static String ANNUL_ERROR = "撤销订单失败";
    private final static String CHECKOUT_ERROR = "退房失败";
    private final static String APPEAL_ERROR = "申诉失败";
    private final static String EVALUATE_ERROR = "评价失败";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    CreditRecordService creditRecordService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomId());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        else if(accountService.getUserInfo(orderVO.getUserId()).getCredit()==0){
            return ResponseVO.buildFailure(CREDIT_LACK);
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public List<Order> getHotelOrders(Integer hotelId){
        return orderMapper.getHotelOrders(hotelId);
    }
    @Override
    public List<Order> getEvaluations(Integer hotelId){
        return orderMapper.getEvaluations(hotelId);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        Order order = orderMapper.getOrderById(orderid);
        String checkInDate = order.getCheckInDate();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date latestCheckInTime;
        try{
            latestCheckInTime = sf.parse(checkInDate);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
        Date current = new Date(System.currentTimeMillis());
        if((latestCheckInTime.getTime()-current.getTime())/60*60*1000<6){
            double beforeCredit = accountService.getUserInfo(order.getUserId()).getCredit();
            double change = Math.min(beforeCredit, order.getPrice() / 10);
            accountService.updateUserCredit(order.getUserId(),-change);
            CreditRecordVO creditRecordVO = new CreditRecordVO();
            creditRecordVO.setUserId(order.getUserId());
            creditRecordVO.setOrderId(orderid);
            creditRecordVO.setChangeAction("订单撤销");
            creditRecordVO.setChangeValue(-order.getPrice()/10);
            creditRecordVO.setChangeTime(sf.format(current));
            creditRecordVO.setFinalValue(accountService.getUserInfo(order.getUserId()).getCredit());
            creditRecordService.addCreditRecord(creditRecordVO);
        }
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());
        orderMapper.annulOrder(orderid);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public Order getOrderById(int orderid){
        return orderMapper.getOrderById(orderid);
    }

    @Override
    public ResponseVO executeOrder(int orderid){
        Order order = orderMapper.getOrderById(orderid);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String checkInDate = sf.format(date);
        if(!checkInDate.equals(order.getCheckInDate()))return ResponseVO.buildFailure("入住时间还未到，无法入住");
        CreditRecordVO creditRecordVO = new CreditRecordVO();
        creditRecordVO.setUserId(order.getUserId());
        creditRecordVO.setOrderId(orderid);
        creditRecordVO.setChangeAction("订单执行");
        if(order.getOrderState().equals("异常")){
            accountService.updateUserCredit(order.getUserId(), order.getPrice()/20);
            creditRecordVO.setChangeValue(order.getPrice()/20);
        }
        else {
            accountService.updateUserCredit(order.getUserId(),order.getPrice()/10);
            creditRecordVO.setChangeValue(order.getPrice()/10);
        }
        creditRecordVO.setChangeTime(checkInDate);
        creditRecordVO.setFinalValue(accountService.getUserInfo(order.getUserId()).getCredit());
        creditRecordService.addCreditRecord(creditRecordVO);
        orderMapper.executeOrder(orderid, checkInDate);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO checkOutOrder(int orderid){
        Order order = orderMapper.getOrderById(orderid);
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String checkOutDate = sf.format(date);
        try {
            orderMapper.checkoutOrder(orderid, checkOutDate);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(CHECKOUT_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getExceptionOrders() {
        return orderMapper.getExceptionOrders();
    }

    @Override
    public ResponseVO processExceptionOrder(int orderId){
        Order order = orderMapper.getOrderById(orderId);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-DD");
        String date = sf.format(System.currentTimeMillis());
        CreditRecordVO creditRecordVO = new CreditRecordVO();
        creditRecordVO.setOrderId(orderId);
        creditRecordVO.setUserId(order.getUserId());
        creditRecordVO.setChangeTime(date);
        creditRecordVO.setChangeValue(order.getPrice()/2);
        creditRecordVO.setChangeAction("异常订单申诉");
        accountService.updateUserCredit(order.getUserId(),order.getPrice()/2);
        creditRecordVO.setFinalValue(accountService.getUserInfo(order.getUserId()).getCredit());
        try{
            orderMapper.annulOrder(orderId);
            creditRecordService.addCreditRecord(creditRecordVO);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO appealOrder(int orderId){
        try{
            orderMapper.appealOrder(orderId);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(APPEAL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO processAllLateOrders(){
        List<Order> allOrder = orderMapper.getAllOrders();
        Date current = new Date(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for(Order order:allOrder){
            if(!order.getOrderState().equals("已预订"))continue;
            Date latestCheckInTime;
            try{
                latestCheckInTime = sf.parse(order.getCheckInDate());
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            if(latestCheckInTime.getTime()+24*60*60*1000<current.getTime()){
                double beforeCredit = accountService.getUserInfo(order.getUserId()).getCredit();
                double change = Math.min(beforeCredit, order.getPrice() / 10);
                accountService.updateUserCredit(order.getUserId(),-change);
                CreditRecordVO creditRecordVO = new CreditRecordVO();
                creditRecordVO.setOrderId(order.getId());
                creditRecordVO.setUserId(order.getUserId());
                creditRecordVO.setChangeAction("订单超时");
                creditRecordVO.setChangeValue(-order.getPrice()/10);
                creditRecordVO.setChangeTime(order.getCheckInDate());
                creditRecordVO.setFinalValue(accountService.getUserInfo(order.getUserId()).getCredit());
                creditRecordService.addCreditRecord(creditRecordVO);
                orderMapper.changeOrderStateToException(order.getId());
            }
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO makeEvaluation(int orderId, String evaluation) {
        try {
            orderMapper.makeEvaluation(orderId,evaluation);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(EVALUATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
