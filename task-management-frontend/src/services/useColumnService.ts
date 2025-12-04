import { api } from '@/api/api-client'
import type {
  BoardColumnResponse,
  CreateBoardColumnRequest,
  UpdateBoardColumnRequest,
} from '@/types/column'
import { unwrapEntity, unwrapPaged, type EntityModel, type PagedModel } from '@/types/hateoas'

const BASE = '/api/boards'

export async function createColumn(boardId: number, payload: CreateBoardColumnRequest) {
  const response = await api.post<EntityModel<BoardColumnResponse>>(
    `${BASE}/${boardId}/columns`,
    payload,
  )
  return unwrapEntity(response.data)
}

export async function getColumns(boardId: number, page: number = 0, size: number = 10) {
  const response = await api.get<PagedModel<BoardColumnResponse>>(`${BASE}/${boardId}/columns`, {
    params: { page, size },
  })
  const { items, page: pageInfo, links } = unwrapPaged(response.data)
  return { items, page: pageInfo, links }
}

export async function updateColumn(
  boardId: number,
  columnId: number,
  payload: UpdateBoardColumnRequest,
) {
  const response = await api.put<EntityModel<BoardColumnResponse>>(
    `${BASE}/${boardId}/columns/${columnId}`,
    payload,
  )
  return unwrapEntity(response.data)
}

export async function deleteColumn(boardId: number, columnId: number) {
  return await api.delete(`${BASE}/${boardId}/columns/${columnId}`)
}

export async function reorderColumns(boardId: number, newOrder: number[]) {
  return await api.put(`${BASE}/${boardId}/columns/reorder`, newOrder)
}
