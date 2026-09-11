package server.ingestion;

public interface SupplierDataFetcher<R> {

    RawSupplierData fetch(R request);
}