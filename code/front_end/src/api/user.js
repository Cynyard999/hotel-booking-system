import { axios } from '@/utils/request'

const api = {
    userPre: '/api/user'
}
export function loginAPI(data){
    return axios({
        url:`${api.userPre}/login`,
        method: 'POST',
        data
    })
}
export function registerAPI(data){
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}

export function getVipInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getVipInfo`,
        method: 'GET'
    })
}

export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}
export function registerVipAPI(data) {
    if (data.vipType == 1){
        return axios({
            url: `${api.userPre}/${data.id}//userInfo/registerPersonalVIP`,
            method: 'POST',
            data
        })
    }
    else{
        return axios({
            url: `${api.userPre}/${data.id}/userInfo/registerCompanyVIP`,
            method: 'POST',
            data
        })
    }
}

export function chargeCreditAPI(email, credit){
    return axios({
        url: `${api.userPre}/${email}/${credit}/chargeCredit`,
        method: 'POST'
    })
}
