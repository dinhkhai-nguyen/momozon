package server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.search.SearchService;
import server.search.dto.SearchResult;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public SearchResult search(
            @RequestParam("query") String query,
            @RequestParam(value = "cursor", required = false) String cursor
    ) {
        return searchService.search(query, cursor);
    }
}