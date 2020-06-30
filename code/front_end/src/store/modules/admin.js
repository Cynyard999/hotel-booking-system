import {
    getUserListAPI,
    addUserAPI,
    adminAddHotelAPI,
    deleteHotelAPI,
    updateHotelAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    deleteUserAPI,
    saveFileAPI,
} from '@/api/admin'
import { message } from 'ant-design-vue'
import addSaleMenModel from "../../views/admin/components/addSaleMenModel";
import {updateLocale} from "moment";

const admin = {
    state: {
        managerList: [

        ],
        clientList:[

        ],
        saleMenList:[

        ],
        updateHotelParams:{
            id: 0,
            managerId : 0,
        },
        addManagerModelVisible: false,
        addSaleMenModelVisible: false,
        adminAddHotelModelVisible: false,
        addHotelPhotoVisible : false,
        updateHotelId: 0,
        managerId: 0,
        photoHotelId:0,
        deleteHotelId:0,
        deleteUserId:0,
        addManagerParams: {
            email:'',
            password:''
        },
        addSaleMenParams: {
            email:'',
            password:''
        },
        adminAddHotelParams: {
            name: '',
            address: '',
            bizRegion:'XiDan',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
        },
        checkUserInfo:{

        },
        checkUserId:0,
    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_clientList: function(state, data) {
            state.clientList = data
        },
        set_saleMenList: function(state, data) {
            state.saleMenList = data
        },
        set_addManagerModelVisible: function(state, data) {
            state.addManagerModelVisible = data
        },
        set_addSaleMenModelVisible: function(state, data) {
            state.addSaleMenModelVisible = data

        },
        set_addHotelPhotoVisible: function(state, data) {
            state.addHotelPhotoVisible = data
        },
        set_checkUserId: function(state, data) {
            state.checkUserId = data
        },
        set_photoHotelId:function(state,data){
            state.photoHotelId = data
        },
        set_deleteHotelId:function(state,data){
            state.deleteHotelId = data
        },
        set_deleteUserId:function(state,data){
            state.deleteUserId = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        set_checkUserInfo: function(state, data) {
            state.checkUserInfo = {
                ...state.checkUserInfo,
                ...data,
            }
        },
        set_updateHotelParams: function(state, data) {
            state.updateHotelParams = {
                ...state.updateHotelParams,
                ...data,
            }
        },
        set_addSaleMenParams: function(state, data) {
            state.addSaleMenParams = {
                ...state.addSaleMenParams,
                ...data,
            }

        },
        set_adminAddHotelParams: function(state, data) {
            state.adminAddHotelParams = {
                ...state.adminAddHotelParams,
                ...data,
            }
        },
        set_adminAddHotelModelVisible: function(state, data) {
            state.adminAddHotelModelVisible = data
        },
        set_updateHotelId : function (state,data) {
            state.updateHotelId = data
        },
        set_managerlId : function (state,data) {
            state.managerId = data
        },
    },
    actions: {
        getManagerList: async({ commit }) => {
            const res = await getUserListAPI('HotelManager')
            if(res){
                commit('set_managerList', res)
            }
        },
        getClientList: async({ commit }) => {
            const res = await getUserListAPI("Client")
            if(res){
                commit('set_clientList', res)
            }
        },
        getSaleMenList: async({ commit }) => {
            const res = await getUserListAPI("SaleMen")
            if(res){
                commit('set_saleMenList', res)
            }
        },
        deleteHotel:async({state, commit})=>{
            const res = await deleteHotelAPI(state.deleteHotelId)
            if(res){
                commit("set_deleteHotelId",0)
                console.log("删除成功")
            }

        },
        deleteUserById:async({state,commit})=>{

            const res = await deleteUserAPI(state.deleteUserId)
            if(res){
                commit('set_deleteUserId',0);
                console.log("删除用户成功")
            }
        },

        adminAddHotel:async({ state, dispatch, commit }) => {
            const res = await adminAddHotelAPI(state.adminAddHotelParams)
            if(res){
                dispatch('getHotelList')
                commit('set_adminAddHotelParams', {
                    name: '',
                    address: '',
                    bizRegion:'',
                    hotelStar:'',
                    rate: 0,
                    description:'',
                    phoneNum:'',

                })
                commit('set_adminAddHotelModelVisible', false)
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        addManager: async({ state, commit, dispatch }) => {
            const res = await addUserAPI(state.addManagerParams)
            console.log(res)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:'',
                    userName:'',
                    phoneNumber:'',
                    userType:'HotelManager'
                })

                commit('set_managerlId',res.toString())
                commit('set_addManagerModelVisible', false)
                message.success('添加工作人员成功')
                dispatch('updateHotel')
            }else{
                message.error('添加失败')
            }
        },
        addSaleMen: async({ state, commit, dispatch }) => {
            const res = await addUserAPI(state.addSaleMenParams)
            console.log(res)
            if(res) {
                commit('set_addSaleMenParams',{
                    email:'',
                    password:'',
                    userType:'SaleMen',
                    phoneNumber:'',
                    userName:'',
                })
                commit('set_addSaleMenModelVisible', false)
                message.success('添加成功')

                dispatch('getSaleMenList')
            }else{
                message.error('添加失败')
            }
        },
        updateHotel: async ({state,commit,dispatch}) =>{
            commit('set_updateHotelParams',{
                id:state.updateHotelId,
                managerId:state.managerId
            })
            // console.log(state.updateHotelId)
            // console.log(state.managerId)
            // console.log("到酒店这里了")
            // console.log(state.updateHotelParams)
            const res = await updateHotelAPI(state.updateHotelParams)
            if(res){
                dispatch('getHotelList')
            }else{
                message.error('添加酒店失败')
            }
        },
        getCheckUserInfo:async({state,commit})=>{
            const res = await getUserInfoAPI(state.checkUserId)
            console.log(res)
            if(res){
                commit('set_checkUserInfo',res)

            }
            else{
                message.error("查询失败")
            }
        },
        updateCheckUserInfo :async({state, commit,dispatch},data)=>{

            console.log(state.checkUserInfo)
            const params={
                ...state.checkUserInfo,
                ...data
            }

            const res = await updateUserInfoAPI(params)
            if(res){
                message.success("修改成功")
                dispatch('getCheckUserInfo')
            }else{
                message.error("修改信息失败")
            }
        },


    }
}
export default admin