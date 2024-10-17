package com.api.budget_bridge.service;

import com.api.budget_bridge.model.Category;
import com.api.budget_bridge.repository.CategoryRepositoy;
import com.api.budget_bridge.rest.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepositoy categoryRepositoy;

    @Autowired
    public CategoryService(CategoryRepositoy categoryRepositoy) {
        this.categoryRepositoy = categoryRepositoy;
    }

    @Transactional
    public void save(CategoryDTO categoryDTO) {
        categoryRepositoy.save(categoryDTO.toModel());
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategories() {
        return categoryRepositoy.findAll().stream()
                .map(Category::toDto)
                .toList();
    }
}
