package server.search;

import org.springframework.stereotype.Service;

import server.search.dto.SearchQuery;
import server.search.dto.SearchResult;

@Service
public class SearchService {

    private static final int RESULTS_PER_BATCH = 40;

    private final SearchProvider searchProvider;

    public SearchService(SearchProvider searchProvider) {
        this.searchProvider = searchProvider;
    }

    public SearchResult search(SearchQuery searchQuery) {
        String normalizedQuery = null;

        if (searchQuery.query() != null && !searchQuery.query().isBlank()) {
            normalizedQuery = searchQuery.query()
                    .trim()
                    .replaceAll("\\s+", " ");
        }

        if (normalizedQuery == null && searchQuery.categoryId() == null) {
            throw new IllegalArgumentException("A search query or category must be provided");
        }

        SearchQuery normalizedSearchQuery = new SearchQuery(
                normalizedQuery,
                searchQuery.categoryId(),
                searchQuery.filters(),
                searchQuery.cursor()
        );

        return searchProvider.search(normalizedSearchQuery, RESULTS_PER_BATCH);
    }
}