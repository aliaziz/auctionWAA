package com.teams_mars.demo._global_domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer license_id;
    private String email;

    private String code;
}
