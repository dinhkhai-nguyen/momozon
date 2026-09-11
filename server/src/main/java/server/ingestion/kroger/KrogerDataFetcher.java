package server.ingestion.kroger;

import org.springframework.stereotype.Component;
import server.ingestion.DataFormat;
import server.ingestion.RawSupplierData;
import server.ingestion.SupplierDataFetcher;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Component
public class KrogerDataFetcher implements SupplierDataFetcher<KrogerFetchRequest> {

    private final KrogerAuth auth;
    private final HttpClient httpClient;

    public KrogerDataFetcher(KrogerAuth auth) {
        this.auth = auth;
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public RawSupplierData fetch(KrogerFetchRequest request) {

        String encodedTerm = URLEncoder.encode(
                request.getSearchTerm(),
                StandardCharsets.UTF_8
        );

        String url =
                "https://api.kroger.com/v1/products"
                        + "?filter.term=" + encodedTerm
                        + "&filter.locationId=" + request.getLocationId()
                        + "&filter.start=" + request.getOffset()
                        + "&filter.limit=" + request.getLimit();

        URI uri = URI.create(url);

        String token = auth.getAccessToken();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return new RawSupplierData(
                    "KROGER",
                    DataFormat.JSON,
                    response.body(),
                    Instant.now()
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}