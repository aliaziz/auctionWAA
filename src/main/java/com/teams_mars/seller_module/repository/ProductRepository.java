package com.teams_mars.seller_module.repository;

import com.teams_mars.seller_module.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product,Integer> {

    @Query("select p from Product p where p.isClosed = false")
    List<Product> getActiveProducts();
}
