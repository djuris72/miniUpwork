package rs.ac.singidunum.miniUpwork.service;

import java.util.List;

import org.springframework.stereotype.Service;

import rs.ac.singidunum.miniUpwork.exception.BusinessException;
import rs.ac.singidunum.miniUpwork.exception.ResourceNotFoundException;
import rs.ac.singidunum.miniUpwork.model.Category;
import rs.ac.singidunum.miniUpwork.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(
            CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"));
    }

    public Category save(Category category) {

        if(categoryRepository.existsByName(category.getName())) {
            throw new BusinessException(
                    "Category already exists");
        }

        return categoryRepository.save(category);
    }

    public Category update(Long id, Category updatedCategory) {

        Category category = findById(id);

        category.setName(updatedCategory.getName());

        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.delete(findById(id));
    }
}
