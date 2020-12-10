package com.teams_mars.admin_module.impl;

import com.teams_mars.admin_module.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public List<Category> findAll();

    public void save(Category category);
    public Category findCategoryById(Long id);
    public void deleteCategoryById(Long id);
    public List<Category> findAllExample(Category category);
    public boolean findOne(Category category);
}
