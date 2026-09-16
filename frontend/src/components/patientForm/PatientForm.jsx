import { useState } from 'react'
import { addPatient, updatePatient } from '../../services/patientService.js'
import './PatientForm.css'

function PatientForm({
    credentials,
    onPatientAdded,
    onPatientUpdated,
    patientToEdit
}) {
    const [error, setError] = useState(null)
    const [firstname, setFirstname] = useState(
        patientToEdit ? patientToEdit.firstname : ''
    )
    const [lastname, setLastname] = useState(
        patientToEdit ? patientToEdit.lastname : ''
    )
    const [dateOfBirth, setDateOfBirth] = useState(
        patientToEdit ? patientToEdit.dateOfBirth : ''
    )
    const [gender, setGender] = useState(
        patientToEdit ? patientToEdit.gender : ''
    )
    const [address, setAddress] = useState(
        patientToEdit ? patientToEdit.address : ''
    )
    const [telephone, setTelephone] = useState(
        patientToEdit ? patientToEdit.telephone : ''
    )

    const yesterday = new Date()
    yesterday.setDate(yesterday.getDate() - 1)

    const maxDate = yesterday.toISOString().split('T')[0]


    function handleSubmit(event) {
        event.preventDefault()

        const patient = {
            firstname,
            lastname,
            dateOfBirth,
            gender,
            address,
            telephone
        }

        const request = patientToEdit
            ? updatePatient(
                { ...patient, id: patientToEdit.id },
                credentials
            )
            : addPatient(patient, credentials)

        request
            .then(patientData => {
                setError(null)

                if (patientToEdit) {
                    onPatientUpdated(patientData)
                } else {
                    onPatientAdded(patientData)
                }
            })
            .catch(error => {
                console.error(error)
                setError(error.message)
            })
    }

    return (
        <div className="patient-form-container">
            <h2>
                {patientToEdit
                    ? 'Modifier un patient'
                    : 'Ajouter un patient'}
            </h2>

            <form className="patient-form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="firstname">Prénom</label>
                    <input
                        id="firstname"
                        type="text"
                        value={firstname}
                        onChange={event => setFirstname(event.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="lastname">Nom</label>
                    <input
                        id="lastname"
                        type="text"
                        value={lastname}
                        onChange={event => setLastname(event.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="dateOfBirth">Date de naissance</label>
                    <input
                        id="dateOfBirth"
                        type="date"
                        value={dateOfBirth}
                        onChange={event => setDateOfBirth(event.target.value)}
                        max={maxDate}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="gender">Genre</label>
                    <select
                        id="gender"
                        value={gender}
                        onChange={event => setGender(event.target.value)}
                        required
                    >
                        <option value="">Sélectionner</option>
                        <option value="F">Femme</option>
                        <option value="M">Homme</option>
                    </select>
                </div>

                <div className="form-group">
                    <label htmlFor="address">Adresse</label>
                    <input
                        id="address"
                        type="text"
                        value={address}
                        onChange={event => setAddress(event.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="telephone">Téléphone</label>
                    <input
                        id="telephone"
                        type="tel"
                        value={telephone}
                        onChange={event => setTelephone(event.target.value)}
                    />
                </div>

                {error && <p>{error}</p>}

                <button type="submit">
                    {patientToEdit ? 'Modifier' : 'Ajouter'}
                </button>
            </form>
        </div>
    )
}

export default PatientForm
