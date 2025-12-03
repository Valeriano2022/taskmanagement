import type { UserResponse } from './user'
import type { BoardMemberResponse } from './member'

export interface CreateBoardRequest {
  name: string
}

export interface UpdateBoardRequest {
  name?: string
}

export interface BoardResponse {
  id: number
  name: string
  owner: UserResponse
  members: BoardMemberResponse[]
}

//usage boards.value = res.data._embedded?.boards ?? [];
