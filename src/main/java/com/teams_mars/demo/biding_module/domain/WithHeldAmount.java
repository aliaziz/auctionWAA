package com.teams_mars.demo.biding_module.domain;

import com.teams_mars.demo.seller_module.domain.Product;
import com.teams_mars.demo.customer_module.domain.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class WithHeldAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer withHeldAmountId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User customer;
}
