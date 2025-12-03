<template>
  <div class="sidebar">
    <h3 class="title">All Boards ({{ boards.length }})</h3>
    <BoardList :boards="boards" @select-board="$emit('selectBoard', $event)" />
    <button @click="$emit('openBoardModal')" class="btn-primary">
      + Add New Board
    </button>
  </div>
</template>

<script setup lang="ts">
import BoardList from './BoardList.vue';

defineProps({
  boards: {
    type: Array,
    required: true
  }
});
defineEmits(['selectBoard', 'openBoardModal']);
</script>

<style scoped>
.sidebar {
  padding: 20px 15px;
}
.title {
  color: #757575; /* Grey */
  font-size: 0.9em;
  text-transform: uppercase;
  letter-spacing: 1px;
  margin-bottom: 15px;
}
.btn-primary {
  background-color: #ff9800; /* Amber */
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}
</style>