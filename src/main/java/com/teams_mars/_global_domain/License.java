package com.teams_mars._global_domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer license_id;
    private String email;

    private String code;
}
