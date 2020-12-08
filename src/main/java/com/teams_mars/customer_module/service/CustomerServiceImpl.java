package com.teams_mars.customer_module.service;

import com.teams_mars.customer_module.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public boolean isBidEligible(int customerId) {
        return customerRepository.isBidEligible(customerId);
    }
}
