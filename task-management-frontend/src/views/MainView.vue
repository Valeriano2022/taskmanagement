<script setup lang="ts">
import { computed, onMounted } from 'vue'
import AppLayout from '@/layouts/AppLayout.vue'
import AppHeader from '@/components/AppHeader.vue'
import Sidebar from '@/components/SideBar.vue'
import BoardsContent from '@/components/BoardContent.vue'
import CreateBoardModal from '@/modal/CreateBoardModal.vue'
import TaskDetailsModal from '@/modal/TaskDetailModal.vue'
import TaskFormModal from '@/modal/TaskFormModal.vue'
import DeleteConfirmModal from '@/modal/DeleteConfirmModal.vue'
import { useBoardStore } from '@/stores/useBoardStore'
import { useTaskStore } from '@/stores/useTaskStore'
import { useModalStore } from '@/stores/useModalStore'
import type { CreateTaskRequest, TaskResponse, UpdateTaskRequest } from '@/types/task'
import type { BoardResponse, CreateBoardRequest } from '@/types/board'

const boardStore = useBoardStore()
const taskStore = useTaskStore()
const modalStore = useModalStore()

onMounted(() => {
  boardStore.loadBoards()
})

// Reactive state
const boards = computed(() => boardStore.boards)
const currentBoard = computed(() => boardStore.activeBoard)
const tasks = computed(() => taskStore.tasks)

// Sidebar selection
const selectBoard = (boardId: number) => {
  boardStore.setActiveBoard(boardId)
}

type TaskFormData = CreateTaskRequest | UpdateTaskRequest

// Task save
const saveTask = (taskData: TaskFormData) => {
  if (!currentBoard.value) return

  if (modalStore.isTaskEdit && modalStore.taskToEdit) {
    taskStore.updateTask(
      currentBoard.value.id,
      modalStore.taskToEdit.id,
      taskData as UpdateTaskRequest,
    )
  } else {
    taskStore.createTask(currentBoard.value.id, taskData as CreateTaskRequest)
  }

  modalStore.closeTaskForm()
}

// Delete confirmation handler
const handleDeleteConfirm = (item: TaskResponse | BoardResponse | null) => {
  if (!item) return

  if ('boardId' in item) {
    taskStore.removeTask(currentBoard.value!.id, item.id)
  } else {
    boardStore.deleteBoard(item.id)
  }

  modalStore.closeDeleteConfirm()
}

// Create Board Modal handler
const handleCreateBoard = async (payload: CreateBoardRequest) => {
  await boardStore.createBoard(payload)
  modalStore.closeCreateBoard()
}
</script>

<template>
  <AppLayout>
    <template #topbar>
      <AppHeader />
    </template>
    <template #sidebar>
      <Sidebar
        :boards="boards"
        @selectBoard="selectBoard"
        @openBoardModal="modalStore.openCreateBoard"
      />
    </template>

    <BoardsContent
      :currentBoard="currentBoard"
      :tasks="tasks"
      @openTaskModal="modalStore.openTaskDetails"
    />

    <CreateBoardModal
      v-if="modalStore.createBoardOpen"
      @close="modalStore.closeCreateBoard"
      @createBoard="handleCreateBoard"
    />

    <TaskDetailsModal
      v-if="modalStore.taskDetailsOpen"
      :task="modalStore.selectedTask"
      @edit="modalStore.openTaskFormForEdit"
      @delete="modalStore.openDeleteConfirm"
      @close="modalStore.closeTaskDetails"
    />

    <TaskFormModal
      v-if="modalStore.taskFormOpen"
      :task="modalStore.taskToEdit"
      :isEdit="modalStore.isTaskEdit"
      :boardId="currentBoard?.id ?? 0"
      @save="saveTask"
      @close="modalStore.closeTaskForm"
    />

    <DeleteConfirmModal
      v-if="modalStore.deleteConfirmOpen"
      :item="modalStore.itemToDelete"
      :itemName="modalStore.itemToDeleteName"
      :itemType="modalStore.itemToDeleteType"
      @confirm="handleDeleteConfirm"
      @close="modalStore.closeDeleteConfirm"
    />
  </AppLayout>
</template>

<style>
body {
  margin: 0;
  font-family: sans-serif;
}
</style>
