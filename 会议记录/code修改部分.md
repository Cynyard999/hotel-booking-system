# 函数修改

对于函数名、参数和返回值无变化的不再赘述，下面只列举修改了以上三点的：

- Admin

  - ```
    updateHotel--》updateManagerOfHotel
    在con、bl、blimpl、data、mapper、test里均修改
    原因：此处功能为给酒店添加酒店工作人员，故updateHotel不妥切与hotel的信息更新重复了
    ```
    
  - ```
    ResponseVO getUserInfo变为UserVO getUserInfo
    原因：get请求有数据返回
    ```

- hotel

  - ```
    void updateRoomInfo(Integer hotelId, String roomType,Integer rooms)参数错误，改为
    ResponseVO updateRoomInfo(Integer hotelId, Integer roomId,Integer rooms)
    原因：严重错误，我的，修改房间信息应该根据roomid而不是type，否则会更改多个房间信息！！
    原因：虽然没有在controller调用，但是仍需要有返回值
    ```

- room

  - ```
    void insertRoomInfo变为ResponseVO insertRoomInfo
    理由：不能为void
    ```

  - ```
    void updateRoomInfo(Integer hotelId, String roomType,Integer rooms) 变为 
    ResponseVO updateRoomInfo(Integer hotelId, Integer roomId,Integer rooms)
    这里相关的bl，blimpl、mapper、data都修改了（hotel中的updateRoomInfo调用了room中的updateRoomInfo）
    ```

- order

  - ```
    getUserOrders(int userid)--》getUserOrders(int userId);【变量命名规则，以下同理，就不写原来的“id”了】
    annulOrder(int orderId);
    getOrderById(int orderId);
    executeOrder(int orderId);
    checkOutOrder(int orderId);
    ```

  - ```
    Order getOrderById(int orderId);变为OrderVO getOrderById(int orderId);
    理由：返回VO，在业务逻辑层转换
    ```

- user

  - ```
    User login变为UserVO login
    理由：返回VO，业务逻辑层转换
    ```

  - ```
    User getUserInfo变为UserVO getUserInfo
    理由：返回VO，业务逻辑层转换
    ```

  - ```
    Vip getVipInfo这里本来要改来着，但是邱哥VO里用两个派生类继承了VIPVO，而且把转换写在con里很杂乱，明天看一下咋说
    ```

# 其他问题

- 信用值记录后端实现了，前端没有调用API，无显示【bl层 getUserCreditRecord】
- 根据时间判断并自动将超时订单设置为异常的功能后端实现了，但是前端没有调用【controller层 processAllLateOrders】
- 

