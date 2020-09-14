import { message } from 'ant-design-vue'
import store from '@/store'
import {
    getExceptionOrdersAPI,
    processExceptionOrderAPI,
} from "@/api/order";
import {
    chargeCreditAPI
} from "@/api/user";
import {
    websiteAllCouponsAPI,
    addCouponAPI,
    deleteCouponAPI,
} from "@/api/coupon";

const getDefaultState = () =>{
    return {
        websiteCouponList: [],
        exceptionOrderList: [],
        activeOrderId: 0,
        activeEmailAddress: "",
        activeCredit: 0,
        addWebsiteCouponModalVisible: false,
        addWebsiteCouponParams:{
            couponName: "",
            description:"",
            couponType: 0,
            hotelId: -1,
            status: 1,
            discount: 0,
            roomNum:null,
        }
    }
}
const saleMen = {
    state: getDefaultState(),
    mutations:{
        set_websiteCouponList:function(state, data){
            state.websiteCouponList = data
        },
        set_exceptionOrderList:function (state, data) {
            state.exceptionOrderList = data
        },
        set_activeOrderId: function (state, data) {
            state.activeOrderId = data
        },
        set_activeEmailAddress: function(state, data){
            state.activeEmailAddress = data
        },
        set_activeCredit: function(state, data){
            state.activeCredit = data
        },
        set_addWebsiteCouponModalVisible: function(state, data){
            state.addWebsiteCouponModalVisible = data;
        },
        set_couponName: function(state,data){
            state.addWebsiteCouponParams.couponName = data
        },
        set_couponDescription: function(state,data){
            state.addWebsiteCouponParams.description = data
        },
        set_couponType: function(state,data){
            state.addWebsiteCouponParams.couponType = data
        },
        set_couponDiscount: function(state,data){
            state.addWebsiteCouponParams.discount = data
        },
        set_couponRoomNum: function(state,data){
            state.addWebsiteCouponParams.roomNum = data
        }
    },
    actions:{
        getWebsiteCoupons: async({state, commit})=>{
            const res = websiteAllCouponsAPI()
            if(res){
                res.then(data=>{
                    commit('set_websiteCouponList',data)
                })
            }
        },
        getAllExceptionOrders: async ({state, commit})=>{
            const res = await getExceptionOrdersAPI()
            if(res){
                commit('set_exceptionOrderList',res)
            }
        },
        processExceptionOrder: async ({state,dispatch,commit})=>{
            const res = await processExceptionOrderAPI(state.activeOrderId)
            if(res){
                dispatch('getAllExceptionOrders')
                message.success("申诉成功")
            } else{
                message.error("申诉失败")
            }
        },
        chargeCredit: async({state,dispatch,commit})=>{
            const res = await chargeCreditAPI(state.activeEmailAddress, state.activeCredit)
            if(res){
                message.success("充值成功")
            } else {
                message.success("充值失败")
            }
            commit('set_activeEmailAddress', "")
            commit('set_activeCredit', 0)
        },
        addWebsiteCoupon: async({state,dispatch,commit})=>{
            const res = await addCouponAPI(state.addWebsiteCouponParams)
            if(res){
                dispatch('getWebsiteCoupons')
                commit("set_addWebsiteCouponModalVisible", false)
                message.success("添加成功")
            }
            else{
                message.error("添加失败")
            }
        },
        deleteWebsiteCoupon: async({state,dispatch,commit},couponId)=>{
            const res = await deleteCouponAPI(couponId)
            if(res){
                dispatch('getWebsiteCoupons')
                message.success("删除成功")
            }
        }
    }
}
export default saleMen