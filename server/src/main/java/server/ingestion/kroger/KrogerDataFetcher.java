package server.ingestion;

public class KrogerDataFetcher implements SupplierDataFetcher<KrogerFetchRequest> {

    private final KrogerAuth auth;

    public KrogerDataFetcher(KrogerAuth auth) {
        this.auth = auth;
    }

    @Override
    public RawSupplierData fetch(KrogerFetchRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}