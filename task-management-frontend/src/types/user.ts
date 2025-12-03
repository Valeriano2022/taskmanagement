export interface UserResponse {
  id: number
  email: string
  name: string
}

export interface SignupRequest {
  email: string
  password: string
}

export interface LoginRequest {
  email: string
  password: string
}

export interface LoginResponse {
  accessToken: string
  refreshToken: string
  user: UserResponse
}

export interface LogoutResponse {
  message: string
}

export interface RefreshResponse {
  accessToken: string
}

export interface RefreshTokenRequest {
  refreshToken: string
}
