package com.teams_mars._global_domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teams_mars._global_domain.License;
import com.teams_mars._global_domain.Role;
import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.WithHeldAmount;
import com.teams_mars.customer_module.domain.Address;
import com.teams_mars.seller_module.domain.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
//
//    @OneToOne
//    @JoinColumn(name = "role_id")
//    private Role_temp role;

    @NotBlank
    @Size(min = 2, max = 15)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 15)
    private String lastName;
    @Email
    @Column(name = "email")
    private String email;
    @NotBlank
    private String password;


//    @OneToOne
//    @JoinColumn(name = "license_id")
//    private License license;

    @OneToMany(mappedBy = "customer")
    private List<Bid> bidList;

    @OneToMany(mappedBy = "customer")
    private List<WithHeldAmount> withHeldAmountList;

    @OneToMany(mappedBy = "owner")
    private List<Product> productList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    @Valid
    private Address address;

    private boolean isProfileVerified;

    @NotBlank
    private String licenseNumber;

    @Column(name = "isVerified")
    private boolean verificationCodeVerified;
    @OneToOne()
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Role userRole;
}