package server.ingestion.kroger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import server.ingestion.NormalizedData;
import server.ingestion.RawData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KrogerDataFetcherTest {

    @Autowired
    private KrogerDataFetcher fetcher;

    @Autowired
    private KrogerDataNormalizer normalizer;

    @Test
    void shouldFetchKrogerProducts() {

        KrogerFetchRequest request =
                new KrogerFetchRequest(
                        "01400513",
                        "milk",
                        0,
                        5
                );

        RawData rawData = fetcher.fetch(request);
        List<NormalizedData> normalizedData =
                normalizer.normalize(rawData);

        assertFalse(normalizedData.isEmpty());

        NormalizedData first = normalizedData.get(0);

        assertEquals("KROGER", first.getSupplier());
        assertNotNull(first.getName());
        assertNotNull(first.getFetchedAt());

        System.out.println("Name: " + first.getName());
        System.out.println("Brand: " + first.getBrand());
        System.out.println("GTIN: " + first.getGtin());
        System.out.println("Price: " + first.getPrice());
        System.out.println("Currency: " + first.getCurrency());
        System.out.println("Availability: " + first.getAvailability());
        System.out.println("URL: " + first.getProductUrl());

        assertNotNull(rawData);
        assertNotNull(rawData.getPayload());
        assertFalse(rawData.getPayload().isBlank());

        System.out.println(rawData.getPayload());
    }
}