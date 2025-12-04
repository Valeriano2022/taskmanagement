<script setup lang="ts">
import TaskCard from '@/components/TaskCard.vue'
import type { TaskResponse } from '@/types/task'
import type { BoardColumnResponse } from '@/types/column'

const props = defineProps<{
  column: BoardColumnResponse
  tasks: TaskResponse[]
}>()

const emit = defineEmits<{
  (e: 'openTaskModal', task: TaskResponse): void
  (e: 'addTask', columnId: number): void
}>()

const getStatusColor = (name: string): string => {
  switch (name.toLowerCase()) {
    case 'todo':
    case 'to do':
      return '#4a90e2'
    case 'in progress':
      return '#f5a623'
    case 'done':
      return '#7ed321'
    default:
      return '#ccc'
  }
}
</script>

<template>
  <div class="task-grid">
    <h3 class="column-title">
      <span class="dot" :style="{ backgroundColor: getStatusColor(props.column.name) }"></span>
      {{ props.column.name }} ({{ props.tasks.length }})
    </h3>

    <div class="tasks-list">
      <TaskCard
        v-for="task in props.tasks"
        :key="task.id"
        :task="task"
        @click="emit('openTaskModal', task)"
      />

      <button class="add-task-btn" @click="emit('addTask', props.column.id)">+ Add New Task</button>
    </div>
  </div>
</template>

<style scoped>
.task-grid {
  min-height: 100%;
  background-color: #fff;
  border-radius: 6px;
  padding: 10px;
}

.column-title {
  display: flex;
  align-items: center;
  font-size: 0.9em;
  color: #757575;
  text-transform: uppercase;
  letter-spacing: 1px;
  margin-bottom: 15px;
}

.dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-right: 8px;
}

.tasks-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.add-task-btn {
  background: none;
  border: 2px dashed #ccc;
  color: #888;
  padding: 10px;
  margin-top: 10px;
  border-radius: 4px;
  width: 100%;
  cursor: pointer;
  font-weight: bold;
}
.add-task-btn:hover {
  border-color: #3f51b5;
  color: #3f51b5;
}
</style>
