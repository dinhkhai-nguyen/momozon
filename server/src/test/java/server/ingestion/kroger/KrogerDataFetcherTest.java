package server.ingestion.kroger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import server.ingestion.RawSupplierData;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KrogerDataFetcherTest {

    @Autowired
    private KrogerDataFetcher fetcher;

    @Test
    void shouldFetchKrogerProducts() {

        KrogerFetchRequest request =
                new KrogerFetchRequest(
                        "01400513",
                        "milk",
                        0,
                        5
                );

        RawSupplierData rawData = fetcher.fetch(request);

        assertNotNull(rawData);
        assertNotNull(rawData.getPayload());
        assertFalse(rawData.getPayload().isBlank());

        System.out.println(rawData.getPayload());
    }
}