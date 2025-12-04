<template>
  <div class="boards-content">
    <template v-if="currentBoard">
      <h2>{{ currentBoard.name }}</h2>

      <div class="task-grid-container">
        <TaskGrid
          v-for="column in columns"
          :key="column.id"
          :column="column"
          :tasks="filteredTasks(column.id)"
          @open-task-modal="emit('openTaskModal', $event)"
        />

        <div class="add-column">
          <button class="add-column-btn">Add New Column</button>
        </div>
      </div>
    </template>

    <p v-else class="no-board-selected">Select a board from the sidebar or create a new one.</p>
  </div>
</template>

<script setup lang="ts">
import TaskGrid from './TaskGrid.vue'
import { computed, watch } from 'vue'
import { useColumnStore } from '@/stores/useColumnStore'
import type { BoardResponse } from '@/types/board'
import type { TaskResponse } from '@/types/task'

const props = defineProps<{
  currentBoard: BoardResponse | null
  tasks: TaskResponse[]
}>()

const emit = defineEmits<{
  (e: 'openTaskModal', task: TaskResponse): void
}>()

const columnStore = useColumnStore()

watch(
  () => props.currentBoard?.id,
  (id) => {
    if (id) columnStore.loadColumns(id)
  },
  { immediate: true },
)

const columns = computed(() => columnStore.orderedColumns)

const filteredTasks = (columnId: number) => {
  return props.tasks.filter((t) => t.columnId === columnId)
}
</script>

<style scoped>
.boards-content {
  padding: 20px;
}

.task-grid-container {
  display: flex;
  gap: 20px;
  padding: 20px 0;
  overflow-x: auto;
  min-height: 80vh;
}

.task-grid-container > div {
  flex-shrink: 0;
  width: 300px;
}

.add-column {
  width: 300px;
  height: 100%;
  background-color: #e0e0e0;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0.7;
  cursor: pointer;
  transition: opacity 0.2s;
  flex-shrink: 0;
}

.add-column:hover {
  opacity: 1;
}

.add-column-btn {
  background: none;
  border: none;
  font-size: 1.2em;
  font-weight: bold;
  color: #888;
  cursor: pointer;
}

.no-board-selected {
  font-size: 1.2em;
  color: #757575;
  margin-top: 50px;
}
</style>
