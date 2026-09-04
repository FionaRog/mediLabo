const API_URL = import.meta.env.VITE_API_URL

export function login(username, password) {

    const credentials = btoa(`${username}:${password}`)

    return fetch(`${API_URL}/auth/check`, {
       headers: {
           Authorization: 'Basic ' + credentials
       }
    })
}