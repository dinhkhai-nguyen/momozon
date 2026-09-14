package server.search.postgres;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import commons.AvailabilityStatus;
import server.database.ProductRepository;
import server.search.SearchProvider;
import server.search.dto.ProductPreview;
import server.search.dto.SearchResult;

@Component
public class PostgresSearchProvider implements SearchProvider {

    private final ProductRepository productRepository;

    public PostgresSearchProvider(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public SearchResult search(String query, String cursor, int resultLimit) {
        int page = cursor == null ? 0 : Integer.parseInt(cursor);

        Pageable pageable = PageRequest.of(page, resultLimit);

        List<ProductPreview> products = productRepository.searchProducts(query, AvailabilityStatus.IN_STOCK, pageable);

        long totalResults = productRepository.countByNameContainingIgnoreCase(query);

        boolean hasMore = (long) (page + 1) * resultLimit < totalResults;

        String nextCursor = hasMore ? String.valueOf(page + 1) : null;

        return new SearchResult(
                products,
                totalResults,
                hasMore,
                nextCursor
        );
    }
}