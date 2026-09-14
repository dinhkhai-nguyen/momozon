package server.ingestion;

import commons.Product;
import org.springframework.stereotype.Service;
import server.database.ProductRepository;

@Service
public class ProductMatcher {

    private final ProductRepository productRepository;

    public ProductMatcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product match(NormalizedData data) {

        if (data.getGtin() != null) {
            Product product = productRepository.findByGtin(data.getGtin());
            if (product != null) return product;
        }

        if (data.getBrand() != null && data.getModelNumber() != null) {
            Product product = productRepository.findByBrandAndModelNumber(data.getBrand(), data.getModelNumber());
            if (product != null) return product;
        }

        return null;
    }
}