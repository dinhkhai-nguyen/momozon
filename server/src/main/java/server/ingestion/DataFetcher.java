package server.ingestion;

public interface DataFetcher<R> {

    RawData fetch(R request);
}