<template>
  <ul class="board-list">
    <BoardListItem 
      v-for="board in boards" 
      :key="board.id" 
      :board="board" 
      @click="$emit('selectBoard', board.id)"
    />
  </ul>
</template>

<script setup lang="ts">
import BoardListItem from './BoardListItem.vue';

defineProps({
  boards: {
    type: Array,
    required: true
  }
});

defineEmits(['selectBoard']);
</script>

<style scoped>
.board-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
</style>