package com.teams_mars.seller_module.service;

import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> getProduct(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
         Iterable<Product> it = productRepository.findAll();
         List<Product> list = new ArrayList<>();
         it.forEach(list::add);
         return list;
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.getActiveProducts();
    }

    @Override
    public List<Product> getAllProductsByPage(int pageNum, String attr, boolean isDesc) {
        Sort sort = Sort.by(attr).descending();
        if (!isDesc) sort.ascending();

        Pageable pageable = PageRequest.of(pageNum, 2, sort);
        Page<Product> productList = productRepository.findAll(pageable);
        return productList.toList();

    }
}
