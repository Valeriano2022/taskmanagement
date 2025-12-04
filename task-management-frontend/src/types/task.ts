import type { UserResponse } from './user'

export interface AssignTaskRequest {
  assigneeId: number
}

export interface CreateTaskRequest {
  title: string
  boardId: number
  description?: string
  status: string
  priority: string
  assigneeId?: number
}

export interface TaskResponse {
  id: number
  boardId: number
  columnId: number
  columnName: string
  title: string
  description: string
  assignee?: UserResponse
  priority: string
  createdAt: string
  updatedAt: string
}

export interface UpdateTaskRequest {
  title?: string
  description?: string
  status?: string
  priority?: string
  assigneeId?: number
}
