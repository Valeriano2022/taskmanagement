<template>
  <li class="board-list-item" :class="{ 'is-active': props.isActive }">
    <span class="icon">📋</span>
    {{ props.board.name }}
  </li>
</template>

<script setup lang="ts">
const props = defineProps({
  board: { type: Object, required: true },
  isActive: { type: Boolean, required: true },
})
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
