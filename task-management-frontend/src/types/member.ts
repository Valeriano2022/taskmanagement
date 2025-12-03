import type { UserResponse } from './user'

export interface BoardMemberResponse {
  id: number
  user: UserResponse
  role: string
}

export interface InviteMemberRequest {
  email: string
}
