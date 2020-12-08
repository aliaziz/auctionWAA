package com.teams_mars.biding_module.service;

import com.teams_mars.biding_module.domain.Bid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {
    String placeBid(int customerId, int productId, double price);
    List<Bid> getCustomerBidHistory(int customerId);
    List<Bid> getProductBidHistory(int productId);
}
