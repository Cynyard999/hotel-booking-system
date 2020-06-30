import {
    addRoomAPI,
    getHotelByManagerIdAPI,
    updateHotelInfoAPI
} from '@/api/hotelManager'
import {
    getAllHotelOrdersAPI,
    executeOrderAPI,
    checkOutOrderAPI,
} from '@/api/order'
import {
    addCouponAPI,
    hotelAllCouponsAPI,
    deleteCouponAPI,
} from '@/api/coupon'
import { message } from 'ant-design-vue'

const hotelManager = {
    state: {
        managerId: '',
        orderList: [],
        addRoomParams: {},
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        managerHotelId: '',
        managerHotelInfo: {},
        couponList: [],
        addCouponParams:{
          description: '',
          hotelId: 0,
          couponType: 0,
          couponName: null,
          roomNum: null,
          targetMoney: null,
          discount: null,
          discountMoney: null,
          startTime: null,
          endTime: null,
          status: 1,
        },
        couponDetailVisible:false,
        couponDetail:{
        },
    },
    mutations: {
        set_managerId: function(state,data){
          state.managerId = data
        },
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_managerHotelId: function(state, data) {
            state.managerHotelId = data
        },
        set_managerHotelInfo: function(state, data) {
            state.managerHotelInfo = {
                ...state.managerHotelInfo,
                ...data,
            }
        },
        set_couponList: function(state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible =data
        },
        set_couponType: function(state,data){
            state.addCouponParams.couponType = data
        },
        set_couponName: function(state, data){
            state.addCouponParams.couponName = data
        },
        set_couponDescription: function(state, data){
            state.addCouponParams.description = data
        },
        set_couponRoomNum: function(state,data){
            state.addCouponParams.roomNum = data
        },
        set_couponTargetMoney: function(state,data){
            state.addCouponParams.targetMoney = data
        },
        set_couponDiscountMoney: function(state,data){
            state.addCouponParams.discountMoney = data
        },
        set_couponDiscount: function(state,data){
            state.addCouponParams.discount = data
        },
        set_couponStartTime: function(state, data){
            state.addCouponParams.startTime = data
        },
        set_couponEndTime:function(state,data){
            state.addCouponParams.endTime = data
        },
        set_couponHotelId:function(state,data){
            state.addCouponParams.hotelId = data
        },
        set_couponDetailVisible: function (state,data) {
            state.couponDetailVisible = data
        },
        set_couponDetail: function (state, data) {
            state.couponDetail = data
        },
    },
    actions: {
        getAllHotelOrders: async({ state, commit }) => {
            const res = await getAllHotelOrdersAPI(state.managerHotelId)
            if(res){
                commit('set_orderList', res)
            }
        },
        getHotelByManagerId: async({commit, state}) => {
            const res = await getHotelByManagerIdAPI({
                managerId: state.managerId
            })
            console.log("managerHotelInfo",res)
            if(res){
                commit('set_managerHotelId',res.id)
                commit('set_managerHotelInfo', res)
            }
        },
        updateHotelInfo: async({ state, dispatch }, data) => {
            const params = {
                hotelId: state.managerHotelId,
                ...data,
            }
            const res = await updateHotelInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getHotelByManagerId')
            }
        },
        addRoom: async({ state, dispatch, commit }) => {
            const res = await addRoomAPI(state.addRoomParams)
            if(res){
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {})
                message.success('添加成功')
                dispatch('getHotelByManagerId')
            }else{
                message.error('添加失败')
            }
        },
        getHotelCoupon: async({state,commit})=>{
            const res = await hotelAllCouponsAPI(Number(state.managerHotelId))
            if(res){
                commit('set_couponList', res)
            }
        },
        addHotelCoupon: async({state,dispatch,commit})=>{
            console.log(state.addCouponParams)
            const res = await addCouponAPI(state.addCouponParams)
            console.log(res)
            if(res){
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                message.success("添加成功")
            } else{
                message.error("添加失败")
            }
        },
        deleteHotelCoupon: async ({state,dispatch,commit}, couponId)=>{
            const res = await deleteCouponAPI(couponId)
            if(res){
                dispatch('getHotelCoupon')
                message.success("删除成功")
            }
            else{
                message.error("删除失败")
            }
        },
        executeOrder: async({state,dispatch,commit},orderId)=>{
            const res = await executeOrderAPI(orderId)
            if(res){
                dispatch('getAllHotelOrders',Number(state.managerHotelId))
                message.success("入住成功")
            }
            else{
                message.error("入住失败")
            }
        },
        checkoutOrder: async({state,dispatch,commit},orderId)=>{
            const res = await checkOutOrderAPI(orderId)
            if(res){
                dispatch('getAllHotelOrders',Number(state.managerHotelId))
                message.success("退房成功")
            }
            else{
                message.error("退房失败")
            }
        }
    }

}
export default hotelManager