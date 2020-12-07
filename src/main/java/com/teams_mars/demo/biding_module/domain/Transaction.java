package com.teams_mars.demo.biding_module.domain;

import com.teams_mars.demo.seller_module.domain.Product;
import com.teams_mars.demo.customer_module.domain.User;
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
