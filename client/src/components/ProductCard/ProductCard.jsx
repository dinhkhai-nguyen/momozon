import styles from './ProductCard.module.css'

function ProductCard({
    name,
    brand,
    categoryName,
    minPrice,
    currency
}) {
    const formattedPrice = new Intl.NumberFormat(
        'en-DE',
        {
            style: 'currency',
            currency
        }
    ).format(minPrice)

    return (
        <article className={styles.card}>
            <div className={styles.imagePlaceholder}>
                <span>No image</span>
            </div>

            <div className={styles.content}>
                {brand && (
                    <p className={styles.brand}>
                        {brand}
                    </p>
                )}

                <h2 className={styles.name}>
                    {name}
                </h2>

                {categoryName && (
                    <p className={styles.category}>
                        {categoryName}
                    </p>
                )}

                <p className={styles.price}>
                    From {formattedPrice}
                </p>
            </div>
        </article>
    )
}

export default ProductCard