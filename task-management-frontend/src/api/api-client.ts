import axios, {
  type AxiosInstance,
  AxiosError,
  type InternalAxiosRequestConfig,
  type AxiosResponse,
} from 'axios'

import { getAccessToken, saveAccessToken } from '@/api/tokens'
import { useAuthStore } from '@/stores/useAuthStore'
import type { RetryAxiosRequestConfig, QueueItem } from '@/types/api'

// API Client
export const api: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  withCredentials: true,
  headers: { 'Content-Type': 'application/json' },
  timeout: 10000,
})

// Refresh Client
const refreshClient: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  withCredentials: true,
})

// Refresh State
let isRefreshing = false
let queue: QueueItem[] = []

// Queue Resolver
const resolveQueue = (error: unknown, token: string | null): void => {
  queue.forEach(({ resolve, reject }) => {
    error ? reject(error) : resolve(token)
  })
  queue = []
}

// Refresh Action
const refreshAccessToken = async (): Promise<string> => {
  const res = await refreshClient.post('/auth/refresh', {})
  const newToken = (res.data as { accessToken: string }).accessToken
  saveAccessToken(newToken)
  return newToken
}

// Request Interceptor
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = getAccessToken()
    if (token) {
      config.headers = config.headers ?? {}
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error),
)

// Response Interceptor
api.interceptors.response.use(
  (response: AxiosResponse) => response,

  async (error: AxiosError) => {
    const auth = useAuthStore()
    const original = error.config as RetryAxiosRequestConfig

    // Reject non-401
    if (error.response?.status !== 401) {
      return Promise.reject(error)
    }

    // Retry Flag
    if (original._retry) {
      auth.logout()
      return Promise.reject(error)
    }

    // Missing refresh token
    if (!auth.refreshToken) {
      auth.logout()
      return Promise.reject(error)
    }

    // Queue if already refreshing
    if (isRefreshing) {
      return new Promise((resolve, reject) => {
        queue.push({ resolve, reject })
      }).then((token) => {
        original.headers = original.headers ?? {}
        original.headers.Authorization = `Bearer ${token}`
        return api(original)
      })
    }

    // Start Refresh
    original._retry = true
    isRefreshing = true

    try {
      const newToken = await refreshAccessToken()

      resolveQueue(null, newToken)

      original.headers = original.headers ?? {}
      original.headers.Authorization = `Bearer ${newToken}`

      return api(original)
    } catch (refreshError) {
      resolveQueue(refreshError, null)
      auth.logout()
      return Promise.reject(refreshError)
    } finally {
      isRefreshing = false
    }
  },
)
