import { defineStore } from 'pinia'
import type { BoardResponse, CreateBoardRequest } from '@/types/board'
import {
  createBoard,
  getBoard,
  getBoards,
  updateBoard,
  deleteBoard,
} from '@/services/useBoardService'
import type { HateoasLinks, PageMetadata } from '@/types/hateoas'

export const useBoardStore = defineStore('board', {
  state: () => ({
    boards: [] as BoardResponse[],
    board: null as BoardResponse | null,
    activeBoard: null as BoardResponse | null,
    pageInfo: null as PageMetadata | null,
    links: null as HateoasLinks | null,
    loading: false,
  }),

  actions: {
    async createBoard(payload: CreateBoardRequest) {
      const newBoard = await createBoard(payload)
      this.boards.push(newBoard)
      this.activeBoard = newBoard
      return newBoard
    },

    async loadBoards(page = 0, size = 10) {
      this.loading = true
      try {
        const { items, page: pageInfo, links } = await getBoards(page, size)

        this.boards = items
        this.pageInfo = pageInfo
        this.links = links

        if (!this.activeBoard && items.length > 0) {
          this.activeBoard = items[0]!
        }
      } finally {
        this.loading = false
      }
    },

    async loadBoard(id: number) {
      this.board = await getBoard(id)
      this.activeBoard = this.board
    },

    async updateBoard(id: number, payload: CreateBoardRequest) {
      const updatedBoard = await updateBoard(id, payload)

      if (this.board?.id === id) this.board = updatedBoard
      if (this.activeBoard?.id === id) this.activeBoard = updatedBoard

      const index = this.boards.findIndex((b) => b.id === id)
      if (index !== -1) this.boards[index] = updatedBoard

      return updatedBoard
    },

    async deleteBoard(id: number) {
      await deleteBoard(id)

      this.boards = this.boards.filter((b) => b.id !== id)

      if (this.activeBoard?.id === id) {
        this.activeBoard = this.boards.length > 0 ? this.boards[0]! : null
      }

      if (this.board?.id === id) {
        this.board = null
      }
    },

    setActiveBoard(id: number) {
      this.activeBoard = this.boards.find((b) => b.id === id) ?? null
    },
  },

  getters: {
    boardList: (state) => state.boards,
    currentBoard: (state) => state.board,
    selectedBoard: (state) => state.activeBoard,
  },
})
