import { useState } from 'react'
import { login, getCsrfToken } from '../../services/authService.js'
import './Login.css'

function Login({ onLoginSuccess }) {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState(null)

    async function handleSubmit(event) {
        event.preventDefault()
        setError(null)

            try {
                const response = await login(username, password)

                if (!response.ok) {
                    setError('Identifiants incorrects')
                    return
                }

                const csrfToken = await getCsrfToken({
                    username,
                    password
                })

                onLoginSuccess(username, password, csrfToken)
            } catch (error) {
                setError(
                    error.message || 'Impossible de contacter le serveur'
                )
            }
    }

    return (
        <main>
            <h2>Connexion</h2>

            <form className="login-form" onSubmit={handleSubmit}>
                <div className="login-field">
                    <label htmlFor="username">Identifiant</label>
                    <input
                        id="username"
                        type="text"
                        value={username}
                        onChange={event => setUsername(event.target.value)}
                        required
                    />
                </div>

                <div className="login-field">
                    <label htmlFor="password">Mot de passe</label>
                    <input
                        id="password"
                        type="password"
                        value={password}
                        onChange={event => setPassword(event.target.value)}
                        required
                    />
                </div>

                {error && <p>{error}</p>}

                <button type="submit">
                    Se connecter
                </button>
            </form>
        </main>
    )
}

export default Login
