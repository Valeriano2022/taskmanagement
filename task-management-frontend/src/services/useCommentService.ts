import { api } from '@/api/api-client'
import {
  unwrapCollection,
  unwrapEntity,
  unwrapPaged,
  type CollectionModel,
  type EntityModel,
} from '@/types/hateoas'
import type { CreateCommentRequest, TaskCommentResponse } from '@/types/comment'

export async function createComment(taskId: number, payload: CreateCommentRequest) {
  try {
    const response = await api.post<EntityModel<TaskCommentResponse>>(
      `/api/tasks/${taskId}/comments`,
      payload,
    )
    return unwrapEntity(response.data)
  } catch (err) {
    console.error('Failed to create comment:', err)
    throw err
  }
}

export async function getComments(taskId: number) {
  try {
    const response = await api.get<CollectionModel<EntityModel<TaskCommentResponse>>>(
      `/api/tasks/${taskId}/comments`,
    )
    return unwrapCollection(response.data)
  } catch (err) {
    console.error('Failed to fetch comments:', err)
    throw err
  }
}
