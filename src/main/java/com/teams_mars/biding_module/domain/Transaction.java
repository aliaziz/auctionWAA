package com.teams_mars.biding_module.domain;

import com.teams_mars._global_domain.User;
import com.teams_mars.seller_module.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @ManyToOne
    private User buyer;

    @ManyToOne
    private User seller;

    @ManyToOne
    private Product product;
    private double amount;
    private LocalDateTime transactionDate;
}
