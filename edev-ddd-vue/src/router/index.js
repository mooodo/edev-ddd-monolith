import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import CustomerGrid from '@/components/CustomerGrid'
import ProductGrid from '@/components/ProductGrid'
import OrderGrid from '@/components/OrderGrid'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/customer',
      name: 'CustomerGrid',
      component: CustomerGrid
    },
    {
      path: '/product',
      name: 'ProductGrid',
      component: ProductGrid
    },
    {
      path: '/order',
      name: 'OrderGrid',
      component: OrderGrid
    }
  ]
})
