import { axios } from '@/utils/request'
const api = {
    couponPre: '/api/coupon'
}
export function addCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/addCoupon`,
        method: 'POST',
        data,
    })
}

export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/${hotelId}/hotelAllCoupons`,
        method: 'GET',
    })
}

export function websiteAllCouponsAPI(){
    return axios({
        url: `${api.couponPre}/websiteAllCoupon`,
        method: 'GET',
    })
}

export function orderMatchCouponsAPI(params) {
    return axios({
        url: `${api.couponPre}/orderMatchCoupons`,
        method: 'GET',
        params,
    })
}

export function deleteCouponAPI(couponId){
    return axios({
        url: `${api.couponPre}/${couponId}/deleteCoupon`,
        method: 'POST'
    })
}