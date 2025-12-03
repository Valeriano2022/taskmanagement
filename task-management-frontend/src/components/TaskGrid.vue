<template>
  <div class="task-grid">
    <h3 class="column-title">
      <span class="dot" :style="{ backgroundColor: getStatusColor(status) }"></span>
      {{ status }} ({{ tasks.length }})
    </h3>
    
    <div class="tasks-list">
      <TaskCard 
        v-for="task in tasks" 
        :key="task.id" 
        :task="task" 
        @click="$emit('openTaskModal', task)"
      />
      
      <button class="add-task-btn">+ Add New Task</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import TaskCard from '@/components/TaskCard.vue';

defineProps({
  status: {
    type: String,
    required: true
  },
  tasks: {
    type: Array,
    required: true
  }
});

defineEmits(['openTaskModal']);

const getStatusColor = (status) => {
  switch (status) {
    case 'To Do': return '#4a90e2'; // Blue
    case 'In Progress': return '#f5a623'; // Orange
    case 'Done': return '#7ed321'; // Green
    case 'Design': return '#9012f2'; // Purple
    case 'Development': return '#12f2a6'; // Cyan
    case 'Testing': return '#f21212'; // Red
    case 'Deployed': return '#3d25d1'; // Dark Blue
    default: return '#ccc';
  }
};
</script>

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