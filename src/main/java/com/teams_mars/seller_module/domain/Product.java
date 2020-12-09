package com.teams_mars.seller_module.domain;

import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.WithHeldAmount;
import com.teams_mars.customer_module.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    @ManyToMany(mappedBy = "productList")
    private List<Category> category;

    @OneToMany(mappedBy = "product")
    private List<Bid> bidList;

    @OneToMany(mappedBy = "productHeld")
    private List<WithHeldAmount> withHeldAmountList;

    @OneToMany
    private List<ProductImage> imagePaths;

    @ManyToOne
    private User owner;

    private double deposit;
    private double startingPrice;
    private String description;
    private LocalDate bidDueDate;
    private LocalDate bidPaymentDueDate;
    private boolean isClosed;
    private boolean isPaymentMade;
    private boolean isShipped;
    private boolean isReceived;
}
