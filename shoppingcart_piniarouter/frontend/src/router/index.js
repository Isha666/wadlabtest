import { createRouter, createWebHistory } from 'vue-router'
import ProductsPage from '../views/ProductsPage.vue'
import CartPage from '../views/CartPage.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: ProductsPage },
    { path: '/cart', component: CartPage }
  ]
})

export default router
