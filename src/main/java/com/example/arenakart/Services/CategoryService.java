package com.example.arenakart.Services;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arenakart.DTO.CategoryDto;
import com.example.arenakart.Entities.Category;
import com.example.arenakart.Exceptions.ResourceNotFoundException;
import com.example.arenakart.Repositories.CategoryRepository;


import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    

    public CategoryService(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getRootCategories() {
        return categoryRepository.findByParentIsNull();
    }

    public List<Category> getSubCategories(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Transactional
    public Category createCategory(CategoryDto dto) {
        Category parent = null;
        if (dto.getParentId() != null) {
            parent = getCategoryById(dto.getParentId());
        }

        // Use plain constructor + setters instead of builder
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setParent(parent);

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, CategoryDto dto) {
        Category category = getCategoryById(id);

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        if (dto.getParentId() != null) {
            Category parent = getCategoryById(dto.getParentId());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
