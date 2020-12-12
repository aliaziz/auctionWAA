package com.teams_mars.admin_module.impl;

import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.admin_module.repository.CategoryRepository;
import com.teams_mars.seller_module.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findAllExample(Category category) {
        Example<Category> categoryExample = Example.of(category);
        return categoryRepository.findAll(categoryExample);
    }
    @Override
    public boolean findOne(Category category) {
        Example<Category> tvExample = Example.of(category);
        Optional<Category> categoryOptional = categoryRepository.findOne(tvExample);
        if (categoryOptional.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Product>  findProductsByCategoryId2(Long categoryId) {
        return categoryRepository.findProductsByCategoryId2(categoryId);
    }

}
