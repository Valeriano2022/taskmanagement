<script setup lang="ts">
import { ref } from 'vue'
import type { BoardResponse } from '@/types/board'
import Sidebar from '@/components/SideBar.vue'

const boards = ref<BoardResponse[]>([])

const activeBoardId = ref<number | null>(1)
const handleSelect = (id: number): void => {
  activeBoardId.value = id
}

const handleCreate = (): void => {}

const handlDelete = (id: number): void => {
  boards.value = boards.value.filter((board) => board.id !== id)
}
</script>
<template>
  <div class="app-layout">
    <Sidebar
      :boards="boards"
      :activeBoardId="activeBoardId"
      @select-board="handleSelect"
      @create-board="handleCreate"
      @delete-board="handlDelete"
    />
    <main class="main-content">
      <RouterView />
    </main>
  </div>
</template>
