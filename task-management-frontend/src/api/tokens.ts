export function saveAccessToken(token: string) {
  localStorage.setItem('token', token)
}

export function getAccessToken() {
  return localStorage.getItem('token')
}

export function removeAccessToken() {
  localStorage.removeItem('token')
}
