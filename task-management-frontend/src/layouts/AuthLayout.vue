<template>
  <div class="auth-layout">
    <header class="auth-header">
      <h1>TaskMaster</h1>
    </header>
    <div class="auth-content">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
// Layout component
</script>

<style scoped>
.auth-layout {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 20px;
  background-color: #f0f2f5;
}

.auth-header {
  margin-bottom: 30px;
}
.auth-header h1 {
  color: #3f51b5; /* Indigo */
  font-size: 2.5em;
}

.auth-content {
  background: white;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}
</style>
