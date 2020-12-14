package com.teams_mars.biding_module.domain;

import com.teams_mars._global_domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class LocalPaypalAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paypalAccountId;

    @OneToOne
    private User customer;
    private double totalBalance;
    private double availableBalance;
    private double totalWithHeldAmount;
}
