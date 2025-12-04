import { defineStore } from 'pinia'
import type { TaskResponse } from '@/types/task'
import type { BoardResponse } from '@/types/board'

export const useModalStore = defineStore('modal', {
  state: () => ({
    createBoardOpen: false,

    taskDetailsOpen: false,
    taskFormOpen: false,

    deleteConfirmOpen: false,

    selectedTask: null as TaskResponse | null,
    taskToEdit: null as TaskResponse | null,
    isTaskEdit: false,

    itemToDelete: null as TaskResponse | BoardResponse | null,
    itemToDeleteName: '' as string,
    itemToDeleteType: '' as 'Task' | 'Board' | '',
  }),

  actions: {
    openCreateBoard() {
      this.createBoardOpen = true
    },
    closeCreateBoard() {
      this.createBoardOpen = false
    },
    openTaskDetails(task: TaskResponse) {
      this.selectedTask = task
      this.taskDetailsOpen = true
    },
    closeTaskDetails() {
      this.selectedTask = null
      this.taskDetailsOpen = false
    },
    openTaskFormForCreate() {
      this.taskToEdit = null
      this.isTaskEdit = false
      this.taskFormOpen = true
    },
    openTaskFormForEdit() {
      this.taskToEdit = this.selectedTask
      this.isTaskEdit = true
      this.taskFormOpen = true
      this.closeTaskDetails()
    },

    closeTaskForm() {
      this.taskFormOpen = false
      this.taskToEdit = null
      this.isTaskEdit = false
    },
    openDeleteConfirm(item: TaskResponse | BoardResponse) {
      this.itemToDelete = item
      this.itemToDeleteType = 'boardId' in item ? 'Task' : 'Board'
      this.itemToDeleteName = 'title' in item ? item.title : item.name
      this.deleteConfirmOpen = true

      if ('boardId' in item) {
        this.closeTaskDetails()
      }
    },
    closeDeleteConfirm() {
      this.deleteConfirmOpen = false
      this.itemToDelete = null
      this.itemToDeleteName = ''
      this.itemToDeleteType = ''
    },
  },
})
