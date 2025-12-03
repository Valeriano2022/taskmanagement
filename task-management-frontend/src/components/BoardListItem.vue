<template>
  <li class="board-list-item" :class="{ 'is-active': board.id === activeBoardId }">
    <span class="icon">📋</span> 
    {{ board.name }}
  </li>
</template>

<script setup lang="ts">
import { computed } from 'vue';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const props = defineProps({
  board: {
    type: Object,
    required: true
  }
});

// A simple way to simulate active state based on the current board in MainView
// In a real app, this prop would be passed down from MainView
const activeBoardId = computed(() => {
    // For demonstration, let's assume the first board is active
    return 1; 
});
</script>

<style scoped>
.board-list-item {
  padding: 10px 15px;
  margin: 5px 0;
  border-radius: 0 50px 50px 0;
  cursor: pointer;
  transition: background-color 0.2s;
  display: flex;
  align-items: center;
  font-weight: 500;
  color: #333;
}

.board-list-item:hover {
  background-color: #e0e0e0; /* Grey hover */
}

.board-list-item.is-active {
  background-color: #3f51b5; /* Indigo */
  color: white;
  font-weight: bold;
}

.board-list-item.is-active:hover {
  background-color: #303f9f;
}

.icon {
  margin-right: 10px;
}
</style>