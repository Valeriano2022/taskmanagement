<template>
  <div class="boards-content">
    <template v-if="currentBoard && currentBoard.columns">
      <h2>{{ currentBoard.name }}</h2>
      
      <div class="task-grid-container">
        <TaskGrid
          v-for="columnName in currentBoard.columns"
          :key="columnName"
          :status="columnName"
          :tasks="filteredTasks(columnName)"
          @open-task-modal="$emit('openTaskModal', $event)"
        />
        
        <div class="add-column">
          <button class="add-column-btn">+ Add New Column</button>
        </div>
      </div>
    </template>
    <p v-else class="no-board-selected">
      Select a board from the sidebar or create a new one.
    </p>
  </div>
</template>

<script setup lang="ts">
import TaskGrid from './TaskGrid.vue';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { computed } from 'vue';

const props = defineProps({
  currentBoard: {
    type: Object,
    default: () => ({})
  },
  tasks: {
    type: Array,
    required: true
  }
});

defineEmits(['openTaskModal']);

// Filter tasks to show only those belonging to the current board and a specific column
const filteredTasks = (columnStatus) => {
  if (!props.currentBoard.id) return [];
  return props.tasks.filter(
    task => task.boardId === props.currentBoard.id && task.status === columnStatus
  );
};
</script>

<style scoped>
.boards-content {
  padding: 20px;
}

.task-grid-container {
  display: flex; /* Use flex to allow horizontal scrolling */
  gap: 20px;
  padding: 20px 0;
  overflow-x: auto; /* Enable horizontal scrolling */
  min-height: 80vh; /* Ensure container has enough height */
}

/* Base styles for a column (TaskGrid is one column) */
.task-grid-container > div {
  flex-shrink: 0; /* Prevent columns from shrinking */
  width: 300px; /* Fixed width for each column */
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