package com.teams_mars.biding_module.domain;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.seller_module.domain.Product;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User customerList;
    private double price;

}
