package server.search.dto;

import java.util.List;
import java.util.Map;

public record SearchQuery(
        String query,
        Long categoryId,
        Map<String, List<String>> filters,
        String cursor
) {
}