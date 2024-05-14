package makaroshyna.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.category.CategoryResponseDto;
import makaroshyna.onlinebookstore.dto.category.CreateCategoryRequestDto;
import makaroshyna.onlinebookstore.service.category.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "Endpoints for managing categories")
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get all categories",
            description = "Get a list of all available categories")
    public List<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID",
            description = "Get a category by ID, if there is one")
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new category",
            description = "Create a new category with generated ID")
    public CategoryResponseDto createCategory(@RequestBody @Valid CreateCategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    @Operation(summary = "Update a category by ID",
            description = "Get and update a category by ID, if there is one")
    public CategoryResponseDto updateCategoryById(
            @PathVariable Long id,
            @RequestBody @Valid CreateCategoryRequestDto requestDto
    ) {
        return categoryService.updateById(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    @Operation(summary = "Delete a category by ID",
            description = "Delete a category by ID, if there is one")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
