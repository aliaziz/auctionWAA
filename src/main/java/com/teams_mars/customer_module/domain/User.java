package com.teams_mars.customer_module.domain;

import com.teams_mars._global_domain.License;
import com.teams_mars._global_domain.Role;
import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.WithHeldAmount;
import com.teams_mars.seller_module.domain.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String email;
    private String password;
    private String fullName;

    @OneToOne
    @JoinColumn(name = "license_id")
    private License license;

    @OneToMany(mappedBy = "customerList")
    private List<Bid> bidList;

    @OneToMany(mappedBy = "customer")
    private List<WithHeldAmount> withHeldAmountList;

    @OneToMany(mappedBy = "owner")
    private List<Product> productList;

    @OneToOne
    private Address address;

    private boolean isProfileVerified;
    private boolean isCodeVerified = false;
}
