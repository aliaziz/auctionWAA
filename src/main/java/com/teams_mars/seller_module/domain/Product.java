package com.teams_mars.seller_module.domain;

import com.teams_mars.biding_module.domain.WithHeldAmount;
import com.teams_mars.customer_module.domain.User;
import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.biding_module.domain.Bid;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int product_id;

    @ManyToMany
    private List<Category> category;

    @OneToMany(mappedBy = "product")
    private List<Bid> bid;

    @OneToMany(mappedBy = "product")
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
}