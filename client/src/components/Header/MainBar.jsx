import {
    useEffect,
    useRef,
    useState
} from 'react'

import {
    ChevronDown,
    MapPin,
    Search,
    ShoppingCart,
    UserRound
} from 'lucide-react'

import { useNavigate } from 'react-router-dom'

import usFlag from '../../assets/flags/us.svg'
import vnFlag from '../../assets/flags/vn.svg'

import styles from './MainBar.module.css'

function MainBar() {
    const navigate = useNavigate()

    const [query, setQuery] = useState('')
    const [language, setLanguage] = useState('EN')
    const [languageOpen, setLanguageOpen] = useState(false)

    const languageSelectorRef = useRef(null)

    useEffect(() => {
        if (!languageOpen) {
            return
        }

        function handleClickOutside(event) {
            if (
                languageSelectorRef.current &&
                !languageSelectorRef.current.contains(event.target)
            ) {
                setLanguageOpen(false)
            }
        }

        document.addEventListener(
            'mousedown',
            handleClickOutside
        )

        return () => {
            document.removeEventListener(
                'mousedown',
                handleClickOutside
            )
        }
    }, [languageOpen])

    function handleSearch(event) {
        event.preventDefault()

        const trimmedQuery = query.trim()

        if (!trimmedQuery) {
            return
        }

        navigate(
            `/search?query=${encodeURIComponent(trimmedQuery)}`
        )
    }

    function selectLanguage(newLanguage) {
        setLanguage(newLanguage)
        setLanguageOpen(false)
    }

    const languageFlag =
        language === 'EN' ? usFlag : vnFlag

    return (
        <div className={styles.mainBar}>
            <div className={styles.logo}>
                Momo<span>zon</span>
            </div>

            <button
                className={styles.location}
                type="button"
            >
                <MapPin size={20} />

                <span className={styles.locationText}>
                    <small>Deliver to</small>
                    <strong>Delft 2614</strong>
                </span>
            </button>

            <form
                className={styles.search}
                onSubmit={handleSearch}
            >
                <input
                    type="search"
                    value={query}
                    placeholder="Search for products from Germany..."
                    aria-label="Search products"
                    onChange={event =>
                        setQuery(event.target.value)
                    }
                />

                <button
                    type="submit"
                    aria-label="Search"
                >
                    <Search size={20} />
                </button>
            </form>

            <div
                className={styles.languageSelector}
                ref={languageSelectorRef}
            >
                <button
                    className={styles.language}
                    type="button"
                    aria-haspopup="menu"
                    aria-expanded={languageOpen}
                    onClick={() =>
                        setLanguageOpen(current => !current)
                    }
                >
                    <img
                        className={styles.languageFlag}
                        src={languageFlag}
                        alt=""
                    />

                    <span>{language}</span>

                    <ChevronDown size={15} />
                </button>

                {languageOpen && (
                    <div
                        className={styles.languageMenu}
                        role="menu"
                    >
                        <button
                            className={`${styles.languageOption} ${
                                language === 'EN'
                                    ? styles.activeLanguage
                                    : ''
                            }`}
                            type="button"
                            role="menuitem"
                            onClick={() =>
                                selectLanguage('EN')
                            }
                        >
                            <img
                                className={styles.optionFlag}
                                src={usFlag}
                                alt=""
                            />

                            <span>English</span>
                        </button>

                        <button
                            className={`${styles.languageOption} ${
                                language === 'VI'
                                    ? styles.activeLanguage
                                    : ''
                            }`}
                            type="button"
                            role="menuitem"
                            onClick={() =>
                                selectLanguage('VI')
                            }
                        >
                            <img
                                className={styles.optionFlag}
                                src={vnFlag}
                                alt=""
                            />

                            <span>Tiếng Việt</span>
                        </button>
                    </div>
                )}
            </div>

            <button
                className={styles.account}
                type="button"
            >
                <UserRound size={22} />
                <span>Sign in</span>
            </button>

            <button
                className={styles.cart}
                type="button"
                aria-label="Cart"
            >
                <ShoppingCart size={25} />
            </button>
        </div>
    )
}

export default MainBar