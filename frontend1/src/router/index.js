import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login/Login'
import AttackOverview from '@/components/home/attack-overview/AttackOverview'
import Home from '@/components/home/Home'
import AttackRealtime from '@/components/home/attack-realtime/AttackRealtime'
import AttackDetector from '@/components/home/attack-detector/AttackDetector'
import Register from '@/components/login/Register'
import AttackProtect from '@/components/home/attack-protect/AttackProtect'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      children: [
        {
          path: 'attack-realtime',
          component: AttackRealtime
        },
        {
          path: 'attack-overview',
          component: AttackOverview
        },
        {
          path: 'attack-detector',
          component: AttackDetector
        },
        {
          path: 'attack-protect',
          component: AttackProtect
        }
      ]
    }
  ]
})
