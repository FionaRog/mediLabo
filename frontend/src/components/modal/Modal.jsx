import './Modal.css'
import { useEffect } from 'react'

function Modal({ children, onClose }) {

    useEffect(() => {
        function handleKeyDown(event) {
            if (event.key === 'Escape') {
                onClose()
            }
        }

        document.addEventListener('keydown', handleKeyDown)

        return () => {
            document.removeEventListener('keydown', handleKeyDown)
        }
    }, [onClose])


    return (
        <div
            className="modal-overlay"
            onClick={onClose}
        >

            <div
                className="modal-content"
                onClick={(event) => event.stopPropagation()}
            >

                <button
                    type="button"
                    className="modal-close"
                    onClick={onClose}
                >
                    x
                </button>

                {children}

            </div>
        </div>
    )
}

export default Modal