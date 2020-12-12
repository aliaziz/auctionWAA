package com.teams_mars.seller_module.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.WithHeldAmount;
import com.teams_mars.customer_module.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;




    @ManyToMany(mappedBy = "productList")
    @JsonBackReference
    //@JsonManagedReference
    private List<Category> category;
    @JsonBackReference
    public List<Category> getCategory() {
        return category;
    }

    @OneToMany(mappedBy = "product")
    private List<Bid> bidList;

    @OneToMany(mappedBy = "productHeld")
    private List<WithHeldAmount> withHeldAmountList;

    @ManyToOne
    private User owner;

    private double deposit;
    private double startingPrice;
    private String description;
    private LocalDateTime bidDueDate;
    private LocalDateTime bidPaymentDueDate;
    private boolean isClosed;
    private boolean isPaymentMade;
    private boolean isShipped;
    private boolean isReceived;
    private String imagePath;

    @Transient
    private MultipartFile[] multipartFiles;
    @Transient
    private String Name;
}
