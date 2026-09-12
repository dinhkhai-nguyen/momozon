package server.ingestion;

import commons.AvailabilityStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class NormalizedData {

    private String supplier;

    private String name;
    private String brand;
    private String modelNumber;
    private String gtin;
    private String description;

    private BigDecimal price;
    private String currency;
    private AvailabilityStatus availability;

    private String productUrl;

    private Instant fetchedAt;

    public NormalizedData(
            String supplier,
            String name,
            String brand,
            String modelNumber,
            String gtin,
            String description,
            BigDecimal price,
            String currency,
            AvailabilityStatus availability,
            String productUrl,
            Instant fetchedAt
    ) {
        this.supplier = supplier;
        this.name = name;
        this.brand = brand;
        this.modelNumber = modelNumber;
        this.gtin = gtin;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.availability = availability;
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

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public AvailabilityStatus getAvailability() {
        return availability;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public Instant getFetchedAt() {
        return fetchedAt;
    }
}