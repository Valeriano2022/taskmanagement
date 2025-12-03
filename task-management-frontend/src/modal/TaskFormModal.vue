<template>
  <div class="modal-backdrop" @click.self="$emit('close')">
    <div class="modal-content">
      <h3>{{ isEdit ? 'Edit Task' : 'Create New Task' }}</h3>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="task-title">Title</label>
          <input type="text" id="task-title" v-model="formData.title" required>
        </div>

        <div class="form-group">
          <label for="task-description">Description</label>
          <textarea id="task-description" v-model="formData.description" rows="4"></textarea>
        </div>

        <div class="form-group">
          <label for="task-status">Status</label>
          <select id="task-status" v-model="formData.status" required>
            <option value="To Do">To Do</option>
            <option value="In Progress">In Progress</option>
            <option value="Done">Done</option>
          </select>
        </div>

        <div class="form-group">
          <label for="task-priority">Priority</label>
          <select id="task-priority" v-model="formData.priority" required>
            <option value="Low">Low</option>
            <option value="Medium">Medium</option>
            <option value="High">High</option>
          </select>
        </div>

        <div class="modal-actions">
          <button type="submit" class="btn-primary">
            {{ isEdit ? 'Save Changes' : 'Create Task' }}
          </button>
          <button type="button" class="btn-secondary" @click="$emit('close')">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue';

const props = defineProps({
  initialTask: {
    type: Object,
    default: null
  },
  isEdit: {
    type: Boolean,
    default: false
  },
  boardId: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(['close', 'saveTask']);

// Reactive state for the form data
const formData = reactive({
  id: null,
  boardId: props.boardId,
  title: '',
  description: '',
  status: 'To Do',
  priority: 'Medium',
});

// Set initial data if editing
onMounted(() => {
  if (props.initialTask) {
    Object.assign(formData, props.initialTask);
  }
});

const submitForm = () => {
  // Ensure boardId is always correct
  formData.boardId = props.boardId;
  emit('saveTask', { ...formData });
};
</script>

<style scoped>
/* Inherit modal styles from CreateBoardModal or use your own */
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
}
.modal-content {
  background: white;
  padding: 25px;
  border-radius: 8px;
  width: 500px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}
.form-group {
  margin-bottom: 15px;
}
label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}
input[type="text"], textarea, select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1em;
}
textarea {
    resize: vertical;
}
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}
.btn-primary {
  background-color: #3f51b5;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}
.btn-secondary {
  background-color: #eee;
  color: #333;
  border: 1px solid #ccc;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}
</style>