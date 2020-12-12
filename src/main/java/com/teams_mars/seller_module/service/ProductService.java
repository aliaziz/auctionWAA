package com.teams_mars.seller_module.service;

import com.teams_mars.seller_module.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    Optional<Product> getProduct(int productId);
    void saveProduct(Product product);
    List<Product> getAllProducts();
    List<Product> getActiveProducts();
    List<Product> findProductByCategory(String category);

    List<Product> getAllProductsByPage(int pageNum, String attr, boolean isDesc);
    List<Product> searchProductsByName(int pageNum, String keyWord);
    List<String> getProductImages(int productId);

}
