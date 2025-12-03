<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useBoardStore } from '@/stores/useBoardStore'
import { storeToRefs } from 'pinia'

const boardStore = useBoardStore()

const { boards, board } = storeToRefs(boardStore)

const activeBoardId = board.value

const emits = defineEmits<{
  (e: 'select-board', boardId: number): void
  (e: 'create-board'): void
  (e: 'delete-board', boardId: number): void
}>()

const isOpen = ref<boolean>(false)
const toggle = (): void => {
  isOpen.value = isOpen.value ? false : true
}

onMounted(() => {
  boardStore.loadBoards()
})
</script>
<template>
  <aside class="sidebar" :class="{ open: isOpen }">
    <div class="top-section">
      <h2 v-if="!isOpen">Task Management</h2>
      <button class="burger" @click="toggle">☰</button>
    </div>
    <ul class="board-list">
      <li
        v-for="board in boards"
        :key="board.id"
        :class="{ active: board.id === activeBoardId?.id }"
        @click="emits('select-board', board.id)"
      >
        <span v-if="!isOpen">{{ board.name }}</span>
        <span v-else class="dot"></span>
        <button class="delete-btn" v-if="!isOpen" @click.stop="emits('delete-board', board.id)">
          Delete
        </button>
      </li>
    </ul>
    <button class="new-board-btn" @click="emits('create-board')" v-if="!isOpen">
      Create New Board
    </button>
  </aside>
</template>
<style scoped>
.sidebar {
  width: 260px;
  background: #1e1f20;
  color: white;
  height: 100vh;
  padding: 1rem;
  transition: width 0.25s ease;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.sidebar.open {
  width: 70px;
}

.top-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.collapse-btn {
  background: none;
  border: none;
  color: white;
  cursor: pointer;
  font-size: 1.2rem;
}

.burger {
  background: none;
  border: none;
  color: white;
  cursor: pointer;
  font-size: 1.5rem;
}
.new-board-btn {
  width: 100%;
  background: #3e3f40;
  border: none;
  color: white;
  padding: 10px;
  border-radius: 6px;
  cursor: pointer;
  text-align: left;
  font-weight: 500;
  margin-bottom: 1rem;
}
.new-board-btn:hover {
  background: #494a4b;
}

.board-list {
  list-style: none;
  padding: 0;
  margin: 0;
  flex: 1;
  overflow-y: auto;
}

.board-list li {
  padding: 10px;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
  cursor: pointer;
  margin-bottom: 5px;
}

.board-list li:hover {
  background: #2b2c2d;
}

.board-list li.active {
  background: #343638;
  font-weight: bold;
}

.delete-btn {
  background: none;
  border: none;
  color: #ff9999;
  cursor: pointer;
  opacity: 10;
  transition: opacity 0.2s;
}

.board-list li:hover .delete-btn {
  opacity: 1;
}

.dot {
  width: 12px;
  height: 12px;
  background: white;
  border-radius: 50%;
  margin: auto;
}
</style>
