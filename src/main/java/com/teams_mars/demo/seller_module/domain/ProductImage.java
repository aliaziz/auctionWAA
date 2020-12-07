package com.teams_mars.demo.seller_module.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productImageId;
    private String imagePath;
    @OneToOne
    private Product product;
}
