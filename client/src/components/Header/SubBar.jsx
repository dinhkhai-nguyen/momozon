import { useState } from 'react'
import { Menu } from 'lucide-react'

import CategoryMenu from './CategoryMenu.jsx'
import styles from './SubBar.module.css'

function SubBar() {
    const [categoriesOpen, setCategoriesOpen] = useState(false)

    function openCategories() {
        setCategoriesOpen(true)
    }

    function closeCategories() {
        setCategoriesOpen(false)
    }

    return (
        <>
            <nav
                className={styles.subBar}
                aria-label="Secondary navigation"
            >
                <button
                    className={styles.categories}
                    type="button"
                    onClick={openCategories}
                    aria-expanded={categoriesOpen}
                    aria-controls="category-menu"
                >
                    <Menu size={18} />
                    <span>Categories</span>
                </button>
            </nav>

            <CategoryMenu
                open={categoriesOpen}
                onClose={closeCategories}
            />
        </>
    )
}

export default SubBar