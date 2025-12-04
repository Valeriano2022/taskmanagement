<template>
  <AuthLayout>
    <SignupForm @signup="handleSignUp" />
    <p class="auth-link">
      Already have an account? <a href="#" @click.prevent="goToSignin">Sign In</a>
    </p>
  </AuthLayout>
</template>

<script setup lang="ts">
import AuthLayout from '@/layouts/AuthLayout.vue'
import SignupForm from '@/components/SignupForm.vue'
import { signUp } from '@/services/useAuthService'
import { useRouter } from 'vue-router'

const router = useRouter()

const handleSignUp = async (formData: { name: string; email: string; password: string }) => {
  try {
    await signUp({
      email: formData.email,
      password: formData.password,
    })

    router.push('/login')
  } catch (err) {
    console.error(err)
    alert('Signup failed')
  }
}

const goToSignin = () => {
  router.push('/login')
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
