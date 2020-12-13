package com.teams_mars.biding_module.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teams_mars._global_domain.User;
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
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JsonIgnore
    private User customer;
    private double price;
    private LocalDateTime bidDate;

    public Bid(double price, Product product, User customer) {
        this.price = price;
        this.product = product;
        this.customer = customer;
    }
}
