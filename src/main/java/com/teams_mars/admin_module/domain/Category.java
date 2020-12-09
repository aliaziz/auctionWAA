package com.teams_mars.admin_module.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//new的时候没有自动生成
    private Long category_id;
    @NotBlank
    @NotEmpty(message = "name can not be empty")
    private String name;
    @NotEmpty(message = "description can not be empty")
    @Size(min = 8, max = 50)
    private String description;

    @ManyToMany
    /*@JoinTable(name = "category_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))*/
    private List<Product_temp> productList;
}
