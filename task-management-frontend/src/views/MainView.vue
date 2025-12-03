<template>
  <AppLayout>
    <template #topbar>
      <Topbar  />
      <ProfileDropdown />
    </template>

    <template #sidebar>
      <Sidebar :boards="boards.list" @select-board="selectBoard" @open-board-modal="openCreateBoardModal" />
    </template>

    <BoardsContent :currentBoard="boards.current" :tasks="tasks" @open-task-modal="openTaskDetailsModal" />
  </AppLayout>

  <CreateBoardModal 
    v-if="modals.createBoardOpen" 
    @close="closeCreateBoardModal" 
    @create-board="addBoard"
  />

  <TaskDetailsModal 
    v-if="modals.taskDetailsOpen" 
    :task="modals.selectedTask"
    @close="closeTaskDetailsModal" 
    @open-edit="openTaskFormModalForEdit"
    @delete-task="openDeleteConfirmModal"
  />

  <TaskFormModal
    v-if="modals.taskFormOpen"
    :initial-task="modals.taskToEdit"
    :is-edit="modals.isTaskEdit"
    :board-id="boards.current.id"
    @create-task="openTaskFormModalForCreate"
    @close="closeTaskFormModal"
    @save-task="saveTask"
  />

  <DeleteConfirmModal
    v-if="modals.deleteConfirmOpen"
    :item-name="modals.itemToDeleteName"
    :item-type="modals.itemToDeleteType"
    @close="closeDeleteConfirmModal"
    @confirm-delete="handleDeleteConfirm"
  />
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import AppLayout from '@/layouts/AppLayout.vue';
import Topbar from '@/components/TopBar.vue';
import ProfileDropdown from '@/components/ProfileDropdown.vue';
import Sidebar from '@/components/SideBar.vue';
import BoardsContent from '@/components/BoardContent.vue';
import CreateBoardModal from '@/modal/CreateBoardModal.vue';
import TaskDetailsModal from '@/modal/TaskDetailModal.vue';
import TaskFormModal from '@/modal/TaskFormModal.vue';
import DeleteConfirmModal from '@/modal/DeleteConfirmModal.vue';

// --- 1. Sample Data ---
const sampleBoards = [
  { id: 1, name: 'Marketing Campaign', columns: ['To Do', 'In Progress', 'Done'] },
  { id: 2, name: 'Product Roadmap', columns: ['Design', 'Development', 'Testing', 'Deployed'] },
];

const sampleTasks = [
  { id: 101, boardId: 1, title: 'Write ad copy for social', description: 'Draft short, catchy lines for Instagram and Facebook.', status: 'To Do', priority: 'Medium' },
  { id: 102, boardId: 1, title: 'Design landing page mockup', description: 'Create a high-fidelity mockup for the new product launch.', status: 'In Progress', priority: 'High' },
  { id: 201, boardId: 2, title: 'Implement user auth module', description: 'Set up sign-in, sign-up, and password reset.', status: 'Development', priority: 'High' },
  { id: 202, boardId: 2, title: 'Final UI Review', description: 'Get sign-off from design lead on all final screens.', status: 'Testing', priority: 'Low' },
];

// --- 2. State Management (Reactivity) ---

const boards = reactive({
  list: sampleBoards,
  current: sampleBoards[0] || {},
});

const tasks = ref(sampleTasks);

const modals = reactive({
  createBoardOpen: false,
  taskDetailsOpen: false,
  taskFormOpen: false,
  deleteConfirmOpen: false,
  selectedTask: null,
  taskToEdit: null,
  isTaskEdit: false,
  itemToDelete: null,
  itemToDeleteName: '',
  itemToDeleteType: '', // 'Task' or 'Board'
});

// --- 3. Board/Sidebar Logic ---

const selectBoard = (boardId) => {
  boards.current = boards.list.find(b => b.id === boardId);
};

// --- 4. CRUD Operations (Simplified) ---

const addBoard = (newBoard) => {
  const newId = Math.max(...boards.list.map(b => b.id), 0) + 1;
  const board = { id: newId, ...newBoard };
  boards.list.push(board);
  boards.current = board; // Select the newly created board
  closeCreateBoardModal();
};

const saveTask = (taskData) => {
  if (modals.isTaskEdit) {
    // EDIT
    const index = tasks.value.findIndex(t => t.id === taskData.id);
    if (index !== -1) {
      tasks.value[index] = taskData;
    }
  } else {
    // CREATE
    const newId = Math.max(...tasks.value.map(t => t.id), 0) + 1;
    tasks.value.push({ id: newId, ...taskData });
  }
  closeTaskFormModal();
};

const deleteTask = (taskId) => {
  tasks.value = tasks.value.filter(t => t.id !== taskId);
};

// --- 5. Modal Handlers ---

// Create Board
const openCreateBoardModal = () => modals.createBoardOpen = true;
const closeCreateBoardModal = () => modals.createBoardOpen = false;

// Task Details/View
const openTaskDetailsModal = (task) => {
  modals.selectedTask = task;
  modals.taskDetailsOpen = true;
};
const closeTaskDetailsModal = () => {
  modals.taskDetailsOpen = false;
  modals.selectedTask = null;
};

// Task Form (Create/Edit)
const openTaskFormModalForCreate = () => {
  modals.taskToEdit = null;
  modals.isTaskEdit = false;
  modals.taskFormOpen = true;
};

const openTaskFormModalForEdit = () => {
  // Transfer selected task from TaskDetailsModal to TaskFormModal
  modals.taskToEdit = modals.selectedTask; 
  modals.isTaskEdit = true;
  modals.taskFormOpen = true;
  closeTaskDetailsModal(); // Close details when opening edit form
};

const closeTaskFormModal = () => {
  modals.taskFormOpen = false;
  modals.taskToEdit = null;
};

// Delete Confirmation
const openDeleteConfirmModal = (item) => {
  modals.itemToDelete = item;
  modals.itemToDeleteType = item.boardId ? 'Task' : 'Board';
  modals.itemToDeleteName = item.title || item.name;
  modals.deleteConfirmOpen = true;
  if (item.boardId) {
    closeTaskDetailsModal(); // Close the details modal if deleting a task
  }
};
const closeDeleteConfirmModal = () => {
  modals.deleteConfirmOpen = false;
  modals.itemToDelete = null;
};

const handleDeleteConfirm = () => {
  if (modals.itemToDeleteType === 'Task') {
    deleteTask(modals.itemToDelete.id);
  } else if (modals.itemToDeleteType === 'Board') {
    // Logic to delete board (not fully implemented here, but structure is ready)
    console.log(`Deleting board: ${modals.itemToDelete.name}`);
  }
  closeDeleteConfirmModal();
};
</script>

<style>
/* Basic global styles */
body {
  margin: 0;
  font-family: sans-serif;
}
</style>