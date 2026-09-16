const API_URL = import.meta.env.VITE_API_URL

function getAuthorizationHeader(credentials) {
    return 'Basic ' + btoa(
        `${credentials.username}:${credentials.password}`
    )
}

export function getNotesByPatientId(patId, credentials) {
    return fetch(`${API_URL}/notes/patient/${patId}`, {
        headers: {
            Authorization: getAuthorizationHeader(credentials)
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(
                    'Erreur lors de la récupération des notes'
                )
            }
            return response.json()
        })
}

export function createNote(noteData, credentials) {
    return fetch(`${API_URL}/notes`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: getAuthorizationHeader(credentials)
        },
        body: JSON.stringify(noteData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(errorData => {
                        const errorMessage =
                            Object.values(errorData).join(', ')
                        throw new Error(errorMessage)
                    })
            }
            return response.json()
        })
}
