import type { AxiosRequestConfig } from 'axios'

export interface RetryAxiosRequestConfig extends AxiosRequestConfig {
  _retry?: boolean
}

export interface QueueItem {
  resolve: (token: string | null) => void
  reject: (error: unknown) => void
}
