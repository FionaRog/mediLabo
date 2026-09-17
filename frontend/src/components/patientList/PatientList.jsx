import { useEffect, useState } from 'react'
import { getAllPatients } from '../../services/patientService.js'
import PatientForm from '../patientForm/PatientForm.jsx'
import Modal from '../modal/Modal.jsx'
import NoteListe from '../noteList/NoteListe.jsx'
import './PatientList.css'

function PatientList({ credentials }) {
    const [patients, setPatients] = useState(null)
    const [error, setError] = useState(null)
    const [showForm, setShowForm] = useState(false)
    const [selectedPatient, setSelectedPatient] = useState(null)
    const [patientForNotes, setPatientForNotes] = useState(null)

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
                <Modal onClose={() => setShowForm(false)}>
                    <PatientForm
                        credentials={credentials}
                        onPatientAdded={handlePatientAdded}
                    />
                </Modal>
            )}

            {patients.map(patient => (
                <div
                    key={patient.id}
                    className="patient-card"
                >
                    <p>
                        <strong>Prénom : </strong>
                        {patient.firstname}
                    </p>

                    <p>
                        <strong>Nom : </strong>
                        {patient.lastname}
                    </p>

                    <p>
                        <strong>Date de naissance : </strong>
                        {patient.dateOfBirth}
                    </p>

                    <p>
                        <strong>Genre : </strong>
                        {patient.gender}
                    </p>

                    <p>
                        <strong>Adresse : </strong>
                        {patient.address}
                    </p>

                    <p>
                        <strong>Téléphone : </strong>
                        {patient.telephone}
                    </p>

                    <button
                        type="button"
                        onClick={() => {
                            setShowForm(false)
                            setSelectedPatient(patient)
                        }}
                    >
                        Modifier
                    </button>

                    <button
                        type="button"
                        onClick={() => {
                            setPatientForNotes(
                                patientForNotes?.id === patient.id
                                    ? null
                                    : patient
                            )
                        }}
                    >
                        {patientForNotes?.id === patient.id
                            ? 'Masquer les notes'
                            : 'Voir les notes'}
                    </button>

                    {patientForNotes?.id === patient.id && (
                        <NoteListe
                            patient={patientForNotes}
                            credentials={credentials}
                        />
                    )}
                </div>
            ))}

            {selectedPatient && (
                <Modal onClose={() => setSelectedPatient(null)}>
                    <PatientForm
                        key={selectedPatient.id}
                        credentials={credentials}
                        patientToEdit={selectedPatient}
                        onPatientUpdated={handlePatientUpdated}
                    />
                </Modal>
            )}

        </main>
    )
}

export default PatientList
