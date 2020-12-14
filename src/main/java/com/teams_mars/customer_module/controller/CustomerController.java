package com.teams_mars.customer_module.controller;

import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.BidWon;
import com.teams_mars.biding_module.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/customer")
@Controller
public class CustomerController {

    @Autowired
    private BidService bidService;

    @RequestMapping("/bidHistory")
    public String customerBidHistory(Model model) {
        int userId = (int) model.getAttribute("userId");
        List<Bid> customerBidHistory = bidService.getCustomerBidHistory(userId);
        model.addAttribute("bidHistory", customerBidHistory);
        return "product/bid_history";
    }

    @RequestMapping("/bidsWon")
    public String customerBidsWon(Model model) {
        int userId = (int) model.getAttribute("userId");
        List<BidWon> customerBidHistory = bidService.getCustomerBidsWon(userId);
        model.addAttribute("bidsWon", customerBidHistory);
        return "product/bids_won";
    }
}
