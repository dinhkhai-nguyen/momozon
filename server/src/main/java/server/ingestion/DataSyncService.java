package server.ingestion;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DataSyncService {

    private final IngestionService ingestionService;

    public DataSyncService(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    public <Q> void sync(DataFetcher<Q> fetcher, DataNormalizer normalizer, Q query) {
        RawData rawData = fetcher.fetch(query);

        List<NormalizedData> normalizedData = normalizer.normalize(rawData);

        for (NormalizedData data : normalizedData) ingestionService.ingest(data);
    }
}