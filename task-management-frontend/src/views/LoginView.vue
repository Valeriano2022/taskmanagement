<template>
  <AuthLayout>
    <SigninForm @signin="handleSignIn" />
    <p class="auth-link">
      Don't have an account? <a href="#" @click.prevent="goToSignup">Sign Up</a>
    </p>
  </AuthLayout>
</template>

<script setup lang="ts">
import AuthLayout from '@/layouts/AuthLayout.vue'
import SigninForm from '@/components/SigninForm.vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/useAuthStore'
import { logIn } from '@/services/useAuthService'

const router = useRouter()
const auth = useAuthStore()

const handleSignIn = async (payload: { email: string; password: string }) => {
  const res = await logIn({
    email: payload.email,
    password: payload.password,
  })

  auth.login({
    user: res.user,
    accessToken: res.accessToken,
    refreshToken: res.refreshToken,
  })

  router.push('/board')
}

const goToSignup = () => {
  router.push('/signup')
}
</script>

<style scoped>
.auth-link {
  text-align: center;
  margin-top: 20px;
  color: #555;
}
.auth-link a {
  color: #3f51b5;
  text-decoration: none;
}
</style>
