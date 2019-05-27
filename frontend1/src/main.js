// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueRouter from 'vue-router'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Vuex from 'vuex'
import store from './store'

Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(iView)
Vue.use(Vuex)
Vue.use(VueAxios, axios)

// axios.defaults.withCredentials = true
/* eslint-disable */
new Vue({
  el: '#app',
  router: router,
  store: store,
  components: { App },
  template: '<App/>'
})
