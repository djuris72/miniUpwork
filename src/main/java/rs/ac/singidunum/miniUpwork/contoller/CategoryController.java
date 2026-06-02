package rs.ac.singidunum.miniUpwork.contoller;

import org.springframework.web.bind.annotation.*;

import rs.ac.singidunum.miniUpwork.model.Category;
import rs.ac.singidunum.miniUpwork.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(
            @PathVariable Long id) {

        return categoryService.findById(id);
    }

    @PostMapping
    public Category create(
            @RequestBody Category category) {

        return categoryService.save(category);
    }
}