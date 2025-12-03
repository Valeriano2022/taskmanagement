<template>
  <div class="modal-backdrop" @click.self="$emit('close')">
    <div class="modal-content">
      <div class="modal-header">
        <h3>{{ task.title }}</h3>
        <div class="actions">
          <button @click="$emit('openEdit')" class="btn-icon edit-btn" title="Edit Task">
            ✏️
          </button>
          <button @click="$emit('deleteTask', task)" class="btn-icon delete-btn" title="Delete Task">
            🗑️
          </button>
        </div>
      </div>
      
      <p class="task-description-full">{{ task.description }}</p>

      <div class="details-section">
        <p><strong>Status:</strong> <span class="detail-tag status">{{ task.status }}</span></p>
        <p><strong>Priority:</strong> <span class="detail-tag priority">{{ task.priority }}</span></p>
      </div>

      <div class="comments-section">
        <h4>Comments</h4>
        <p class="no-comments">No comments yet.</p>
        <div class="comment-input">
            <input type="text" placeholder="Add a comment...">
            <button class="btn-primary">Post</button>
        </div>
      </div>
      
      <button class="close-btn" @click="$emit('close')">Close</button>
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

defineEmits(['close', 'openEdit', 'deleteTask']);
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
  width: 600px; /* Wider for details */
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
  margin-bottom: 15px;
}

.actions {
  display: flex;
  gap: 10px;
}

.btn-icon {
  background: none;
  border: none;
  font-size: 1.2em;
  cursor: pointer;
  padding: 5px;
  border-radius: 4px;
}
.btn-icon:hover {
  background-color: #eee;
}

.task-description-full {
  margin-bottom: 20px;
  color: #555;
  white-space: pre-wrap;
}

.details-section p {
  margin: 5px 0;
}

.detail-tag {
  padding: 3px 8px;
  border-radius: 3px;
  font-weight: bold;
  background-color: #f0f0f0;
  color: #333;
  margin-left: 5px;
  font-size: 0.9em;
}

.comments-section {
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid #eee;
}

.no-comments {
    color: #999;
    font-style: italic;
    margin-bottom: 10px;
}

.comment-input {
    display: flex;
    gap: 10px;
}
.comment-input input {
    flex-grow: 1;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
.comment-input .btn-primary {
    background-color: #3f51b5;
    color: white;
    border: none;
    padding: 8px 15px;
    border-radius: 4px;
    cursor: pointer;
}

.close-btn {
  display: block;
  width: 100%;
  margin-top: 20px;
  background-color: #3f51b5;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
}
</style>