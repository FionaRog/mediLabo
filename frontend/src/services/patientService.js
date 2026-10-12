import { API_URL, getAuthorizationHeader, getErrorMessage } from './apiClient.js'


export function getPatientById(id, credentials) {
    return fetch(`${API_URL}/patients/${id}`, {
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
        headers: {
            'Content-Type': 'application/json',
            Authorization: getAuthorizationHeader(credentials)
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
        headers: {
            'Content-Type': 'application/json',
            Authorization: getAuthorizationHeader(credentials)
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