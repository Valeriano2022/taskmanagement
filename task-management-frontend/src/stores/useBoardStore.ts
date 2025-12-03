import { defineStore } from 'pinia'
import type { BoardResponse, CreateBoardRequest } from '@/types/board'
import { createBoard, getBoard, getBoards, updateBoard } from '@/services/useBoardService'
import type { HateoasLinks, PageMetadata } from '@/types/hateoas'

export const useBoardStore = defineStore('board', {
  state: () => ({
    boards: [] as BoardResponse[],
    board: null as BoardResponse | null,
    pageInfo: null as PageMetadata | null,
    links: null as HateoasLinks | null,
    loading: false,
  }),

  actions: {
    async createBoard(payload: CreateBoardRequest) {
      const newBoard = await createBoard(payload)
      this.boards.push(newBoard)
      return newBoard
    },
    async loadBoards(page = 0, size = 10) {
      this.loading = true
      try {
        const { items, page: pageInfo, links } = await getBoards(page, size)

        this.boards = items
        this.pageInfo = pageInfo
        this.links = links
      } finally {
        this.loading = false
      }
    },
    async loadBoard(id: number) {
      this.board = await getBoard(id)
    },

    async updateBoard(id: number, payload: CreateBoardRequest) {
      const updatedBoard = await updateBoard(id, payload)

      if (this.board?.id === id) {
        this.board = updatedBoard
      }

      const index = this.boards.findIndex((b) => b.id === id)
      if (index !== -1) {
        this.boards[index] = updatedBoard
      }
      return updatedBoard
    },
  },

  getters: {
    boardList: (state) => state.boards,
    currentBoard: (state) => state.board,
  },
})
