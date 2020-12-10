package com.teams_mars.seller_module.domain;

import com.teams_mars.customer_module.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer shipmentId;

    @ManyToOne
    private User customer;

    @OneToOne
    private Product product;

    private LocalDateTime day;
    private boolean isShipped;
    private boolean isReceived;
}
