package server.search;

import org.springframework.stereotype.Service;
import server.search.dto.SearchResult;

@Service
public class SearchService {

    private static final int RESULTS_PER_BATCH = 40;

    private final SearchProvider searchProvider;

    public SearchService(SearchProvider searchProvider) {
        this.searchProvider = searchProvider;
    }

    public SearchResult search(String query, String cursor) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search query cannot be empty");
        }

        String normalizedQuery = query.trim().replaceAll("\\s+", " ");

        return searchProvider.search(normalizedQuery, cursor, RESULTS_PER_BATCH);
    }
}