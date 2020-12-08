package com.teams_mars.customer_module.service;

import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    boolean isBidEligible(int customerId);
}
