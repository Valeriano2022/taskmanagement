import { defineStore } from 'pinia'
import type {
  TaskResponse,
  CreateTaskRequest,
  UpdateTaskRequest,
  AssignTaskRequest,
} from '@/types/task'
import {
  createTask,
  getTask,
  updateTask,
  deleteTask,
  assignTask,
  getTasks,
} from '@/services/useTaskService'
import type { HateoasLinks, PageMetadata } from '@/types/hateoas'

export const useTaskStore = defineStore('task', {
  state: () => ({
    tasks: [] as TaskResponse[],
    activeTask: null as TaskResponse | null,
    pageInfo: null as PageMetadata | null,
    links: null as HateoasLinks | null,
    loading: false,
  }),

  actions: {
    async loadTasks(boardId: number, page = 0, size = 10) {
      this.loading = true
      try {
        const { items, page: pageInfo, links } = await getTasks(boardId, page, size)

        this.tasks = items
        this.pageInfo = pageInfo
        this.links = links
      } finally {
        this.loading = false
      }
    },
    async loadTask(boardId: number, taskId: number) {
      this.loading = true
      try {
        const task = await getTask(boardId, taskId)
        this.activeTask = task

        const index = this.tasks.findIndex((t) => t.id === taskId)
        if (index !== -1) {
          this.tasks[index] = task
        } else {
          this.tasks.push(task)
        }

        return task
      } finally {
        this.loading = false
      }
    },

    async createTask(boardId: number, payload: CreateTaskRequest) {
      this.loading = true
      try {
        const newTask = await createTask(boardId, payload)
        this.tasks.push(newTask)
        return newTask
      } finally {
        this.loading = false
      }
    },

    async updateTask(boardId: number, taskId: number, payload: UpdateTaskRequest) {
      this.loading = true
      try {
        const updated = await updateTask(boardId, taskId, payload)

        const index = this.tasks.findIndex((t) => t.id === taskId)
        if (index !== -1) {
          this.tasks[index] = updated
        }

        if (this.activeTask?.id === taskId) {
          this.activeTask = updated
        }

        return updated
      } finally {
        this.loading = false
      }
    },

    async assignTask(boardId: number, taskId: number, payload: AssignTaskRequest) {
      this.loading = true
      try {
        const updated = await assignTask(boardId, taskId, payload)

        const index = this.tasks.findIndex((t) => t.id === taskId)
        if (index !== -1) {
          this.tasks[index] = updated
        }

        if (this.activeTask?.id === taskId) {
          this.activeTask = updated
        }

        return updated
      } finally {
        this.loading = false
      }
    },

    async removeTask(boardId: number, taskId: number) {
      this.loading = true
      try {
        await deleteTask(boardId, taskId)

        this.tasks = this.tasks.filter((t) => t.id !== taskId)

        // clear active if matching
        if (this.activeTask?.id === taskId) {
          this.activeTask = null
        }
      } finally {
        this.loading = false
      }
    },
  },

  getters: {
    allTasks: (state) => state.tasks,
    currentTask: (state) => state.activeTask,
  },
})
