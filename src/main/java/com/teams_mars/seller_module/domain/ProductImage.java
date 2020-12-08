package com.teams_mars.seller_module.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productImageId;
    private String imagePath;
    @OneToOne
    private Product product;
}
