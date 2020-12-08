package com.teams_mars.customer_module.controller;

import com.teams_mars.customer_module.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/product/{productId}")
    public ModelAndView viewProductDetails(@PathVariable int productId) {
        int customerId = 1;
        boolean bidEligible = customerService.isBidEligible(customerId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetails");
        modelAndView.addObject("isEligible", bidEligible);
        return modelAndView;
    }
}
