package com.teams_mars.biding_module.controller;

import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bid")
public class BidController {

    @Autowired
    BidService bidService;

    @RequestMapping("/place/{productId}/{price}")
    @ResponseBody
    public String placeBid(@PathVariable int productId, @PathVariable int price) {
        int customerId = 1; //from Session
        bidService.placeBid(2, 3, 30000);
        bidService.placeBid(2, 3, 30001);
        bidService.placeBid(2, 3, 30002);
        bidService.placeBid(2, 3, 30003);
        String error3Message = bidService.placeBid(2, 4, 600);


        bidService.placeBid(1, 3, 30004);
        bidService.placeBid(1, 3, 30005);

        bidService.placeBid(1, 3, 30006);
        bidService.placeBid(1, 3, 30007);

        return "index";
    }

    @RequestMapping("/deposit/{productId}/{amount}")
    public String makeDeposit(@PathVariable int productId, @PathVariable int amount) {
        bidService.makeDeposit(30000, 2, 3);
        bidService.makeDeposit(30000, 1, 3);

        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/get/{productId}", method = RequestMethod.GET)
    public List<Bid> getBids(@PathVariable int productId) {
        return bidService.getProductBidHistory(productId);
    }

    @InitBinder
    public void Bind(WebDataBinder binder) {
        binder.setDisallowedFields("productImage");
    }
}
