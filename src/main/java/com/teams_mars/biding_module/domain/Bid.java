package com.teams_mars.biding_module.domain;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.seller_module.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User customer;
    private double price;
    private LocalDateTime bidDate;

    public Bid(double price, Product product, User customer) {
        this.price = price;
        this.product = product;
        this.customer = customer;
    }
}
