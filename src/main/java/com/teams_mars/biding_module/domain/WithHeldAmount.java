package com.teams_mars.biding_module.domain;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.seller_module.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WithHeldAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer withHeldAmountId;

    @ManyToOne
    private Product productHeld;

    @ManyToOne
    private User customer;

    private double amount;
}
