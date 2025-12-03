export interface CreateCommentRequest {
  name: string
}

export interface TaskCommentResponse {
  id: number
  content: string
  author: string
}
