<template>
  <form class="auth-form" @submit.prevent="submitForm">
    <h3>Create Your Account</h3>

    <div class="form-group">
      <label for="name">Full Name</label>
      <input type="text" id="name" v-model="name" required />
    </div>

    <div class="form-group">
      <label for="email">Email</label>
      <input type="email" id="email" v-model="email" required />
    </div>

    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" id="password" v-model="password" required />
    </div>

    <button type="submit" class="btn-auth">Sign Up</button>
  </form>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { SignupRequest } from '@/types/user'

const emit = defineEmits<{
  (e: 'signup', data: SignupRequest & { name: string }): void
}>()

const name = ref('')
const email = ref('')
const password = ref('')

const submitForm = () => {
  emit('signup', {
    name: name.value,
    email: email.value,
    password: password.value,
  })
}
</script>

<style scoped>
/* Inherit styles from SigninForm.vue */
.auth-form h3 {
  margin-top: 0;
  margin-bottom: 20px;
  text-align: center;
}
.form-group {
  margin-bottom: 15px;
}
label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}
input[type='text'],
input[type='email'],
input[type='password'] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
.btn-auth {
  width: 100%;
  background-color: #3f51b5; /* Indigo */
  color: white;
  border: none;
  padding: 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1.1em;
  margin-top: 10px;
}
</style>
