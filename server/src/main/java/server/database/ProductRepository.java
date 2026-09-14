package server.database;

import commons.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import commons.AvailabilityStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.search.dto.ProductPreview;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByGtin(String gtin);

    Product findByBrandAndModelNumber(String brand, String modelNumber);

    long countByNameContainingIgnoreCase(String name);

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
        LEFT JOIN Offer o
            ON o.product = p
            AND o.availability = :availability
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))
        GROUP BY p.id, p.name, p.brand, c.name
        ORDER BY p.name, p.id
        """)
    List<ProductPreview> searchProducts(
            @Param("query") String query,
            @Param("availability") AvailabilityStatus availability,
            Pageable pageable
    );
}
