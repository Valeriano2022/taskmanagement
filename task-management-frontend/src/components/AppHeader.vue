<template>
  <div class="app-header">
    <h1 class="logo">TaskMaster</h1>
    <div class="right-section">
      <ProfileDropdown />
    </div>
  </div>
</template>

<script setup lang="ts">
import ProfileDropdown from '@/components/ProfileDropdown.vue'
</script>

<style scoped>
.app-header {
  /* REMOVE all header-specific backgrounds/padding */
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%; /* Let it fill AppLayout topbar slot */
}

.logo {
  font-size: 1.5em;
  font-weight: 700;
  margin: 0;
}

.right-section {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>
