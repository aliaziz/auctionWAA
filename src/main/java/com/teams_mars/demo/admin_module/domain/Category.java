package com.teams_mars.demo.admin_module.domain;

import com.teams_mars.demo.seller_module.domain.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer category_id;
    private String name;

    @ManyToMany
    private List<Product> productList;
}
