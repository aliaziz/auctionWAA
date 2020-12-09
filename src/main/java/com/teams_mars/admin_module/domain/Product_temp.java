package com.teams_mars.admin_module.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product_temp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
    String name;
   /* @ManyToMany(mappedBy = "Category")
    List<Product> productList;*/


}
