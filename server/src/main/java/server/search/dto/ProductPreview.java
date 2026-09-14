package server.search.dto;

import java.math.BigDecimal;


public record ProductPreview(
        Long productId,
        String name,
        String brand,
        String category,
        BigDecimal price,
        String currency
) {
}