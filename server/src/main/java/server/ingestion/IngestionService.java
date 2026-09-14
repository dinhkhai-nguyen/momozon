package server.ingestion;

import commons.Category;
import commons.Offer;
import commons.Product;
import commons.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import server.database.CategoryRepository;
import server.database.OfferRepository;
import server.database.ProductRepository;
import server.database.SupplierRepository;

@Service
public class IngestionService {

    private final ProductMatcher productMatcher;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final OfferRepository offerRepository;
    private final CategoryRepository categoryRepository;

    public IngestionService(
            ProductMatcher productMatcher,
            ProductRepository productRepository,
            SupplierRepository supplierRepository,
            OfferRepository offerRepository,
            CategoryRepository categoryRepository
    ) {
        this.productMatcher = productMatcher;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void ingest(NormalizedData data) {
        Supplier supplier = supplierRepository.findByName(data.getSupplier());

        if (supplier == null) {
            throw new IllegalStateException("Supplier not found: " + data.getSupplier());
        }

        Product product = productMatcher.match(data);

        if (product == null) {
            Category category = categoryRepository.findByName("UNCATEGORIZED");

            if (category == null) {
                throw new IllegalStateException("Category not found: UNCATEGORIZED");
            }

            product = new Product(
                    data.getName(),
                    data.getDescription(),
                    data.getBrand(),
                    data.getModelNumber(),
                    data.getGtin(),
                    category
            );

            productRepository.save(product);
        }

        Offer offer = offerRepository.findByProductAndSupplier(product, supplier);

        if (offer == null) {
            offer = new Offer(
                    product,
                    supplier,
                    data.getPrice(),
                    data.getCurrency(),
                    data.getAvailability(),
                    null,
                    data.getFetchedAt()
            );

            offerRepository.save(offer);
            return;
        }

        if (!data.getFetchedAt().isAfter(offer.getLastCheckedAt())) return;

        offer.setPrice(data.getPrice());
        offer.setCurrency(data.getCurrency());
        offer.setAvailability(data.getAvailability());
        offer.setLastCheckedAt(data.getFetchedAt());

        offerRepository.save(offer);
    }
}