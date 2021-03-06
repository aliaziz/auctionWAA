package com.teams_mars.customer_module.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Size(min = 5, max = 20)
    private String street;

    @NotBlank
    private String city;
    @NotBlank
    @Size(min = 2, max = 2)
    private String state;
    @NotBlank
    private String zipcode;
}
