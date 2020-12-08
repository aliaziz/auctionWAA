package com.teams_mars.biding_module.service;

import com.teams_mars.biding_module.repository.BidRepository;
import com.teams_mars.customer_module.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    CustomerService customerService;

    @Override
    public void placeBid(int customerId) {
        if (customerService.isBidEligible(customerId)) {

        }
    }
}
