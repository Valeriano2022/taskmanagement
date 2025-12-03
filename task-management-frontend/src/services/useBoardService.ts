import { api } from '@/api/api-client'
import type { CreateBoardRequest, UpdateBoardRequest, BoardResponse } from '@/types/board'
import { unwrapEntity, unwrapPaged, type EntityModel, type PagedModel } from '@/types/hateoas'

export async function createBoard(payload: CreateBoardRequest) {
  const response = await api.post<EntityModel<BoardResponse>>('/api/boards', payload)
  return unwrapEntity(response.data)
}

export async function getBoards(pages: number = 0, size: number = 10) {
  const response = await api.get<PagedModel<BoardResponse>>('/api/boards', {
    params: { pages, size },
  })
  const { items, page, links } = unwrapPaged(response.data)
  return { items, page, links }
}

export async function getBoard(boardId: number) {
  const response = await api.get<EntityModel<BoardResponse>>(`/api/boards/${boardId}`)
  return unwrapEntity(response.data)
}

export async function updateBoard(boardId: number, payload: UpdateBoardRequest) {
  const response = await api.put<EntityModel<BoardResponse>>(`/api/boards/${boardId}`, payload)
  return unwrapEntity(response.data)
}

export async function deleteBoard(boardId: number) {
  return await api.delete(`/api/boards/${boardId}`)
}
