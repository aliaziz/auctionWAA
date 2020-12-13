package com.teams_mars.biding_module.domain;

import com.teams_mars._global_domain.User;
import com.teams_mars.seller_module.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PaypalTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paypalTransactionId;

    private String saleId;
    private String payerId;
    private String paymentId;
    private String paymentEnumType;
    private double amount;

    @ManyToOne
    private User customer;

    @ManyToOne
    private Product product;

}
