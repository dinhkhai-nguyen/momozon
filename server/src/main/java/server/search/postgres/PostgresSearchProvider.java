package server.search.postgres;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import commons.AvailabilityStatus;
import server.database.ProductRepository;
import server.search.SearchProvider;
import server.search.dto.ProductPreview;
import server.search.dto.SearchQuery;
import server.search.dto.SearchResult;

@Component
public class PostgresSearchProvider
        implements SearchProvider {

    private final ProductRepository productRepository;

    public PostgresSearchProvider(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public SearchResult search(SearchQuery searchQuery, int batchSize) {
        int page = searchQuery.cursor() == null ? 0 : Integer.parseInt(searchQuery.cursor());

        Pageable pageable = PageRequest.of(page, batchSize);

        List<ProductPreview> products = productRepository.searchProducts(
                searchQuery.query(),
                searchQuery.categoryId(),
                AvailabilityStatus.IN_STOCK,
                pageable
        );

        long totalResults = productRepository.countSearchProducts(
                searchQuery.query(),
                searchQuery.categoryId(),
                AvailabilityStatus.IN_STOCK
        );

        boolean hasMore = (long) (page + 1) * batchSize < totalResults;

        String nextCursor = hasMore ? String.valueOf(page + 1) : null;

        return new SearchResult(
                products,
                totalResults,
                hasMore,
                nextCursor
        );
    }
}