import { message } from 'ant-design-vue'
import {
    getHotelsAPI,
    getHotelByIdAPI
} from '@/api/hotel'
import {
    reserveHotelAPI,
    getEvaluationsAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
    hotelAllCouponsAPI
} from '@/api/coupon'

const hotel = {
    state: {
        hotelList: [],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {},
        orderModalVisible: false,
        currentOrderRoom: {

        },
        orderMatchCouponList: [

        ],
        hotelCouponList:[],
        evaluations:[]
    },
    mutations: {
        set_evaluations:function(state,data){
            state.evaluations = data.reverse()
        },
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_hotelListParams: function(state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function(state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function(state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function(state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function(state, data) {
            state.orderModalVisible = data
        },
        set_currentOrderRoom: function(state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function(state, data) {
            state.orderMatchCouponList = data
        },
        set_hotelCouponList: function(state, data) {
            state.hotelCouponList = data
        },
    },

    actions: {
        getEvaluations: async({ state, commit }) => {
            const res = await getEvaluationsAPI(state.currentHotelId)
            if(res){
                commit('set_evaluations', res)
            }
        },
        getHotelList: async({commit, state}) => {
            const res = await getHotelsAPI()
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelById: async({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async({ state, dispatch, commit , rootGetters}, data) => {
            const res = await reserveHotelAPI(data)
            console.log(res)
            if(res){
                message.success('预定成功')
                commit('set_orderModalVisible', false)
                dispatch('getHotelById') // 调用本模块的 actions
                dispatch('getHotelByManagerId', {}, {root: true}) // 调用hotelManager模块的 actions
                dispatch('getAllHotelOrders', {}, {root: true}) // 调用hotelManager模块的 actions
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        getHotelCouponList: async({ state, commit},data) => {
            const res = await hotelAllCouponsAPI(data)
            if(res){
                commit('set_hotelCouponList', res)
            }
        },

    }
}

export default hotel