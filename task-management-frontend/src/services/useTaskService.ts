import { api } from '@/api/api-client'
import { unwrapEntity, unwrapPaged, type EntityModel, type PagedModel } from '@/types/hateoas'
import type {
  CreateTaskRequest,
  UpdateTaskRequest,
  TaskResponse,
  AssignTaskRequest,
} from '@/types/task'

export async function createTask(boardId: number, payload: CreateTaskRequest) {
  const response = await api.post<EntityModel<TaskResponse>>(
    `/api/boards/${boardId}/tasks`,
    payload,
  )
  return unwrapEntity(response.data)
}

export async function getTask(boardId: number, taskId: number) {
  const response = await api.get<EntityModel<TaskResponse>>(
    `/api/boards/${boardId}/tasks/${taskId}`,
  )
  return unwrapEntity(response.data)
}

export async function getTasks(boardId: number, pages: number = 0, size: number = 10) {
  const response = await api.get<PagedModel<TaskResponse>>(`/api/boards/${boardId}/tasks`, {
    params: { pages, size },
  })
  const { items, page, links } = unwrapPaged(response.data)
  return { items, page, links }
}

export async function updateTask(boardId: number, taskId: number, payload: UpdateTaskRequest) {
  const response = await api.put<EntityModel<TaskResponse>>(
    `/api/boards/${boardId}/tasks/${taskId}`,
    payload,
  )
  return unwrapEntity(response.data)
}

export async function deleteTask(boardId: number, taskId: number) {
  const response = await api.delete(`/api/boards/${boardId}/tasks/${taskId}`)
}

export async function assignTask(boardId: number, taskId: number, payload: AssignTaskRequest) {
  const response = await api.post<EntityModel<TaskResponse>>(
    `/api/boards/${boardId}/tasks/${taskId}/assign`,
    payload,
  )
  return unwrapEntity(response.data)
}
