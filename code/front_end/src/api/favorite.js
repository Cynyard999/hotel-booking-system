import {axios} from '@/utils/request'

const api = {
  favorPre: '/api/favorite'
}

export function favorHotelAPI(data) {
  return axios({
    url: `${api.favorPre}/favorHotel`,
    method: 'POST',
    data,
  })
}

export function retrieveFavoriteListAPI(id) {
  return axios({
    url: `${api.favorPre}/${id}/retrieveFavoriteList`,
    method: 'GET',
  })
}

export function disfavorHotelAPI(data) {
  return axios({
    url: `${api.favorPre}/disfavorHotel`,
    method: 'POST',
    data,
  })
}


export function isFavoredAPI(userId,hotelId) {
  return axios({
    url: `${api.favorPre}/${userId}/${hotelId}/isFavored`,
    method: 'GET',
  })
}
