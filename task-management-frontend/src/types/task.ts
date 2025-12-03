import type { UserResponse } from './user'

export interface AssignTaskRequest {
  assigneeId: number
}

export interface CreateTaskRequest {
  title: string
  description?: string
  assigneeId?: number
}

export interface TaskResponse {
  id: number
  title: string
  description: string
  assignee?: UserResponse
  status: string
  createdAt: string
  updatedAt: string
}

export interface UpdateTaskRequest {
  title?: string
  description?: string
  assigneeId?: number
}
