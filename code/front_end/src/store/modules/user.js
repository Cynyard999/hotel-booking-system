import Vue from 'vue'
import router from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    registerVipAPI,
    getVipInfoAPI
} from '@/api/user'

import {
    favorHotelAPI,
    retrieveFavoriteListAPI,
    disfavorHotelAPI,
    isFavoredAPI
} from '@/api/favorite'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
    appealOrderAPI,
    checkOrdersAPI,
    getOrderByIdAPI,
    evaluateAPI
} from '@/api/order'
import {
    rateAPI
} from '@/api/hotel'

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {

        },
        vipInfo:{

        },
        userOrderList: [

        ],
        favList:[

        ],
        favHotel:'',
        favListParams: {
            pageNo: 0,
            pageSize: 12
        },
        addSuccess:'',
        deleteSuccess:'',
        userOrderDetailVisible:false,
        userOrderDetail:{},
        orderId:0,
        evaluatedHotelName:'',
        makeEvaluationVisible:false,
        evaluatedHotelId:''
    }
}

const user = {
    state : getDefaultState(),

    mutations: {
        reset_state: function(state) {
            state.token = '',
            state.userId = '',
            state.userInfo = {},
            state.vipInfo = {},
            state.userOrderList = []
        },
        set_token: function(state, token){
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_vipInfo:(state,data)=>{
            state.vipInfo = data
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_favHotel: (state, data) => {
            state.favHotelId = data
        },
        set_favList: (state, data) => {
            state.favList = data
        },
        set_favListLoading: function(state, data) {
            state.favListLoading = data
        },
        set_addSuccess:function(state,data){
            state.addSuccess = data
        },
        set_deleteSuccess:function(state,data){
            state.deleteSuccess = data
        },
        set_favListParams: function(state, data) {
            state.favListParams = {
                ...state.favListParams,
                ...data,
            }
        },
        set_userOrderDetailVisible: function (state,data) {
            state.userOrderDetailVisible = data
        },
        set_userOrderDetail: function (state, data) {
            state.userOrderDetail = data
        },
        set_orderId: function(state, data){
            state.orderId = Number(data)
        },
        set_makeEvaluationVisible:function (state,data) {
            state.makeEvaluationVisible = data
        },
        set_evaluatedHotelName:function (state,data) {
            state.evaluatedHotelName = data
        },
        set_evaluatedHotelId:function (state,data) {
            state.evaluatedHotelId = data
        }
    },

    actions: {
        getIsFavored:async ({commit,state},hotelId) =>{
            const res = await isFavoredAPI(state.userId,hotelId)
            return res > 0;
        },
        getFavList: async({commit, state}) => {
            const res = await retrieveFavoriteListAPI(state.userId)
            if(res){
                commit('set_favList', res)
                commit('set_favListLoading', false)
            }
        },
        favorHotel: async({commit, state}) => {
            const params = {
                "userId":state.userId,
                "hotelId":state.favHotelId
            };
            const res = favorHotelAPI(params)
            if(res){
                commit('set_addSuccess',true)
            }
            else {
                commit('set_addSuccess',false)
            }
        },
        disfavorHotel: async({commit, state}) => {
            const params = {
                "userId":state.userId,
                "hotelId":state.favHotelId
            };
            const res = disfavorHotelAPI(params)
            if(res){
                commit('set_deleteSuccess',true)
            }
            else {
                commit('set_deleteSuccess',false)
            }
        },
        login: async ({ dispatch, commit}, userData) => {
            const res = await loginAPI(userData)
            if(res){
                setToken(res.id)
                commit('set_userId', res.id)
                dispatch('getUserInfo')
                if(res.userType=="HotelManager"){
                    router.push('/hotelManager/manageHotel/'+res.id)
                }
                else if(res.userType=="Manager"){
                    router.push('/admin/manageUser')
                }
                else if(res.userType=="SaleMen"){
                    router.push('/saleMen/manageWebsite')
                }
                else{
                    router.push('/hotel/hotelList')
                }

            }
        },
        register: async({ commit }, data) => {
            const res = await registerAPI(data)
            if(res){
                if(res=="邮箱已被注册"){message.error("邮箱已存在")}
                else if(res=="手机号已被注册"){message.error("手机号已存在")}
                else {
                    message.success('注册成功')
                }
            }
        },
        getVipInfo: async({commit, state}) => {
            const res = await getVipInfoAPI(state.userId)
            if(res){
                commit('set_vipInfo', res)
            }
        },

        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
              getUserInfoAPI(state.userId).then(response => {
                const data = response
                if (!data) {
                  reject('登录已过期，请重新登录')
                }
                commit('set_userInfo', data)
                commit('set_userId', data.id)
                resolve(data)
              }).catch(error => {
                reject(error)
              })
            })
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        registerVip:async ({state,dispatch},data)=>{
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await registerVipAPI(params)
            if(res){
                message.success('注册成功')
            }
        },
        getUserOrders: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserOrdersAPI(data)
            if(res){
                commit('set_userOrderList', res)
                console.log(state.userOrderList)
            }
        },
        cancelOrder: async({ state, dispatch }, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if(res) {
                dispatch('getUserOrders')
                dispatch('getUserInfo')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
          // remove token
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
        appealOrder: async({state, dispatch}, orderId) => {
            const res = await appealOrderAPI(orderId)
            if(res) {
                dispatch('getUserOrders')
                message.success('提交申诉成功')
            } else {
                message.error('提交申诉失败')
            }
        },
        checkAllOrders: async({state,commit}) =>{
            const res = checkOrdersAPI()
        },
        getOrderById: async({state,commit})=>{
            const res = await getOrderByIdAPI(state.orderId)
            if(res){
                commit('set_userOrderDetail', res)
            }
        },
        evaluate:async ({state,dispatch},evaluation)=>{
            const data = {
                orderId: Number(state.orderId),
                evaluation:evaluation
            }
            const res = await evaluateAPI(data)
            if(res){
                dispatch('getUserOrders')
                return true
            }
            return false
        },
        rate:async ({state,dispatch},rate)=>{
            const data = {
                hotelId: Number(state.evaluatedHotelId),
                rate:rate
            }
            const res = await rateAPI(data)
            if(res){
                return true
            }
            return false
        },
    }
}

export default user