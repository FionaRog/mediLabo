
function Header({ isAuthenticated, onLogout }) {
    return (
        <header>
            <h1>MédiLabo</h1>
            {isAuthenticated && (
                <button onClick={onLogout}>
                    Se déconnecter
                </button>
            )}
        </header>
        )
    }

export default Header