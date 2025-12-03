<template>
  <div class="modal-backdrop" @click.self="$emit('close')">
    <div class="modal-content">
      <h3>Create New Board</h3>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="board-name">Board Name</label>
          <input type="text" id="board-name" v-model="boardName" required>
        </div>

        <div class="form-group">
          <label>Board Columns</label>
          <textarea v-model="columnsInput" rows="3" placeholder="Enter column names separated by commas (e.g., To Do, In Progress, Done)"></textarea>
        </div>

        <div class="modal-actions">
          <button type="submit" class="btn-primary">Create Board</button>
          <button type="button" class="btn-secondary" @click="$emit('close')">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const boardName = ref('');
const columnsInput = ref('To Do, In Progress, Done');

defineEmits(['close', 'createBoard']);

const submitForm = () => {
  const columns = columnsInput.value.split(',').map(c => c.trim()).filter(c => c.length > 0);
  if (boardName.value && columns.length > 0) {
    const newBoard = {
      name: boardName.value,
      columns: columns,
    };
    emit('createBoard', newBoard);
  }
};
</script>

<style scoped>
/* Modal Base Styles (shared) */
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
  width: 450px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

/* Form Styles */
.form-group {
  margin-bottom: 15px;
}
label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}
input[type="text"], textarea {
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