export function getAuthorizationHeader(credentials) {
    return 'Basic ' + btoa(
        `${credentials.username}:${credentials.password}`
    )
}

export async function getErrorMessage(response, fallbackMessage) {
    try {
        const errorData = await response.json()

        return Object.values(errorData).join(', ') || fallbackMessage
    } catch {
        return fallbackMessage
    }
}

export function getCsrfHeader(credentials) {
    return {
        [credentials.csrfToken.headerName]: credentials.csrfToken.token
    }
}

export const API_URL = import.meta.env.VITE_API_URL