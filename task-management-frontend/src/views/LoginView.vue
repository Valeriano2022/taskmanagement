<template>
  <AuthLayout>
    <SigninForm @signin="handleSignIn" />
    <p class="auth-link">Don't have an account? <a href="#" @click.prevent="goToSignup">Sign Up</a></p>
  </AuthLayout>
</template>

<script setup lang="ts">
import AuthLayout from '@/layouts/AuthLayout.vue';
import SigninForm from '@/components/SigninForm.vue';

const handleSignIn = (credentials) => {
    alert(`Signing In with Email: ${credentials.email}`);
    // In a real app, this would route to MainView on success
};

const goToSignup = () => {
    alert("Navigating to Signup View (simulated)");
    // In a real app, you would use a router (e.g., Vue Router) to change the view
};
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