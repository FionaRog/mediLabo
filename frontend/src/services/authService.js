import { API_URL, getAuthorizationHeader } from './apiClient.js'

export function login(username, password) {
    return fetch(`${API_URL}/auth/check`, {
        headers: {
            Authorization: getAuthorizationHeader({
                username,
                password
            })
        }
    })
}