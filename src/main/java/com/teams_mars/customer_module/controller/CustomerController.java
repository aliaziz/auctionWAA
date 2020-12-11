package com.teams_mars.customer_module.controller;

import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/{productId}")
    public ModelAndView viewProductDetails(@PathVariable int productId) {
        int customerId = 1;
        boolean bidEligible = customerService.isBidEligible(customerId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetails");
        modelAndView.addObject("isEligible", bidEligible);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/{pageNum}/{attr}/{isDesc}")
    public List<Product> viewProductLists(@PathVariable int pageNum,
                                   @PathVariable String attr,@PathVariable Boolean isDesc) {

        return customerService.viewPagedProductList(pageNum,attr,isDesc);
    }

    @ResponseBody
    @RequestMapping("/search/{pageNum}/{search}")
    public List<Product> searchProducts(@PathVariable int pageNum,
                                 @PathVariable String search) {
        return customerService.searchPagedProducts(pageNum, search);
    }
}
