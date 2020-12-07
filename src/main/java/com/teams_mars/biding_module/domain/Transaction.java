package com.teams_mars.biding_module.domain;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.seller_module.domain.Product;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @ManyToOne
    private User buyer;

    @ManyToOne
    private Product product;
    private double amount;
    private LocalDate transactionDate;
}
