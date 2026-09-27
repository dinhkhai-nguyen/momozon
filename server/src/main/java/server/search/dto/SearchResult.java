package server.search.dto;

import java.util.List;

public record SearchResult(
        List<ProductPreview> products,
        long totalProducts,
        boolean hasMore,
        String nextCursor
) {
}