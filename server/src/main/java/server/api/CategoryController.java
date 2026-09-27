package server.api;

import commons.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    public record CategoryResponse(long id, String name, Long parentId) {

        public static CategoryResponse from(Category category) {
            Long parentId = category.getParent() == null ? null : category.getParent().getId();
            return new CategoryResponse(category.getId(), category.getName(), parentId);
        }
    }
}