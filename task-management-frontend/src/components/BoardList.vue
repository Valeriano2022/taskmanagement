<template>
  <ul class="board-list">
    <BoardListItem
      v-for="board in props.boards"
      :key="board.id"
      :board="board"
      :isActive="board.id === activeBoardId"
      @click="emit('select-board', board.id)"
    />
  </ul>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import BoardListItem from './BoardListItem.vue'
import { useBoardStore } from '@/stores/useBoardStore'
import type { BoardResponse } from '@/types/board'

const props = defineProps<{
  boards: BoardResponse[]
}>()

const emit = defineEmits<{
  (e: 'select-board', boardId: number): void
}>()

const boardStore = useBoardStore()

const activeBoardId = computed(() => boardStore.activeBoard?.id ?? null)
</script>

<style scoped>
.board-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
</style>
