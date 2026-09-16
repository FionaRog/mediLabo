import { useState } from 'react'
import { createNote } from '../../services/noteService.js'
import './NoteForm.css'

function NoteForm({ patient, credentials, onNoteAdded }) {
    const [error, setError] = useState(null)
    const [note, setNote] = useState('')   

    function handleSubmit(event) {
        event.preventDefault()

        const noteData = {
            patId: patient.id,
            patient: `${patient.firstname} ${patient.lastname}`,
            note,
        }

        createNote(noteData, credentials)
            .then(newNote => {
                setError(null)
                setNote('')

                if (onNoteAdded) {
                    onNoteAdded(newNote)
                }
            })
            .catch(fetchError => {
                console.error(fetchError)
                setError(fetchError.message)
            })  
    }
    
    return (
        <div>
            <h3>Ajouter une note</h3>

            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="note">Note :</label>
                    <textarea
                        className="note-textarea"
                        id="note"
                        value={note}
                        onChange={event => setNote(event.target.value)}
                    />
                </div>
                {error && <p>{error}</p>}
                <button type="submit">Ajouter</button>
            </form>
        </div>
    )
}

export default NoteForm

