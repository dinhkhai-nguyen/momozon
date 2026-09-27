package server.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.search.SearchService;
import server.search.dto.SearchQuery;
import server.search.dto.SearchResult;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private static final Set<String> CORE_PARAMETERS = Set.of("query", "categoryId", "cursor");

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public SearchResult search(@RequestParam MultiValueMap<String, String> parameters) {
        String query = parameters.getFirst("query");
        String cursor = parameters.getFirst("cursor");

        Long categoryId = null;

        String categoryIdValue = parameters.getFirst("categoryId");

        if (categoryIdValue != null) categoryId = Long.valueOf(categoryIdValue);

        Map<String, List<String>> filters = new LinkedHashMap<>();

        parameters.forEach((key, values) -> {
            if (!CORE_PARAMETERS.contains(key)) filters.put(key, values);
        });

        SearchQuery searchQuery = new SearchQuery(query, categoryId, filters, cursor);

        return searchService.search(searchQuery);
    }
}