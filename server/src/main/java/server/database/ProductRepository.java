package server.database;

import commons.AvailabilityStatus;
import commons.Product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import server.search.dto.ProductPreview;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    Product findByGtin(String gtin);

    Product findByBrandAndModelNumber(
            String brand,
            String modelNumber
    );

    @Query("""
        SELECT new server.search.dto.ProductPreview(
            p.id,
            p.name,
            p.brand,
            c.name,
            MIN(o.price),
            'EUR'
        )
        FROM Product p
        JOIN p.category c
        LEFT JOIN c.parent parentCategory
        LEFT JOIN Offer o
            ON o.product = p
            AND o.availability = :availability
        WHERE
            (
                :query IS NULL
                OR LOWER(p.name)
                    LIKE LOWER(
                        CONCAT(
                            '%',
                            CAST(:query AS String),
                            '%'
                        )
                    )
            )
            AND
            (
                :categoryId IS NULL
                OR c.id = :categoryId
                OR parentCategory.id = :categoryId
            )
        GROUP BY
            p.id,
            p.name,
            p.brand,
            c.name
        ORDER BY
            p.name,
            p.id
        """)
    List<ProductPreview> searchProducts(
            @Param("query") String query,
            @Param("categoryId") Long categoryId,
            @Param("availability")
            AvailabilityStatus availability,
            Pageable pageable
    );

    @Query("""
        SELECT COUNT(DISTINCT p.id)
        FROM Product p
        JOIN p.category c
        LEFT JOIN c.parent parentCategory
        LEFT JOIN Offer o
            ON o.product = p
            AND o.availability = :availability
        WHERE
            (
                :query IS NULL
                OR LOWER(p.name)
                    LIKE LOWER(
                        CONCAT(
                            '%',
                            CAST(:query AS String),
                            '%'
                        )
                    )
            )
            AND
            (
                :categoryId IS NULL
                OR c.id = :categoryId
                OR parentCategory.id = :categoryId
            )
        """)
    long countSearchProducts(
            @Param("query") String query,
            @Param("categoryId") Long categoryId,
            @Param("availability")
            AvailabilityStatus availability
    );
}