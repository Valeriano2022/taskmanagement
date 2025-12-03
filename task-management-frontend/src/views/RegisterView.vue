<template>
  <AuthLayout>
    <SignupForm @signup="handleSignUp" />
    <p class="auth-link">Already have an account? <a href="#" @click.prevent="goToSignin">Sign In</a></p>
  </AuthLayout>
</template>

<script setup lang="ts">
import AuthLayout from '@/layouts/AuthLayout.vue';
import SignupForm from '@/components/SignupForm.vue';

const handleSignUp = (formData) => {
    alert(`Signing Up user: ${formData.name} with Email: ${formData.email}`);
    // In a real app, this would route to SigninView or MainView on success
};

const goToSignin = () => {
    alert("Navigating to Signin View (simulated)");
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