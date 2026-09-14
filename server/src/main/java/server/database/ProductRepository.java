package server.database;

import commons.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByGtin(String gtin);

    Product findByBrandAndModelNumber(String brand, String modelNumber);
}
