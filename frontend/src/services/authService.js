import { API_URL, getAuthorizationHeader, getCsrfHeader, getErrorMessage } from './apiClient.js'

export function login(username, password) {
    return fetch(`${API_URL}/auth/check`, {
        credentials: 'include',
        headers: {
            Authorization: getAuthorizationHeader({
                username,
                password
            })
        }
    })
}

export function getCsrfToken(credentials) {
    return fetch(`${API_URL}/auth/csrf`, {
        credentials: 'include',
        headers: {
            Authorization: getAuthorizationHeader(credentials)
        }
    })
        .then(async response => {
            if (!response.ok) {
                const message = await getErrorMessage(
                    response,
                    'Impossible de récupérer le jeton de sécurité'
                )

                throw new Error(message)
            }

            return response.json()
        })
}

export async function logout(credentials) {
    const response = await fetch(`${API_URL}/auth/logout`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            Authorization: getAuthorizationHeader(credentials),
            ...getCsrfHeader(credentials)
        }
    })

    if (!response.ok) {
        const message = await getErrorMessage(
            response,
            'Impossible de fermer la session'
        )

        throw new Error(message)
    }
}