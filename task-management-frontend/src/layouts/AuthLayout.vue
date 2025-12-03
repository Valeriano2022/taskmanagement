<template>
  <div class="auth-wrapper">
    <div class="auth-inner">
      <slot />
    </div>
  </div>
</template>

<style scoped>
.auth-wrapper {
  width: 100vw;
  height: 100vh;
  background: #f4f6f8;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.auth-inner {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 500px;
  padding: 1rem;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
