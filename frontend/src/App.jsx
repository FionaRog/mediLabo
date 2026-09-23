import { useState } from 'react'
import './App.css'
import Header from './components/header/Header.jsx'
import PatientList from './components/patientList/PatientList.jsx'
import Login from './components/login/Login.jsx'
import Grainient from './components/background/Grainient.jsx'

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
            <div className="app-background">
            <Grainient
                color1="#b8ecdb"
                color2="#4a79f7"
                color3="#2dc2c3"
                timeSpeed={1.15}
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