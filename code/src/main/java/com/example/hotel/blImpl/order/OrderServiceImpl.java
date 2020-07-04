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
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Czh
 * @Date: 2020-05-27
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
        //预订房间数量不能超过剩余数量
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        //信用值为0时不能预订酒店（注：信用值最低为0，不会为负值）
        else if(accountService.getUserInfo(orderVO.getUserId()).getCredit()==0){
            return ResponseVO.buildFailure(CREDIT_LACK);
        }
        //添加订单，如果成功便返回true，否则返回添加预订失败
        try {
            //设置下单时间
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            UserVO userVO = accountService.getUserInfo(orderVO.getUserId());
            User user = new User();
            BeanUtils.copyProperties(userVO,user);
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            //通过反射将一个对象的值赋值个另外一个对象
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomId(),orderVO.getRoomNum());
        } catch (Exception e) {
            e.printStackTrace();
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
        //设置下单时间
        String checkInDate = order.getCheckInDate();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date latestCheckInTime;
        try{
            latestCheckInTime = sf.parse(checkInDate);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
        //在入住前6小时内撤销订单，会扣除订单额度10%的信用值
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
        //订单撤销后恢复酒店房间数量
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomId(), -order.getRoomNum());
        orderMapper.annulOrder(orderid);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public OrderVO getOrderById(int orderid){
        //order,PO->VO
        Order order = orderMapper.getOrderById(orderid);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        return orderVO;
    }

    @Override
    public ResponseVO executeOrder(int orderid){
        Order order = orderMapper.getOrderById(orderid);
        //获取当前时间与入住时间作比较
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String checkInDate = sf.format(date);
        Date current,plannedTime;
        try{
            current = sf.parse(checkInDate);
            plannedTime = sf.parse(order.getCheckInDate());
        } catch (Exception e){
            return ResponseVO.buildFailure("入住失败，请稍后重试");
        }
        if(current.getTime()<plannedTime.getTime())return ResponseVO.buildFailure("入住时间还未到，无法入住");
        //可以入住，生成相应的信用值变化记录，并且改变信用值
        CreditRecordVO creditRecordVO = new CreditRecordVO();
        creditRecordVO.setUserId(order.getUserId());
        creditRecordVO.setOrderId(orderid);
        creditRecordVO.setChangeAction("订单执行");
        //
        if(order.getOrderState().equals("异常")){
            accountService.updateUserCredit(order.getUserId(), order.getPrice()/20);
            creditRecordVO.setChangeValue(order.getPrice()/20);
        }
        else {
            accountService.updateUserCredit(order.getUserId(),order.getPrice()/10);
            creditRecordVO.setChangeValue(order.getPrice()/10);
        }
        //
        creditRecordVO.setChangeTime(checkInDate);
        creditRecordVO.setFinalValue(accountService.getUserInfo(order.getUserId()).getCredit());
        creditRecordService.addCreditRecord(creditRecordVO);
        orderMapper.executeOrder(orderid, checkInDate);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO checkOutOrder(int orderid){
        //完成订单/退房，恢复房间数量，记录退房时间
        Order order = orderMapper.getOrderById(orderid);
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomId(), -order.getRoomNum());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String checkOutDate = sf.format(date);
        //退房，成功返回true，失败返回退房失败
        try {
            orderMapper.checkoutOrder(orderid, checkOutDate);
        } catch (Exception e){
            e.printStackTrace();
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
        //记录时间，处理异常订单并且改变用户信用值
        CreditRecordVO creditRecordVO = new CreditRecordVO();
        creditRecordVO.setOrderId(orderId);
        creditRecordVO.setUserId(order.getUserId());
        creditRecordVO.setChangeTime(date);
        creditRecordVO.setChangeValue(order.getPrice()/2);
        creditRecordVO.setChangeAction("异常订单申诉");
        accountService.updateUserCredit(order.getUserId(),order.getPrice()/20);
        creditRecordVO.setFinalValue(accountService.getUserInfo(order.getUserId()).getCredit());
        //订单标记为已撤销，记录本次信用值变化记录，成功则返回true，失败返回撤销失败
        try{
            orderMapper.annulOrder(orderId);
            creditRecordService.addCreditRecord(creditRecordVO);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO appealOrder(int orderId){
        //申诉订单，成功则返回true，失败则返回申诉失败
        try{
            orderMapper.appealOrder(orderId);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(APPEAL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO processAllLateOrders(){
        //将超时订单置为异常，并扣除信用值
        List<Order> allOrder = orderMapper.getAllOrders();
        Date current = new Date(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for(Order order:allOrder){
            //只处理已预订状态的订单
            if(!order.getOrderState().equals("已预订"))continue;
            Date latestCheckInTime;
            try{
                latestCheckInTime = sf.parse(order.getCheckInDate());
            } catch (Exception e){
                e.printStackTrace();
                continue;
            }
            //如果入住时间晚上12：00之前还未入住，即为订单超时，更新信用值，记录信用值变化，设置订单状态
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
        //做出评价，成功返回true，失败返回评价失败
        try {
            orderMapper.makeEvaluation(orderId,evaluation);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(EVALUATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
