package com.teams_mars.customer_module.service;

import com.teams_mars._global_domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerService {
    boolean isCustomerVerified(int customerId);
    boolean isSeller(int customerId, int productId);
    Optional<User> getCustomer(int customerId);
}
