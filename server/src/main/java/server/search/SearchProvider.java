package server.search;

import server.search.dto.SearchResult;

public interface SearchProvider {

    SearchResult search(String query, String cursor, int batchSize);
}