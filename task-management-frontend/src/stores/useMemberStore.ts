import { defineStore } from 'pinia'
import type { BoardMemberResponse, InviteMemberRequest } from '@/types/member'
import { inviteMember, getMember, removeMember } from '@/services/useMemberService'

export const useMemberStore = defineStore('member', {
  state: () => ({
    members: [] as BoardMemberResponse[],
    activeMember: null as BoardMemberResponse | null,
    loading: false,
  }),

  actions: {
    async loadMember(boardId: number, memberId: number) {
      this.loading = true
      try {
        const member = await getMember(boardId, memberId)
        this.activeMember = member

        // Update in list if exists
        const index = this.members.findIndex((m) => m.id === memberId)
        if (index !== -1) {
          this.members[index] = member
        } else {
          this.members.push(member)
        }

        return member
      } finally {
        this.loading = false
      }
    },

    async invite(boardId: number, payload: InviteMemberRequest) {
      this.loading = true
      try {
        const newMember = await inviteMember(boardId, payload)
        this.members.push(newMember)
        return newMember
      } finally {
        this.loading = false
      }
    },

    async remove(boardId: number, memberId: number) {
      this.loading = true
      try {
        await removeMember(boardId, memberId)
        this.members = this.members.filter((m) => m.id !== memberId)

        if (this.activeMember?.id === memberId) {
          this.activeMember = null
        }
      } finally {
        this.loading = false
      }
    },
  },

  getters: {
    allMembers: (state) => state.members,
    selectedMember: (state) => state.activeMember,
  },
})
