import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}
export function getAllHotelOrdersAPI(hotelId) {
    return axios({
        url: `${api.orderPre}/${hotelId}/allOrders`,
        method: 'GET',
    })
}
export function getEvaluationsAPI(hotelId) {
    return axios({
        url: `${api.orderPre}/${hotelId}/evaluations`,
        method: 'GET',
    })
}
export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}
export function evaluateAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.orderId}/evaluate`,
        method: 'POST',
        data
    })
}
export function executeOrderAPI(orderId){
    return axios({
        url: `${api.orderPre}/${orderId}/executeOrder`,
        method: 'POST',
    })
}
export function checkOutOrderAPI(orderId){
    return axios({
        url: `${api.orderPre}/${orderId}/checkOutOrder`,
        method: 'POST',
    })
}
export function getExceptionOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getExceptionOrders`,
        method: 'GET',
    })
}
export function processExceptionOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/processExceptionOrder`,
        method: 'POST',
    })
}
export function appealOrderAPI(orderId){
    return axios({
        url: `${api.orderPre}/${orderId}/appealOrder`,
        method: 'POST'
    })
}
export function checkOrdersAPI() {
    return axios({
        url: `${api.orderPre}/processAllLateOrders`,
        method: 'POST'
    })
}
export function getOrderByIdAPI(orderId){
    return axios({
        url:`${api.orderPre}/${orderId}/getOrderById`,
        method: 'GET'
    })
}