import { useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import {
    ChevronRight,
    X
} from 'lucide-react'

import styles from './CategoryMenu.module.css'

function CategoryMenu({ open, onClose }) {
    const navigate = useNavigate()

    const [categories, setCategories] = useState([])
    const [activeRootId, setActiveRootId] = useState(null)

    useEffect(() => {
        async function loadCategories() {
            const response = await fetch('/api/categories')

            if (!response.ok) {
                throw new Error(
                    'Failed to load categories'
                )
            }

            const data = await response.json()

            setCategories(data)
        }

        loadCategories().catch(console.error)
    }, [])

    const childrenByParent = useMemo(() => {
        const map = new Map()

        for (const category of categories) {
            const parentId = category.parentId ?? null

            if (!map.has(parentId)) {
                map.set(parentId, [])
            }

            map.get(parentId).push(category)
        }

        return map
    }, [categories])

    const rootCategories =
        childrenByParent.get(null) ?? []

    useEffect(() => {
        if (
            open &&
            activeRootId === null &&
            rootCategories.length > 0
        ) {
            setActiveRootId(rootCategories[0].id)
        }
    }, [open, activeRootId, rootCategories])

    useEffect(() => {
        if (!open) return

        function handleKeyDown(event) {
            if (event.key === 'Escape') {
                onClose()
            }
        }

        document.addEventListener('keydown', handleKeyDown)

        const previousOverflow =
            document.body.style.overflow

        document.body.style.overflow = 'hidden'

        return () => {
            document.removeEventListener(
                'keydown',
                handleKeyDown
            )

            document.body.style.overflow =
                previousOverflow
        }
    }, [open, onClose])

    if (!open) return null

    function getChildren(parentId) {
        return childrenByParent.get(parentId) ?? []
    }

    function selectRoot(categoryId) {
        setActiveRootId(categoryId)
    }

    function openRootCategory(categoryId) {
        onClose()
        navigate(`/category/${categoryId}`)
    }

    function openProductCategory(categoryId) {
        onClose()
        navigate(`/search?categoryId=${categoryId}`)
    }

    const activeRoot = rootCategories.find(
        category => category.id === activeRootId
    )

    const levelTwoCategories = activeRoot
        ? getChildren(activeRoot.id)
        : []

    return (
        <div className={styles.overlay}>
            <button
                className={styles.backdrop}
                type="button"
                aria-label="Close categories"
                onClick={onClose}
            />

            <aside
                id="category-menu"
                className={styles.menu}
                aria-label="Categories"
            >
                <div className={styles.menuBody}>
                    <div className={styles.sidebar}>
                        <div className={styles.brandHeader}>
                            <button
                                className={styles.brand}
                                type="button"
                                onClick={() => {
                                    onClose()
                                    navigate('/')
                                }}
                            >
                                Momozon
                            </button>

                            <button
                                className={styles.closeButton}
                                type="button"
                                aria-label="Close categories"
                                onClick={onClose}
                            >
                                <X size={25} />
                            </button>
                        </div>

                        <h2 className={styles.categoryHeading}>
                            Categories
                        </h2>

                        <nav
                            className={styles.rootPanel}
                            aria-label="Main categories"
                        >
                            {rootCategories.map(category => {
                                const active =
                                    category.id === activeRootId

                                return (
                                    <button
                                        className={`${styles.rootItem} ${
                                            active
                                                ? styles.activeRoot
                                                : ''
                                        }`}
                                        type="button"
                                        key={category.id}
                                        onMouseEnter={() =>
                                            selectRoot(category.id)
                                        }
                                        onFocus={() =>
                                            selectRoot(category.id)
                                        }
                                        onClick={() =>
                                            openRootCategory(
                                                category.id
                                            )
                                        }
                                    >
                                        <span>
                                            {category.name}
                                        </span>

                                        <ChevronRight
                                            size={18}
                                            strokeWidth={1.8}
                                        />
                                    </button>
                                )
                            })}
                        </nav>
                    </div>

                    <section className={styles.megaPanel}>
                        {activeRoot && (
                            <>
                                <button
                                    className={styles.megaTitle}
                                    type="button"
                                    onClick={() =>
                                        openRootCategory(
                                            activeRoot.id
                                        )
                                    }
                                >
                                    {activeRoot.name}
                                </button>

                                {levelTwoCategories.length > 0 ? (
                                    <div className={styles.groups}>
                                        {levelTwoCategories.map(
                                            levelTwo => {
                                                const levelThree =
                                                    getChildren(
                                                        levelTwo.id
                                                    )

                                                return (
                                                    <section
                                                        className={
                                                            styles.group
                                                        }
                                                        key={
                                                            levelTwo.id
                                                        }
                                                    >
                                                        <button
                                                            className={
                                                                styles.groupTitle
                                                            }
                                                            type="button"
                                                            onClick={() =>
                                                                openProductCategory(
                                                                    levelTwo.id
                                                                )
                                                            }
                                                        >
                                                            {
                                                                levelTwo.name
                                                            }
                                                        </button>

                                                        <ul
                                                            className={
                                                                styles.childList
                                                            }
                                                        >
                                                            {levelThree.map(
                                                                levelThreeCategory => (
                                                                    <li
                                                                        key={
                                                                            levelThreeCategory.id
                                                                        }
                                                                    >
                                                                        <button
                                                                            className={
                                                                                styles.childItem
                                                                            }
                                                                            type="button"
                                                                            onClick={() =>
                                                                                openProductCategory(
                                                                                    levelThreeCategory.id
                                                                                )
                                                                            }
                                                                        >
                                                                            {
                                                                                levelThreeCategory.name
                                                                            }
                                                                        </button>
                                                                    </li>
                                                                )
                                                            )}
                                                        </ul>
                                                    </section>
                                                )
                                            }
                                        )}
                                    </div>
                                ) : (
                                    <p className={styles.empty}>
                                        No subcategories
                                    </p>
                                )}
                            </>
                        )}
                    </section>
                </div>
            </aside>
        </div>
    )
}

export default CategoryMenu