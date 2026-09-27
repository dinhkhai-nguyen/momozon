import { useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom'

import ProductCard from '../../components/ProductCard/ProductCard.jsx'
import styles from './SearchPage.module.css'

function SearchPage() {
    const [searchParams] = useSearchParams()

    const query = searchParams.get('query')
    const categoryId = searchParams.get('categoryId')

    const [products, setProducts] = useState([])
    const [totalProducts, setTotalProducts] = useState(0)
    const [hasMore, setHasMore] = useState(false)
    const [nextCursor, setNextCursor] = useState(null)

    const [loading, setLoading] = useState(false)
    const [loadingMore, setLoadingMore] = useState(false)
    const [error, setError] = useState(null)

    useEffect(() => {
        if (!query && !categoryId) {
            setProducts([])
            setTotalProducts(0)
            setHasMore(false)
            setNextCursor(null)
            return
        }

        async function searchProducts() {
            setLoading(true)
            setError(null)

            try {
                const parameters = new URLSearchParams()

                if (query) {
                    parameters.set('query', query)
                }

                if (categoryId) {
                    parameters.set(
                        'categoryId',
                        categoryId
                    )
                }

                const response = await fetch(
                    `/api/search?${parameters.toString()}`
                )

                if (!response.ok) {
                    throw new Error(
                        'Failed to search products'
                    )
                }

                const data = await response.json()

                setProducts(data.products)
                setTotalProducts(data.totalProducts)
                setHasMore(data.hasMore)
                setNextCursor(data.nextCursor)
            } catch (error) {
                console.error(error)

                setError(
                    'Could not load search results.'
                )
            } finally {
                setLoading(false)
            }
        }

        searchProducts()
    }, [query, categoryId])

    async function loadMoreProducts() {
        if (!nextCursor || loadingMore) {
            return
        }

        setLoadingMore(true)
        setError(null)

        try {
            const parameters = new URLSearchParams()

            if (query) {
                parameters.set('query', query)
            }

            if (categoryId) {
                parameters.set(
                    'categoryId',
                    categoryId
                )
            }

            parameters.set('cursor', nextCursor)

            const response = await fetch(
                `/api/search?${parameters.toString()}`
            )

            if (!response.ok) {
                throw new Error(
                    'Failed to load more products'
                )
            }

            const data = await response.json()

            setProducts(currentProducts => [
                ...currentProducts,
                ...data.products
            ])

            setHasMore(data.hasMore)
            setNextCursor(data.nextCursor)
        } catch (error) {
            console.error(error)

            setError(
                'Could not load more products.'
            )
        } finally {
            setLoadingMore(false)
        }
    }

    return (
        <main className={styles.page}>
            <h1 className={styles.heading}>
                {query
                    ? `Search results for "${query}"`
                    : 'Products'}
            </h1>

            {!loading && !error && (
                <p className={styles.resultCount}>
                    {totalProducts} products found
                </p>
            )}

            {loading && (
                <p className={styles.message}>
                    Loading products...
                </p>
            )}

            {error && (
                <p className={styles.error}>
                    {error}
                </p>
            )}

            {!loading &&
                !error &&
                products.length === 0 && (
                    <p className={styles.message}>
                        No products found.
                    </p>
                )}

            {!loading &&
                products.length > 0 && (
                    <section
                        className={styles.productGrid}
                        aria-label="Search results"
                    >
                        {products.map(product => (
                            <ProductCard
                                key={product.productId}
                                name={product.name}
                                brand={product.brand}
                                categoryName={
                                    product.category
                                }
                                minPrice={product.price}
                                currency={product.currency}
                            />
                        ))}
                    </section>
                )}

            {hasMore && !loading && (
                <div
                    className={
                        styles.loadMoreContainer
                    }
                >
                    <button
                        className={
                            styles.loadMoreButton
                        }
                        type="button"
                        onClick={loadMoreProducts}
                        disabled={loadingMore}
                    >
                        {loadingMore
                            ? 'Loading...'
                            : 'Show more'}
                    </button>
                </div>
            )}
        </main>
    )
}

export default SearchPage