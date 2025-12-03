import { defineStore } from 'pinia'
import type { CreateCommentRequest, TaskCommentResponse } from '@/types/comment'
import { createComment, getComments } from '@/services/useCommentService'

export const useCommentStore = defineStore('comment', {
  state: () => ({
    comments: [] as TaskCommentResponse[],
    loading: false,
  }),

  actions: {
    async addComment(taskId: number, comment: CreateCommentRequest) {
      const newComment = await createComment(taskId, comment)
      this.comments.push(newComment)
    },

    async loadCommentsByTask(taskId: number) {
      this.loading = true
      try {
        this.comments = await getComments(taskId)
      } finally {
        this.loading = false
      }
    },
  },
})
