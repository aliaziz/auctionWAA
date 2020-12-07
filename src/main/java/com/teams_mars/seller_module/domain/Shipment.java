package com.teams_mars.seller_module.domain;

import com.teams_mars.customer_module.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer shipmentId;

    @ManyToOne
    private User customer;

    @OneToOne
    private Product product;

    private LocalDate day;
    private boolean isShipped;
    private boolean isReceived;
}
