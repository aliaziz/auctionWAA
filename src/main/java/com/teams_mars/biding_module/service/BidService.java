package com.teams_mars.biding_module.service;

import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.BidWon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {
    String placeBid(int customerId, int productId, double price);
    List<Bid> getCustomerBidHistory(int customerId);
    List<Bid> getProductBidHistory(int productId);
    double getHighestBidPrice(int productId);
    boolean makeDeposit(double amount, int customerId, int productId);
    boolean makeFullPayment(BidWon bidWon);
    boolean paySeller(int productId);
    void productReceived(int productId);
    double getDepositAmount(int productId);
    double getBidBalanceAmount(int productId);
    boolean hasMadeDeposit(int customerId, int productId);
    BidWon getInvoice(int customerId, int productId);
    List<BidWon> getCustomerBidsWon(int customerId);
}
