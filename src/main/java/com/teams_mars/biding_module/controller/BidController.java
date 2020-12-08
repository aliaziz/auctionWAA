package com.teams_mars.biding_module.controller;

import com.teams_mars.biding_module.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bid")
public class BidController {

    @Autowired
    BidService bidService;

    @RequestMapping("/place/{productId}/{price}")
    @ResponseBody
    public String placeBid(@PathVariable int productId, @PathVariable int price) {
        int customerId = 1; //from Session
        String errorMessage = bidService.placeBid(2, 1, 400);
        String error2Message = bidService.placeBid(2, 3, 500);
        String error3Message = bidService.placeBid(2, 4, 600);

        System.out.println("Ending =="+bidService.getCustomerBidHistory(2));
        System.out.println(bidService.getProductBidHistory(1));
        return "index";
    }
}
