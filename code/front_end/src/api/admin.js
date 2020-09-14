import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getUserListAPI(type){
    return axios({
        url: `${api.adminPre}/${type}/getUserList`,
        method: 'GET'
    })
}

export function addUserAPI(data) {
    return axios({
        url: `${api.adminPre}/addUser`,
        method: 'POST',
        data
    })
}

export function adminAddHotelAPI(data){
    return axios({
        url: `${api.adminPre}/addHotel`,
        method: 'POST',
        data
    })
}
export function updateHotelAPI(data){
    return axios({
        url: `${api.adminPre}/updateHotel`,
        method: 'POST',
        data
    })
}
export function updateUserInfoAPI(data){
    return axios({
        url: `${api.adminPre}/updateUserInfo`,
        method: 'POST',
        data
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.adminPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}
export function deleteHotelAPI(hotelId) {
    return axios({
        url:`${api.adminPre}/deleteHotel/${hotelId}`,
        method:'GET'
    })
}
export function deleteUserAPI(userId) {
    return axios({
        url:`${api.adminPre}/deleteUser/${userId}`,
        method:'GET'
    })
}
export function saveFileAPI(formData) {
    console.log(formData)
    return axios({
        url: `${api.adminPre}/saveFile`,
        method: 'POST',
        data: formData
    })
}
