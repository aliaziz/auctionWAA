package com.teams_mars.customer_module.service;

import com.teams_mars.customer_module.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerService {
    boolean isBidEligible(int customerId);
    boolean isSeller(int customerId, int productId);
    Optional<User> getCustomer(int customerId);
}
