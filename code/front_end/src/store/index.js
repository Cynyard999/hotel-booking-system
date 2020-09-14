import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import hotel from './modules/hotel'
import hotelManager from './modules/hotelManager'
import admin from './modules/admin'
import saleMen from './modules/saleMen'
import getters from './getters'
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    hotel,
    user,
    hotelManager,
    saleMen,
    admin,
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})
