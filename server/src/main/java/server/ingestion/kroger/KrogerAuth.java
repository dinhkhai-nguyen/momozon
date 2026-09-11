package server.ingestion.kroger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class KrogerAuth {

    private final String clientId;
    private final String clientSecret;

    private String accessToken;
    private Instant expiresAt;

    private static final String URL = "https://api.kroger.com/v1/connect/oauth2/token";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper;

    public KrogerAuth(
            @Value("${kroger.client-id}") String clientId,
            @Value("${kroger.client-secret}") String clientSecret,
            ObjectMapper objectMapper
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.objectMapper = objectMapper;
    }

    public String getAccessToken() {
        if (hasValidToken()) return accessToken;

        return requestNewToken();
    }

    private boolean hasValidToken() {
        return accessToken != null && expiresAt != null && Instant.now().isBefore(expiresAt);
    }

    private String requestNewToken() {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header(
                        "Authorization",
                        "Basic " + encodedCredentials
                )
                .header(
                        "Content-Type",
                        "application/x-www-form-urlencoded"
                )
                .POST(
                        HttpRequest.BodyPublishers.ofString(
                                "grant_type=client_credentials&scope=product.compact"
                        )
                )
                .build();

        try {

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode jsonNode = objectMapper.readTree(response.body());
            accessToken = jsonNode.get("access_token").asText();
            long expiresIn = jsonNode.get("expires_in").asLong();
            expiresAt = Instant.now().plusSeconds(expiresIn);

            return accessToken;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}