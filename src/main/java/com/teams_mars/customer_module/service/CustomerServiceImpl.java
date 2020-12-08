package com.teams_mars.customer_module.service;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.customer_module.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public boolean isBidEligible(int customerId) {
        return customerRepository.isBidEligible(customerId);
    }

    @Override
    public boolean isSeller(int customerId, int productId) {
        return customerRepository.isSellerOfProduct(productId) == customerId;
    }

    @Override
    public Optional<User> getCustomer(int customerId) {
        return customerRepository.findById(customerId);
    }

}
