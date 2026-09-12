package server.ingestion;

import commons.AvailabilityStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class NormalizedSupplierData {

    private String supplier;

    private String name;
    private String brand;
    private String modelNumber;
    private String gtin;
    private String description;
    private String category;

    private BigDecimal price;
    private String currency;
    private AvailabilityStatus availability;
    private Integer estimatedShippingDays;

    private String productUrl;

    private Instant fetchedAt;

    public NormalizedSupplierData(
            String supplier,
            String name,
            String brand,
            String modelNumber,
            String gtin,
            String description,
            String category,
            BigDecimal price,
            String currency,
            AvailabilityStatus availability,
            Integer estimatedShippingDays,
            String productUrl,
            Instant fetchedAt
    ) {
        this.supplier = supplier;
        this.name = name;
        this.brand = brand;
        this.modelNumber = modelNumber;
        this.gtin = gtin;
        this.description = description;
        this.category = category;
        this.price = price;
        this.currency = currency;
        this.availability = availability;
        this.estimatedShippingDays = estimatedShippingDays;
        this.productUrl = productUrl;
        this.fetchedAt = fetchedAt;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getGtin() {
        return gtin;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public AvailabilityStatus getAvailability() {
        return availability;
    }

    public Integer getEstimatedShippingDays() {
        return estimatedShippingDays;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public Instant getFetchedAt() {
        return fetchedAt;
    }
}