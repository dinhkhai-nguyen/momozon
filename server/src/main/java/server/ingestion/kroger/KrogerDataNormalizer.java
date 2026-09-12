package server.ingestion.kroger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.AvailabilityStatus;
import org.springframework.stereotype.Component;
import server.ingestion.DataNormalizer;
import server.ingestion.NormalizedData;
import server.ingestion.RawData;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class KrogerDataNormalizer implements DataNormalizer {

    private static final String SUPPLIER = "KROGER";
    private static final String CURRENCY = "USD";
    private static final String BASE_URL = "https://www.kroger.com";

    private final ObjectMapper objectMapper;

    public KrogerDataNormalizer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<NormalizedData> normalize(RawData rawData) {
        try {
            JsonNode root = objectMapper.readTree(rawData.getPayload());
            JsonNode products = root.path("data");

            if (!products.isArray()) {
                throw new IllegalArgumentException("Kroger response does not contain a valid data array");
            }

            List<NormalizedData> result = new ArrayList<>(products.size());

            for (JsonNode product : products) result.add(normalizeProduct(product, rawData.getFetchedAt()));

            return result;

        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to parse Kroger response", e);
        }
    }

    private NormalizedData normalizeProduct(JsonNode product, Instant fetchedAt) {
        String name = product.path("description").asText(null);

        String brand = product.path("brand").asText(null);

        String gtin = product.path("upc").asText(null);

        String productUrl = extractProductUrl(product);

        JsonNode item = extractFirstItem(product);

        BigDecimal price = extractPrice(item);

        AvailabilityStatus availability = extractAvailability(item);

        return new NormalizedData(
                SUPPLIER,
                name,
                brand,
                null,
                gtin,
                null,
                price,
                CURRENCY,
                availability,
                productUrl,
                fetchedAt
        );
    }

    private JsonNode extractFirstItem(JsonNode product) {
        JsonNode items = product.path("items");

        if (!items.isArray() || items.isEmpty()) {
            return null;
        }

        return items.get(0);
    }

    private BigDecimal extractPrice(JsonNode item) {
        if (item == null) return null;

        JsonNode priceNode = item.path("price").path("regular");

        if (!priceNode.isNumber()) return null;

        return priceNode.decimalValue();
    }

    private AvailabilityStatus extractAvailability(JsonNode item) {
        if (item == null) return AvailabilityStatus.UNKNOWN;

        String stockLevel = item
                .path("inventory")
                .path("stockLevel")
                .asText(null);

        return normalizeAvailability(stockLevel);
    }

    private AvailabilityStatus normalizeAvailability(String stockLevel) {
        if (stockLevel == null) return AvailabilityStatus.UNKNOWN;

        return switch (stockLevel.toUpperCase(Locale.ROOT)) {
            case "HIGH", "LOW" -> AvailabilityStatus.IN_STOCK;

            case "OUT_OF_STOCK", "TEMPORARILY_OUT_OF_STOCK" -> AvailabilityStatus.OUT_OF_STOCK;

            default -> AvailabilityStatus.UNKNOWN;
        };
    }

    private String extractProductUrl(JsonNode product) {
        String relativeUrl = product.path("productPageURI").asText(null);

        if (relativeUrl == null || relativeUrl.isBlank()) return null;

        // Remove Kroger tracking query parameters such as ?cid=...
        int queryIndex = relativeUrl.indexOf('?');

        if (queryIndex >= 0) relativeUrl = relativeUrl.substring(0, queryIndex);

        return BASE_URL + relativeUrl;
    }
}