import {useEffect, useState} from 'react'
import {getNotesByPatientId } from '../../services/noteService.js'
import NoteForm from '../noteForm/NoteForm.jsx'
import './NoteListe.css'

function NoteListe({ patient, credentials, onRiskUpdate }) {
    const [notes, setNotes] = useState(null)    
    const [error, setError] = useState(null)
    const [noteForm, setNoteForm] = useState(false)

    useEffect(() => {
        getNotesByPatientId(patient.id, credentials)
            .then(notesData => {
                setNotes(notesData)
            })
            .catch(fetchError => {
                console.error(fetchError)
                setError(fetchError.message)
            })
    }, [patient.id, credentials])

    if (error !== null) {
        return <p>{error}</p>
    }

    if (notes === null) {
        return <p>Chargement des notes...</p>
    }

    function handleNoteAdded(newNote) {
        setNotes(previousNotes => [
            ...previousNotes,
            newNote
        ])

        setNoteForm(false)

        if (onRiskUpdate) {
            onRiskUpdate(patient.id)
        }
    }

    const formatDate = (date) => {
        return new Date(date).toLocaleString("fr-FR", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit"
        });
    }

    return (
        <div className="note-section">
            <h3>Notes pour {patient.firstname} {patient.lastname}</h3> 

            {notes.map(note => (
                <div key={note.id} className="note-item">
                    <p className="note-content">
                        <strong>Note : </strong> {note.note}
                    </p>

                  {note.createdAt && (
                    <p className="note-date">
                        Ajoutée le {formatDate(note.createdAt)}
                    </p>
                  )}
                </div>
            ))}

            
            {noteForm ? (
                <button
                    type="button"
                    onClick={() => setNoteForm(false)}
                >
                    Annuler
                </button> ) : (
                <button
                    type="button"
                    onClick={() => setNoteForm(true)}
                >
                    Ajouter une note
                </button>
            )}
                    

            {noteForm && (
                <NoteForm
                    patient={patient}
                    credentials={credentials}
                    onNoteAdded={handleNoteAdded}
                />
            )}

            
        </div>
    )


}

export default NoteListe



