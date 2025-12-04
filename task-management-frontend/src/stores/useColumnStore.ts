import { defineStore } from 'pinia'
import type {
  BoardColumnResponse,
  CreateBoardColumnRequest,
  UpdateBoardColumnRequest,
} from '@/types/column'
import {
  createColumn,
  getColumns,
  updateColumn,
  deleteColumn,
  reorderColumns,
} from '@/services/useColumnService'
import type { HateoasLinks, PageMetadata } from '@/types/hateoas'

export const useColumnStore = defineStore('column', {
  state: () => ({
    columns: [] as BoardColumnResponse[],
    pageInfo: null as PageMetadata | null,
    links: null as HateoasLinks | null,
    loading: false,
  }),

  actions: {
    async loadColumns(boardId: number, page = 0, size = 10) {
      this.loading = true
      try {
        const { items, page: pageInfo, links } = await getColumns(boardId, page, size)

        this.columns = items
        this.pageInfo = pageInfo
        this.links = links
      } finally {
        this.loading = false
      }
    },

    async createColumn(boardId: number, payload: CreateBoardColumnRequest) {
      const newColumn = await createColumn(boardId, payload)
      this.columns.push(newColumn)
      return newColumn
    },

    async updateColumn(boardId: number, columnId: number, payload: UpdateBoardColumnRequest) {
      const updated = await updateColumn(boardId, columnId, payload)

      const index = this.columns.findIndex((c) => c.id === columnId)
      if (index !== -1) {
        this.columns[index] = updated
      }
      return updated
    },

    async deleteColumn(boardId: number, columnId: number) {
      await deleteColumn(boardId, columnId)
      this.columns = this.columns.filter((c) => c.id !== columnId)
    },

    async reorder(boardId: number, newOrder: number[]) {
      await reorderColumns(boardId, newOrder)

      this.columns.sort((a, b) => newOrder.indexOf(a.id) - newOrder.indexOf(b.id))
    },
  },

  getters: {
    orderedColumns: (state) => [...state.columns].sort((a, b) => a.position - b.position),
  },
})
