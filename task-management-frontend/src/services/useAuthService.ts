import { api } from '@/api/api-client'
import { unwrapEntity, type EntityModel } from '@/types/hateoas'
import type {
  SignupRequest,
  LoginRequest,
  LoginResponse,
  RefreshResponse,
  RefreshTokenRequest,
} from '@/types/user'

export async function logIn(payload: LoginRequest) {
  const auth = await api.post<EntityModel<LoginResponse>>('/api/auth/login', payload)
  return unwrapEntity(auth.data)
}

export async function signUp(payload: SignupRequest) {
  const auth = await api.post<EntityModel<Map<String, String>>>('/api/auth/signup', payload)
  return unwrapEntity(auth.data)
}

export async function logOut() {
  return await api.post('/api/auth/logout')
}

export async function refreshToken(payload: RefreshTokenRequest) {
  const response = await api.post<EntityModel<RefreshResponse>>('/api/auth/refresh', payload)
  return unwrapEntity(response.data)
}
