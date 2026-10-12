import { useEffect, useState } from 'react'
import { getAllPatients } from '../../services/patientService.js'
import { getRiskByPatientId } from '../../services/riskService.js'
import PatientForm from '../patientForm/PatientForm.jsx'
import Modal from '../modal/Modal.jsx'
import NoteList from '../noteList/NoteList.jsx'
import './PatientList.css'

function PatientList({ credentials }) {
    const [patients, setPatients] = useState(null)
    const [error, setError] = useState(null)
    const [showForm, setShowForm] = useState(false)
    const [selectedPatient, setSelectedPatient] = useState(null)
    const [patientForNotes, setPatientForNotes] = useState(null)
    const [risks, setRisks] = useState({})
    const [riskErrors, setRiskErrors] = useState({})

    useEffect(() => {
        getAllPatients(credentials)
            .then(patientsData => {
                setPatients(patientsData)

                patientsData.forEach(patient => {
                    getRiskByPatientId(patient.id, credentials)
                        .then(riskLevel => {
                            setRisks(previousRisks => ({
                                ...previousRisks,
                                [patient.id]: riskLevel
                            }))
                        })
                        .catch(fetchError => {
                            console.error(fetchError)

                            setRiskErrors(previousErrors => ({
                                ...previousErrors,
                                [patient.id]: fetchError.message
                            }))
                        })
                })
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

        handleRiskUpdate(newPatient.id)

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

        handleRiskUpdate(updatedPatient.id)

        setSelectedPatient(null)
    }

    function getRiskLabel(riskLevel) {
        switch (riskLevel) {
            case 'NONE':
                return 'Aucun risque'
            case 'BORDERLINE':
                return 'Risque limité'
            case 'IN_DANGER':
                return 'En danger'
            case 'EARLY_ONSET':
                return 'Début précoce'
            default:
                return 'Non évalué'
        }
    }

    function handleRiskUpdate(patientId) {
        setRisks(previousRisks => {
            const updatedRisks = { ...previousRisks }
            delete updatedRisks[patientId]
            return updatedRisks
        })

        setRiskErrors(previousErrors => {
            const updatedErrors = { ...previousErrors }
            delete updatedErrors[patientId]
            return updatedErrors
        })

        getRiskByPatientId(patientId, credentials)
            .then(riskLevel => {
                setRisks(previousRisks => ({
                    ...previousRisks,
                    [patientId]: riskLevel
                }))
            })
            .catch(fetchError => {
                console.error(fetchError)

                setRiskErrors(previousErrors => ({
                    ...previousErrors,
                    [patientId]: fetchError.message
                }))
            })
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
                className={`patient-row ${
                    patientForNotes?.id === patient.id ? 'with-notes' : ''
            }`}
            >

                <div className="patient-card">

                    <span
                        className={`risk-badge ${
                            riskErrors[patient.id]
                                ? 'risk-unavailable'
                                : `risk-${risks[patient.id]?.toLowerCase()}`
                        }`}
                    >
                        {riskErrors[patient.id]
                            ? 'Évaluation indisponible'
                            : getRiskLabel(risks[patient.id])}
                    </span>

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

                </div>

                {patientForNotes?.id === patient.id && (
                        <NoteList
                            patient={patientForNotes}
                            credentials={credentials}
                            onRiskUpdate={handleRiskUpdate}
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
