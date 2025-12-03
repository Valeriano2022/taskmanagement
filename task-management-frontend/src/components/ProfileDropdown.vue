<template>
  <div class="profile-dropdown">
    <button class="profile-btn" @click="isOpen = !isOpen">
      <span class="initials">JD</span>
    </button>
    <div v-if="isOpen" class="dropdown-menu">
      <div class="user-info">
        <p>John Doe</p>
        <small>john.doe@example.com</small>
      </div>
      <button class="menu-item">My Profile</button>
      <button class="menu-item">Settings</button>
      <hr>
      <button class="menu-item logout">Logout</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const isOpen = ref(false);
</script>

<style scoped>
.profile-dropdown {
  position: absolute;
  right: 20px;
  z-index: 20;
}
.profile-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #ffc107; /* Amber Light */
  color: #333;
  font-weight: bold;
  border: none;
  cursor: pointer;
}
.initials {
  display: block;
  line-height: 40px;
  text-align: center;
}
.dropdown-menu {
  position: absolute;
  top: 50px;
  right: 0;
  background: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  width: 200px;
  overflow: hidden;
}
.user-info {
  padding: 10px;
  border-bottom: 1px solid #eee;
}
.menu-item {
  display: block;
  width: 100%;
  padding: 10px;
  background: none;
  border: none;
  text-align: left;
  cursor: pointer;
}
.menu-item:hover {
  background-color: #f5f5f5;
}
.logout {
  color: #f44336; /* Red */
}
</style>