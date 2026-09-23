const API_URL = import.meta.env.VITE_API_URL;

function getAuthorizationHeader(credentials) {
    return 'Basic ' + btoa(
        `${credentials.username}:${credentials.password}`
    )
}

export function getRiskByPatientId(patientId, credentials) {
    return fetch(`${API_URL}/risk/${patientId}`, {
        headers: {
            'Authorization': getAuthorizationHeader(credentials)
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erreur lors de la récupération du risque")
            }
            return response.json()
        })
}        