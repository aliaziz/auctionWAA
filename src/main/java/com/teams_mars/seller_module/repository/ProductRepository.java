package com.teams_mars.seller_module.repository;

import com.teams_mars.seller_module.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> { }
