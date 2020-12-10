package com.teams_mars.biding_module.domain;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.seller_module.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "product_product_Id")
)
public class BidWon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidWonId;

    private double bidFinalAmount;
    private double balanceAmount;
    private boolean hasCustomerPaid;
    private boolean isSellerPaid;
    private LocalDate dateWon;

    @ManyToOne
    private User bidWinner;

    @OneToOne
    private Product product;
}
