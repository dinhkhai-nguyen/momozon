import { useEffect, useMemo, useState } from 'react'
import {
    Link,
    useParams
} from 'react-router-dom'

function CategoryPage() {
    const { categoryId } = useParams()

    const [categories, setCategories] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)

    useEffect(() => {
        async function loadCategories() {
            setLoading(true)
            setError(null)

            try {
                const response = await fetch('/api/categories')

                if (!response.ok) {
                    throw new Error(
                        'Failed to load categories'
                    )
                }

                const data = await response.json()

                setCategories(data)
            } catch (error) {
                console.error(error)

                setError(
                    'Could not load categories.'
                )
            } finally {
                setLoading(false)
            }
        }

        loadCategories()
    }, [])

    const currentCategory = useMemo(
        () =>
            categories.find(
                category =>
                    category.id === Number(categoryId)
            ),
        [categories, categoryId]
    )

    const levelTwoCategories = useMemo(
        () =>
            categories.filter(
                category =>
                    category.parentId ===
                    Number(categoryId)
            ),
        [categories, categoryId]
    )

    function getChildren(parentId) {
        return categories.filter(
            category =>
                category.parentId === parentId
        )
    }

    if (loading) {
        return (
            <main>
                <p>Loading categories...</p>
            </main>
        )
    }

    if (error) {
        return (
            <main>
                <p>{error}</p>
            </main>
        )
    }

    if (!currentCategory) {
        return (
            <main>
                <p>Category not found.</p>
            </main>
        )
    }

    return (
        <main>
            <h1>{currentCategory.name}</h1>

            <section>
                {levelTwoCategories.map(levelTwo => (
                    <article key={levelTwo.id}>
                        <h2>
                            <Link
                                to={`/search?categoryId=${levelTwo.id}`}
                            >
                                {levelTwo.name}
                            </Link>
                        </h2>

                        <ul>
                            {getChildren(
                                levelTwo.id
                            ).map(levelThree => (
                                <li key={levelThree.id}>
                                    <Link
                                        to={`/search?categoryId=${levelThree.id}`}
                                    >
                                        {levelThree.name}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </article>
                ))}
            </section>
        </main>
    )
}

export default CategoryPage