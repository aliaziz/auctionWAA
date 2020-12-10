package com.teams_mars.seller_module.service;

import com.teams_mars.seller_module.domain.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    Optional<Product> getProduct(int productId);
    void saveProduct(Product product);
    List<Product> getAllProducts();
    List<Product> getActiveProducts();
    List<Product> getAllProductsByPage(int pageNum, String attr, boolean isDesc);
}
