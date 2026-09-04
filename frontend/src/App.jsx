import { useState } from 'react'
import './App.css'
import Header from './components/Header.jsx'
import PatientList from './components/PatientList.jsx'
import Login from './components/Login.jsx'

function App() {
    const [credentials, setCredentials] = useState(null)

    function handleLogout() {
        setCredentials(null)
    }

    function handleLoginSuccess(username, password) {
        setCredentials({
            username,
            password
        })
    }

    return (
        <>
            <Header
                isAuthenticated={credentials !== null}
                onLogout={handleLogout}
            />

            {credentials
                ? <PatientList credentials={credentials} />
                : <Login onLoginSuccess={handleLoginSuccess} />
            }
        </>
    )
}

export default App