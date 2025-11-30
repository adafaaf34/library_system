import { createRouter, createWebHistory } from 'vue-router'
import Register from '../views/Register.vue'
import Login from '../views/Login.vue'
import BookList from '../views/BookList.vue'

const routes = [
  { path: '/', component: BookList },
  { path: '/register', component: Register },
  { path: '/login', component: Login }
]

const router = createRouter({ history: createWebHistory(), routes })
export default router
