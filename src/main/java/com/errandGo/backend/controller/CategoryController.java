package com.errandGo.backend.controller;

import com.errandGo.backend.entities.Category;
import com.errandGo.backend.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    public record CategoryView(Long id, String name, String description) {
        static CategoryView from(Category c) {
            return new CategoryView(c.getId(), c.getName(), c.getDescription());
        }
    }

    public record CategoryRequest(@NotBlank @Size(max = 64) String name,
                                  @Size(max = 1000) String description) {}

    @GetMapping
    public List<CategoryView> getAllCategories() {
        return categoryService.getAllCategories().stream().map(CategoryView::from).toList();
    }

    @GetMapping("/{id}")
    public CategoryView getCategoryById(@PathVariable Long id) {
        return CategoryView.from(categoryService.getCategoryById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryView> createCategory(@Valid @RequestBody CategoryRequest r) {
        Category c = new Category();
        c.setName(r.name().trim());
        c.setDescription(r.description());
        return ResponseEntity.ok(CategoryView.from(categoryService.createCategory(c)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryView updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest r) {
        Category c = new Category();
        c.setName(r.name().trim());
        c.setDescription(r.description());
        return CategoryView.from(categoryService.updateCategory(id, c));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}