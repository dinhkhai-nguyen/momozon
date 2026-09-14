package server.ingestion.kroger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.ingestion.DataSyncService;

@Component
public class KrogerScheduler {

    private final DataSyncService dataSyncService;
    private final KrogerDataFetcher dataFetcher;
    private final KrogerDataNormalizer dataNormalizer;

    public KrogerScheduler(
            DataSyncService dataSyncService,
            KrogerDataFetcher dataFetcher,
            KrogerDataNormalizer dataNormalizer
    ) {
        this.dataSyncService = dataSyncService;
        this.dataFetcher = dataFetcher;
        this.dataNormalizer = dataNormalizer;
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 30 * 60 * 1000L)
    public void schedule() {
        KrogerQuery query = new KrogerQuery(
                "01400513",
                "milk",
                0,
                50
        );

        dataSyncService.sync(dataFetcher, dataNormalizer, query);
    }
}