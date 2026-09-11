package server.ingestion;

import java.time.Instant;

public class RawSupplierData {

    private String supplier;
    private DataFormat format;
    private String payload;
    private Instant fetchedAt;

    public RawSupplierData(String supplier, DataFormat format, String payload, Instant fetchedAt) {
        this.supplier = supplier;
        this.format = format;
        this.payload = payload;
        this.fetchedAt = fetchedAt;
    }

    public String getSupplier() {
        return supplier;
    }

    public DataFormat getFormat() {
        return format;
    }

    public String getPayload() {
        return payload;
    }

    public Instant getFetchedAt() {
        return fetchedAt;
    }
}