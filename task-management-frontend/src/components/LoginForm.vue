<script setup lang="ts">
import { ref } from 'vue'
import type { LoginRequest } from '@/types/user'

const emit = defineEmits<{
  submit: [payload: LoginRequest]
}>()

const form = ref<LoginRequest>({
  email: '',
  password: '',
})

const handleSubmit = () => {
  if (!form.value.email.trim() || !form.value.password.trim()) return
  emit('submit', { ...form.value })
}
</script>

<template>
  <form @submit.prevent="handleSubmit" class="login-box">
    <h2 class="title">Welcome Back</h2>

    <div class="field">
      <label>Email</label>
      <input type="email" v-model="form.email" class="input-full" required />
    </div>

    <div class="field">
      <label>Password</label>
      <input type="password" v-model="form.password" class="input-full" required />
    </div>

    <button class="btn-primary submit-btn" type="submit">Login</button>

    <p class="note">Access your boards & tasks seamlessly</p>
  </form>
</template>

<style scoped>
input:-webkit-autofill,
input:-webkit-autofill:hover,
input:-webkit-autofill:focus {
  box-shadow: 0 0 0 1000px #ffffff inset !important;
  -webkit-text-fill-color: #2c3e50 !important;
  transition: background-color 5000s ease-in-out 0s;
}
.login-box {
  width: 100%;
  max-width: 380px;
  background: #ffffff;
  padding: 2.5rem;
  border-radius: 12px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  border: 1px solid #eee;
  display: flex;
  flex-direction: column;
}

.title {
  text-align: center;
  font-size: 1.6rem;
  margin-bottom: 1.5rem;
  color: #2c3e50;
  font-weight: 700;
}

.field {
  display: flex;
  flex-direction: column;
  margin-bottom: 1.2rem;
}

.field label {
  font-size: 0.9rem;
  margin-bottom: 6px;
  font-weight: 500;
  color: #34495e;
}

.input-full {
  box-sizing: border-box;
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
  font-family: inherit;
  transition:
    border-color 0.2s,
    box-shadow 0.2s;
}

.input-full:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 3px rgba(52, 152, 219, 0.4);
}

.submit-btn {
  margin-top: 10px;
  width: 100%;
  padding: 12px;
  font-size: 1rem;
  border-radius: 6px;
  font-weight: 600;
}

.note {
  margin-top: 1rem;
  text-align: center;
  color: #95a5a6;
  font-size: 0.9rem;
}
</style>
