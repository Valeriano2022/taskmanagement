<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/useAuthStore'
import { type LoginRequest } from '@/types/user'
import { logIn } from '@/services/useAuthService'
import LoginForm from '@/components/LoginForm.vue'
import AuthLayout from '@/layouts/AuthLayout.vue'

const router = useRouter()
const auth = useAuthStore()

const handleLogin = async (data: LoginRequest) => {
  try {
    const response = await logIn(data)
    auth.login(response)

    router.push('/board')
  } catch (e) {
    console.error('Login failed', e)
    alert('Invalid email or password')
  }
}
</script>

<template>
  <AuthLayout>
    <LoginForm @submit="handleLogin" />
  </AuthLayout>
</template>
