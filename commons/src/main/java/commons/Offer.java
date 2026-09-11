package commons;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
        indexes = {
                @Index(columnList = "product_id, price"),
                @Index(columnList = "supplier_id")
        }
)
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus availability;

    private Integer estimatedShippingDays;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    protected Offer() {
    }

    public Offer(
            Product product,
            Supplier supplier,
            BigDecimal price,
            AvailabilityStatus availability,
            Integer estimatedShippingDays
    ) {
        this.product = product;
        this.supplier = supplier;
        this.price = price;
        this.availability = availability;
        this.estimatedShippingDays = estimatedShippingDays;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AvailabilityStatus getAvailability() {
        return availability;
    }

    public Integer getEstimatedShippingDays() {
        return estimatedShippingDays;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailability(AvailabilityStatus availability) {
        this.availability = availability;
    }

    public void setEstimatedShippingDays(Integer estimatedShippingDays) {
        this.estimatedShippingDays = estimatedShippingDays;
    }
}