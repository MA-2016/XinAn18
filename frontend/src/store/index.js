import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import user from './modules/user'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    apiPrefix: 'http://192.168.1.113:8001'
  },
  mutations: {},
  actions: {},
  modules: {
    app,
    user
  }
})

export default store
