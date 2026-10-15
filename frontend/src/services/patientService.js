import { API_URL, getAuthorizationHeader, getCsrfHeader, getErrorMessage } from './apiClient.js'


export function getPatientById(id, credentials) {
    return fetch(`${API_URL}/patients/${id}`, {
        credentials: 'include',
        headers: {
            Authorization: getAuthorizationHeader(credentials)
        }
    })
        .then(async response => {
            if (!response.ok) {
                const message = await getErrorMessage(
                    response,
                    'Erreur lors de la récupération du patient'
                )

                throw new Error(message)
            }

            return response.json()
        })
}

export function getAllPatients(credentials) {
    return fetch(`${API_URL}/patients`, {
        credentials: 'include',
        headers: {
            Authorization: getAuthorizationHeader(credentials)
        }
    })
        .then(async response => {
            if (!response.ok) {
                const message = await getErrorMessage(
                    response,
                    'Erreur lors de la récupération des patients'
                )

                throw new Error(message)
            }

            return response.json()
        })
}

export function addPatient(patient, credentials) {
    return fetch(`${API_URL}/patients`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
            Authorization: getAuthorizationHeader(credentials),
            ...getCsrfHeader(credentials)
        },
        body: JSON.stringify(patient)
    })
        .then(async response => {
            if (!response.ok) {
                const message = await getErrorMessage(
                    response,
                    'Erreur lors de la création du patient'
                )

                throw new Error(message)
            }

            return response.json()
        })
}

export function updatePatient(patient, credentials) {
    return fetch(`${API_URL}/patients/${patient.id}`, {
        method: 'PUT',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
            Authorization: getAuthorizationHeader(credentials),
            ...getCsrfHeader(credentials)
        },
        body: JSON.stringify(patient)
    })
        .then(async response => {
            if (!response.ok) {
                const message = await getErrorMessage(
                    response,
                    'Erreur lors de la modification du patient'
                )

                throw new Error(message)
            }

            return response.json()
        })
}