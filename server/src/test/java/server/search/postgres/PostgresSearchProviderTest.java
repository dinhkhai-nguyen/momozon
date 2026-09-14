package server.search.postgres;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import commons.AvailabilityStatus;
import server.database.ProductRepository;
import server.search.dto.ProductPreview;
import server.search.dto.SearchResult;

@ExtendWith(MockitoExtension.class)
class PostgresSearchProviderTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PostgresSearchProvider searchProvider;

    @Test
    void searchWithoutCursorReturnsFirstPageAndNextCursor() {
        List<ProductPreview> products = List.of();

        when(productRepository.searchProducts(
                "ASUS",
                AvailabilityStatus.IN_STOCK,
                PageRequest.of(0, 40)
        )).thenReturn(products);

        when(productRepository.countByNameContainingIgnoreCase("ASUS"))
                .thenReturn(100L);

        SearchResult result =
                searchProvider.search("ASUS", null, 40);

        assertEquals(products, result.products());
        assertEquals(100L, result.totalProducts());
        assertTrue(result.hasMore());
        assertEquals("1", result.nextCursor());
    }
    @Test
    void searchOnLastPageHasNoNextCursor() {
        List<ProductPreview> products = List.of();

        when(productRepository.searchProducts(
                "ASUS",
                AvailabilityStatus.IN_STOCK,
                PageRequest.of(1, 40)
        )).thenReturn(products);

        when(productRepository.countByNameContainingIgnoreCase("ASUS"))
                .thenReturn(70L);

        SearchResult result =
                searchProvider.search("ASUS", "1", 40);

        assertEquals(products, result.products());
        assertEquals(70L, result.totalProducts());
        assertFalse(result.hasMore());
        assertNull(result.nextCursor());
    }

}