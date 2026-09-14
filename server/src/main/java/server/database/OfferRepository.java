package server.database;

import commons.Offer;
import commons.Product;
import commons.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findByProductAndSupplier(Product product, Supplier supplier);
}
