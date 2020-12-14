package com.teams_mars.seller_module.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teams_mars._global_domain.User;
import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.WithHeldAmount;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private List<Category> category;
    @JsonBackReference
    public List<Category> getCategory() {
        return category;
    }

    @OneToMany(mappedBy = "product")
    @JsonBackReference
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
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime bidDueDate;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime bidPaymentDueDate;

    private boolean isClosed;
    private boolean isPaymentMade;
    private boolean isShipped;
    private boolean isReceived;
    private String imagePath;
    private Integer imageCount;
    private Boolean isReleased;
    private boolean hasBid;
    private String categoryNameList;

    @Transient
    private MultipartFile[] multipartFiles;
}

