package com.teams_mars.customer_module.domain;

import com.teams_mars._global_domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String verificationCode;
    private LocalDateTime timeIssued;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn()
    private User user;

    private int countTries;


}
