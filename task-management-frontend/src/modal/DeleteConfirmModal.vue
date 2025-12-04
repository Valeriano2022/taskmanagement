<template>
  <div class="modal-backdrop" @click.self="emit('close')">
    <div class="modal-content delete-modal">
      <h3 class="delete-title">Delete {{ itemType }}?</h3>

      <p class="delete-message">
        Are you sure you want to delete the "{{ itemName }}" {{ itemType }}?
      </p>

      <div class="modal-actions">
        <button class="btn-cancel" @click="emit('close')">Cancel</button>
        <button class="btn-delete" @click="emit('confirm', item)">Delete</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { TaskResponse } from '@/types/task'
import type { BoardResponse } from '@/types/board'

const props = defineProps<{
  item: TaskResponse | BoardResponse | null
  itemName: string
  itemType: 'Task' | 'Board' | ''
}>()

const emit = defineEmits<{
  (e: 'close'): void
  (e: 'confirm', item: TaskResponse | BoardResponse | null): void
}>()

const { item, itemName, itemType } = props
</script>

<style scoped>
/* Inherit modal base styles */
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
  width: 400px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  text-align: center;
}

.delete-title {
  color: #f44336; /* Red */
  margin-bottom: 10px;
}

.delete-message {
  color: #555;
  margin-bottom: 25px;
}

.modal-actions {
  display: flex;
  justify-content: space-around;
  gap: 15px;
}

.btn-delete,
.btn-cancel {
  flex-grow: 1;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.btn-delete {
  background-color: #f44336; /* Red */
  color: white;
  border: none;
}

.btn-cancel {
  background-color: #eee;
  color: #333;
  border: 1px solid #ccc;
}
</style>
