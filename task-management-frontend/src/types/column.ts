export interface BoardColumnResponse {
  id: number
  name: string
  position: number
}

export interface CreateBoardColumnRequest {
  name: string
  position: number
}

export interface UpdateBoardColumnRequest {
  name: string
  position: number
}
