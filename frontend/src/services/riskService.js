import { API_URL, getAuthorizationHeader, getErrorMessage } from './apiClient.js'


export function getRiskByPatientId(patientId, credentials) {
    return fetch(`${API_URL}/risk/${patientId}`, {
        credentials: 'include',
        headers: {
            'Authorization': getAuthorizationHeader(credentials)
        }
    })
        .then(async response => {
            if (!response.ok) {
                const message = await getErrorMessage(
                    response,
                    'Erreur lors de la récupération du risque'
                )

                throw new Error(message)
            }

            return response.json()
        })
}        