package server.search;

import server.search.dto.SearchQuery;
import server.search.dto.SearchResult;

public interface SearchProvider {

    SearchResult search(SearchQuery searchQuery, int batchSize);
}