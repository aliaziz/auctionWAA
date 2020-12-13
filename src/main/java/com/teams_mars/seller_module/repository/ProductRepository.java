package com.teams_mars.seller_module.repository;

import com.teams_mars.seller_module.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product,Integer> {

    @Query("select p from Product p where p.isClosed = false")
    List<Product> getActiveProducts();
    List<Product> findAllByOwner_UserId(int user_Id);
    //@Query("select p.owner.userId from Product p where p.productId = :productId")
    //Integer isSellerOfProduct(Integer productId);
    //List<Product> findProductByName(String name);
    List<Product> findProductByCategory(String category);

    Page<Product> findAllByDescription(String keyWord, Pageable pageable);
//    Page<Product> findAllByDescription(Pageable pageable);



}
