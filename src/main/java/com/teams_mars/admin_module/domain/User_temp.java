package com.teams_mars.admin_module.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

//@Data
@Entity
@Data
public class User_temp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @NotEmpty(message = "user name can not be empty")
    @NotBlank
    private String userName;

    @NotBlank(message = "password can not be empty")
    @NotEmpty
    private String password;

    @ManyToMany
    private List<Role> roles = new ArrayList<>();
}
