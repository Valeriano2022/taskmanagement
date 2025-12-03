<template>
  <div class="task-card">
    <h4 class="task-title">{{ task.title }}</h4>
    <p class="task-description">{{ task.description.substring(0, 50) + '...' }}</p>
    <div class="task-footer">
      <span :class="['priority', task.priority.toLowerCase().replace(' ', '-')]">
        {{ task.priority }}
      </span>
      <span class="comment-count">💬 0</span>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps({
  task: {
    type: Object,
    required: true
  }
});
</script>

<style scoped>
.task-card {
  background-color: white;
  padding: 15px;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: box-shadow 0.2s;
  border-left: 5px solid #3f51b5; /* Highlight */
}

.task-card:hover {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.15);
}

.task-title {
  font-size: 1em;
  font-weight: bold;
  margin: 0 0 5px 0;
  color: #333;
}

.task-description {
  font-size: 0.85em;
  color: #757575;
  margin: 0 0 10px 0;
}

.task-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.8em;
}

.priority {
  padding: 3px 8px;
  border-radius: 3px;
  font-weight: bold;
  color: white;
}

.priority.high {
  background-color: #f44336; /* Red */
}

.priority.medium {
  background-color: #ff9800; /* Amber */
}

.priority.low {
  background-color: #4caf50; /* Green */
}
</style>