import MainBar from './MainBar.jsx'
import SubBar from './SubBar.jsx'
import styles from './Header.module.css'

function Header() {
    return (
        <header className={styles.header}>
            <MainBar />
            <SubBar />
        </header>
    )
}

export default Header