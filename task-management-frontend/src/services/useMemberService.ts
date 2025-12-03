import { api } from '@/api/api-client'
import { unwrapEntity, type EntityModel } from '@/types/hateoas'
import type { BoardMemberResponse, InviteMemberRequest } from '@/types/member'

export async function inviteMember(boardId: number, payload: InviteMemberRequest) {
  const response = await api.post<EntityModel<BoardMemberResponse>>(
    `/api/boards/${boardId}/members/invite`,
    payload,
  )
  return unwrapEntity(response.data)
}

export async function getMember(boardId: number, memberId: number) {
  const response = await api.get<EntityModel<BoardMemberResponse>>(
    `/api/boards/${boardId}/members/${memberId}`,
  )
  return unwrapEntity(response.data)
}

export async function removeMember(boardId: number, memberId: number) {
  return await api.delete(`/api/boards/${boardId}/members/${memberId}`)
}
