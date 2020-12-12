package com.teams_mars.admin_module.repository;


import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.seller_module.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Override
    //@Query(value = "select c from Category c")//手动输入的数据里有信息
    List<Category> findAll();
    //@Query(value = "select c from #{#entityName} c where c.name = :name ")
    //Category findCategoryByName(String name); //this is interface
 /*   @Query("select p from Product p where p.category.category_id = :categoryId")
    List<Product>  findProductsByCategoryId(String categoryId);*/

  /*  @Query("SELECT p FROM Product p join p.category c WHERE c.category_id = :categoryId")
    List<Product>  findProductsByCategoryId2(Long categoryId);*/
      @Query("select p from Product p join p.category c where c.category_id = :categoryId")
    List<Product>  findProductsByCategoryId2(Long categoryId);


    /*
    @Query("select p.owner.userId from Product p where p.productId = :productId")
    Integer isSellerOfProduct(Integer productId);*/



}
