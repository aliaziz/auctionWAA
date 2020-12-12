package com.teams_mars.seller_module.domain;

import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.WithHeldAmount;
import com.teams_mars.customer_module.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    @ManyToOne
    private User owner;

    @NotNull
    private Double deposit;

    @NotNull
    private Double startingPrice;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime bidDueDate;

    @NotNull
    @Future
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime bidPaymentDueDate;

    private boolean isClosed;
    private boolean isPaymentMade;
    private boolean isShipped;
    private boolean isReceived;
    private String imagePath;

    @Transient
//    @NotBlank
    private MultipartFile[] multipartFiles;
}

