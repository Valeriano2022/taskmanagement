import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/useAuthStore'
import LoginView from '@/views/LoginView.vue'
import MainView from '@/views/MainView.vue'
import RegisterView from '@/views/RegisterView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      component: LoginView,
    },
    {
      path: '/board',
      component: MainView,
    },
    {
      path: '/signup',
      component: RegisterView,
    },
  ],
})

router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    return '/login'
  }

  if (auth.isAuthenticated && to.path === '/login') {
    return '/board'
  }
})

export default router
