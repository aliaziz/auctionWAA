package com.teams_mars.biding_module.service;

import org.springframework.stereotype.Service;

@Service
public interface BidService {
    void placeBid(int customerId);
}
