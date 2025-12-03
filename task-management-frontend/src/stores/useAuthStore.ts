import { defineStore } from 'pinia'
import { removeAccessToken, getAccessToken, saveAccessToken } from '@/api/tokens'
import type { UserResponse } from '@/types/user'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as UserResponse | null,
    accessToken: getAccessToken(),
    refreshToken: null as string | null,
  }),

  actions: {
    login(data: { user: UserResponse; accessToken: string; refreshToken: string }) {
      this.accessToken = data.accessToken
      this.refreshToken = data.refreshToken
      this.user = data.user

      saveAccessToken(data.accessToken)
    },

    logout() {
      this.user = null
      this.accessToken = null
      this.refreshToken = null

      removeAccessToken()
    },
  },
  getters: {
    isAuthenticated: (state) => !!state.accessToken,
  },
})
