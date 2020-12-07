package com.teams_mars.customer_module.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;

    private String street;
    private Integer zipCode;
    private String city;
    private String state;

}
