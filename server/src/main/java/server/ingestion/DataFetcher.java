package server.ingestion;

public interface DataFetcher<Q> {

    RawData fetch(Q query);
}