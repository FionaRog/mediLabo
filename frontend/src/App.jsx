import { useState } from 'react'
import { logout } from './services/authService.js'
import './App.css'
import Header from './components/header/Header.jsx'
import PatientList from './components/patientList/PatientList.jsx'
import Login from './components/login/Login.jsx'
import Grainient from './components/background/Grainient.jsx'

function App() {
    const [credentials, setCredentials] = useState(null)
    const [logoutError, setLogoutError] = useState(null)

    async function handleLogout() {
        setLogoutError(null)

        try {
            await logout(credentials)
            setCredentials(null)
        } catch (error) {
            setLogoutError(error.message)
        }
    }

    function handleLoginSuccess(username, password, csrfToken) {
        setLogoutError(null)

        setCredentials({
            username,
            password,
            csrfToken
        })
    }

    return (
        <>            
            <div className="app-background">
            <Grainient
                color1="#fdfdfd"
                color2="#acf0fb"
                color3="#9bc1ff"
                timeSpeed={0.15}
                colorBalance={0}
                warpStrength={1}
                warpFrequency={5}
                warpSpeed={2}
                warpAmplitude={50}
                blendAngle={0}
                blendSoftness={0.05}
                rotationAmount={500}
                noiseScale={2}
                grainAmount={0.1}
            />

            <Header
                isAuthenticated={credentials !== null}
                onLogout={handleLogout}
            />

            <div className="app-content">
                {logoutError && <p role="alert">{logoutError}</p>}
                {credentials
                    ? <PatientList credentials={credentials} />
                    : <Login onLoginSuccess={handleLoginSuccess} />
                }
            </div>
        </div>
    </>
)
}

export default App