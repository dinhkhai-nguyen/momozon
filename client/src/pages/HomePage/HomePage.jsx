import { ArrowRight } from 'lucide-react'
import styles from './HomePage.module.css'

function HomePage() {
    return (
        <main className={styles.page}>
            <section className={styles.hero}>
                <div className={styles.heroContent}>
                    <h1 className={styles.heroTitle}>
                        German products,
                        <br />
                        closer to you.
                    </h1>

                    <p className={styles.heroDescription}>
                        Top brands. Better prices.
                        <br />
                        Delivered to Vietnam.
                    </p>

                    <button
                        className={styles.heroButton}
                        type="button"
                    >
                        Start shopping
                        <ArrowRight size={18} />
                    </button>
                </div>
            </section>
        </main>
    )
}

export default HomePage