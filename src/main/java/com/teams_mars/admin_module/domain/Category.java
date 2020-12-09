package com.teams_mars.admin_module.domain;

import com.teams_mars.seller_module.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer category_id;
    private String name;

    @ManyToMany
    @JoinTable(name = "CategoryAndProduct",
            joinColumns = @JoinColumn(name = "category_id" ),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;
}
