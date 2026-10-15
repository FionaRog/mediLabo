import { API_URL, getAuthorizationHeader, getCsrfHeader, getErrorMessage } from './apiClient.js'

export function getNotesByPatientId(patId, credentials) {
    return fetch(`${API_URL}/notes/patient/${patId}`, {
        credentials: 'include',
        headers: {
            Authorization: getAuthorizationHeader(credentials)
        }
    })
        .then(async response => {
            if (!response.ok) {
                const message = await getErrorMessage(
                    response,
                    'Erreur lors de la récupération des notes'
                )

                throw new Error(message)
            }

            return response.json()
        })
}

export function createNote(noteData, credentials) {
    return fetch(`${API_URL}/notes`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
            Authorization: getAuthorizationHeader(credentials),
            ...getCsrfHeader(credentials)
        },
        body: JSON.stringify(noteData)
    })
        .then(async response => {
            if (!response.ok) {
                const message = await getErrorMessage(
                    response,
                    'Erreur lors de la création de la note'
                )

                throw new Error(message)
            }

            return response.json()
        })
}
