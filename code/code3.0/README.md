# 酒店管理系统

## 作业说明
  1. 学习前端代码，完成策略相关用例的前端代码填充。
  2. 学习循环依赖，解决循环依赖问题。
  3. 学习策略模式，增加不同策略的实现。
  4. 学习后端实现流程，分别填补取消订单用例相关的controller,bl, blimpl,data,dataimpl相关缺失代码。


## 作业详情

### 前端代码填充要求
  1. 查看酒店策略列表：弹出模态框后填写策略列表，并获取到数据
  2. 添加策略：点击添加策略按钮，策略列表模态框消失，弹出添加策略，填写表单并完成添加
  3. 代码位置：

    template: /views/hotalManager/components/coupon.vue
              /views/hotalManager/components/addCoupon.vue
    store: /store/modules/hotelManager.js
  3. 效果参考图：/public目录下
  4. 本项目推荐使用antd UI组件库，依赖已经安装，具体学习使用参考官方文档：https://www.antdv.com/docs/vue/introduce-cn/

### 循环依赖
在代码中隐含着循环依赖，即HotelServiceImpl处的getHotelOrders方法。  
可以看到HotelService依赖了OrderService，同时在OrderService中，也依赖了HotelService实现了某些方法。  
造成这种错误的原因，主要是对getHotelOrders的方法所归属的功能模块不清晰导致的，获取酒店订单应属于订单服务的功能。  
该方法怎么改会更好呢？放到OrderService中会不会更好呢  
解决思路：  

1. 将HotelController中的retrieveHotelOrders相关方法移至OrderController中。
2. 将HotelService接口中的方法getHotelOrders移动至OrderService接口中。
3. 将相关实现类HotelServiceImpl的相关方法移动至OrderService的实现类中。

### 优惠券策略补充

目前已经实现的优惠券策略为满减优惠，需要同学们做的是支持节日特惠、多间优惠，实现CouponMatchStrategy接口中的isMatch方法。在实现时也需要考虑发放优惠券的主体包括酒店与网站两个主体。

### 取消订单填充
取消订单功能从Controller层中的annulOrder接口开始，需要同学们实现OrderServiceImpl类中的annulOrder方法（注意除了对order状态的修改之外还有可能涉及和别的业务的交互）和OrderMapper中取消订单的数据库操作。