const API_URL = import.meta.env.VITE_API_URL

function getAuthorizationHeader(credentials) {
    return 'Basic ' + btoa(
        `${credentials.username}:${credentials.password}`
    )
}

export function getPatientById(id, credentials) {
    return fetch(`${API_URL}/patients/${id}`, {
        headers: {
            Authorization: getAuthorizationHeader(credentials)
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(
                    'Erreur lors de la récupération du patient'
                )
            }

            return response.json()
        })
}

export function getAllPatients(credentials) {
    return fetch(`${API_URL}/patients`, {
        headers: {
            Authorization: getAuthorizationHeader(credentials)
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(
                    'Erreur lors de la récupération des patients'
                )
            }

            return response.json()
        })
}

export function addPatient(patient, credentials) {
    return fetch(`${API_URL}/patients`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: getAuthorizationHeader(credentials)
        },
        body: JSON.stringify(patient)
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

export function updatePatient(patient, credentials) {
    return fetch(`${API_URL}/patients/${patient.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: getAuthorizationHeader(credentials)
        },
        body: JSON.stringify(patient)
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