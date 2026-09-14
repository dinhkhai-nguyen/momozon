package server.search;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import server.search.dto.SearchResult;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private SearchProvider searchProvider;

    @InjectMocks
    private SearchService searchService;

    @Test
    void searchNormalizesQueryAndUsesFortyResultsPerBatch() {
        SearchResult expected =
                new SearchResult(List.of(), 0, false, null);

        when(searchProvider.search("ASUS laptop", null, 40))
                .thenReturn(expected);

        searchService.search("   ASUS    laptop   ", null);

        verify(searchProvider)
                .search("ASUS laptop", null, 40);
    }

    @Test
    void searchRejectsBlankQuery() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searchService.search("   ", null)
        );

        verifyNoInteractions(searchProvider);
    }
}