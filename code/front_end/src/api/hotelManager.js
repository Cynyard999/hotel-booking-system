import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/addRoomInfo`,
        method: 'POST',
        data,
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function getHotelByManagerIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.managerId}/managerHotelDetail`,
        method: 'GET',
    })
}
export function updateHotelInfoAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.hotelId}/hotelInfo/update`,
        method: 'POST',
        data
    })
}


