package com.teams_mars.biding_module.domain;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.seller_module.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "product")
)
public class BidWon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidWonId;

    private double bidFinalAmount;
    private double balanceAmount;
    private boolean hasCustomerPaid;
    private boolean isSellerPaid;

    @ManyToOne
    private User user;

    @OneToOne
    private Product product;
}
