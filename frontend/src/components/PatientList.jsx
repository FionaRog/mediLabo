import { useEffect, useState } from 'react'
import { getAllPatients } from '../services/patientService.js'
import PatientForm from './PatientForm.jsx'

function PatientList({ credentials }) {
    const [patients, setPatients] = useState(null)
    const [error, setError] = useState(null)
    const [showForm, setShowForm] = useState(false)
    const [selectedPatient, setSelectedPatient] = useState(null)

    useEffect(() => {
        getAllPatients(credentials)
            .then(patientsData => {
                setPatients(patientsData)
            })
            .catch(fetchError => {
                console.error(fetchError)
                setError(fetchError.message)
            })
    }, [credentials])

    if (error !== null) {
        return <p>{error}</p>
    }

    if (patients === null) {
        return <p>Chargement des patients...</p>
    }

    function handlePatientAdded(newPatient) {
        setPatients(previousPatients => [
            ...previousPatients,
            newPatient
        ])

        setShowForm(false)
    }

    function handlePatientUpdated(updatedPatient) {
        setPatients(previousPatients =>
            previousPatients.map(patient =>
                patient.id === updatedPatient.id
                    ? updatedPatient
                    : patient
            )
        )

        setSelectedPatient(null)
    }

    return (
        <main>
            <h2>Patients</h2>

            {patients.map(patient => (
                <div key={patient.id}>
                    <p>Prénom : {patient.firstname}</p>
                    <p>Nom : {patient.lastname}</p>
                    <p>Date de naissance : {patient.dateOfBirth}</p>
                    <p>Genre : {patient.gender}</p>

                    <button
                        type="button"
                        onClick={() => {
                            setShowForm(false)
                            setSelectedPatient(patient)
                        }}
                    >
                        Modifier
                    </button>
                </div>
            ))}

            <button
                type="button"
                onClick={() => {
                    setSelectedPatient(null)
                    setShowForm(true)
                }}
            >
                Ajouter un patient
            </button>

            {showForm && (
                <PatientForm
                    credentials={credentials}
                    onPatientAdded={handlePatientAdded}
                />
            )}

            {selectedPatient && (
                <PatientForm
                    key={selectedPatient.id}
                    credentials={credentials}
                    patientToEdit={selectedPatient}
                    onPatientUpdated={handlePatientUpdated}
                />
            )}
        </main>
    )
}

export default PatientList