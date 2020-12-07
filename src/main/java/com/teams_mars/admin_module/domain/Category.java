package com.teams_mars.admin_module.domain;

import com.teams_mars.seller_module.domain.Product;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer category_id;
    @NotBlank
    private String name;
    @Size(min = 8, max = 50, message = "{Size.name.validation}")
    private String description;

    @OneToMany
    private List<Product> productList;
}
