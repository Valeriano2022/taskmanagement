<template>
  <div class="modal-backdrop" @click.self="emit('close')">
    <div class="modal-content">
      <h3>{{ isEdit ? 'Edit Task' : 'Create New Task' }}</h3>

      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="task-title">Title</label>
          <input type="text" id="task-title" v-model="formData.title" required />
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
          <select id="task-priority" v-model="formData.priority">
            <option value="Low">Low</option>
            <option value="Medium">Medium</option>
            <option value="High">High</option>
          </select>
        </div>

        <div class="modal-actions">
          <button type="submit" class="btn-primary">
            {{ isEdit ? 'Save Changes' : 'Create Task' }}
          </button>
          <button type="button" class="btn-secondary" @click="emit('close')">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, watchEffect } from 'vue'
import type { TaskResponse, CreateTaskRequest, UpdateTaskRequest } from '@/types/task'

const props = defineProps<{
  task: TaskResponse | null
  isEdit: boolean
  boardId: number
}>()

const emit = defineEmits<{
  (e: 'close'): void
  (e: 'save', payload: CreateTaskRequest | UpdateTaskRequest): void
}>()

const formData = reactive<CreateTaskRequest & Partial<UpdateTaskRequest>>({
  title: '',
  description: '',
  status: 'To Do',
  priority: 'Medium',
  boardId: props.boardId,
})

watchEffect(() => {
  if (props.task && props.isEdit) {
    Object.assign(formData, props.task)
  }
})

const submitForm = () => {
  formData.boardId = props.boardId

  emit('save', { ...formData })
  emit('close')
}
</script>

<style scoped>
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
input[type='text'],
textarea,
select {
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
