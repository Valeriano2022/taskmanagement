<template>
  <div class="app-layout">
    <header class="app-topbar">
      <slot name="topbar"></slot>
    </header>

    <aside class="app-sidebar">
      <slot name="sidebar"></slot>
    </aside>

    <main class="app-main">
      <slot></slot>
    </main>
  </div>
</template>

<script setup lang="ts">
// No script needed here, just the layout structure
</script>

<style scoped>
.app-layout {
  display: grid;
  grid-template-columns: 250px 1fr; /* Sidebar width, Main content width */
  grid-template-rows: 60px 1fr; /* Topbar height */
  grid-template-areas:
    "sidebar topbar"
    "sidebar main";
  height: 100vh;
}

.app-topbar {
  grid-area: topbar;
  background-color: #3f51b5; /* Indigo */
  color: white;
  padding: 0 20px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #303f9f;
  z-index: 10;
}

.app-sidebar {
  grid-area: sidebar;
  background-color: #f5f5f5; /* Light grey */
  border-right: 1px solid #ddd;
  overflow-y: auto;
  padding: 10px 0;
}

.app-main {
  grid-area: main;
  background-color: #eee; /* Very light grey */
  overflow-y: auto;
  padding: 20px;
}
</style>