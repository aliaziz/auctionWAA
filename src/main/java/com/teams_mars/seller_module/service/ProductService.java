package com.teams_mars.seller_module.service;

import com.teams_mars.seller_module.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService {
    Optional<Product> getProduct(int productId);
}
