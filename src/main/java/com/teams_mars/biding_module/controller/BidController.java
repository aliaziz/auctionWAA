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
        bidService.placeBid(2, 1, 400);
        bidService.placeBid(2, 3, 500);
        String error3Message = bidService.placeBid(2, 4, 600);


        bidService.placeBid(1, 3, 560);
        bidService.placeBid(1, 4, 605);

        bidService.placeBid(3, 4, 610);
        bidService.placeBid(5, 4, 620);

        bidService.test(3);
        bidService.test(4);
        return "index";
    }
}
