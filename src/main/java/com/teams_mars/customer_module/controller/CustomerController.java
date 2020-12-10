package com.teams_mars.customer_module.controller;

import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/{pageNum}/{attr}/{isDesc}")
    public String viewProductLists(@PathVariable int pageNum,
                                   @PathVariable String attr,@PathVariable Boolean isDesc, Model model) {
       List<Product> productList = customerService.viewPagedProductList(pageNum,attr,isDesc);
            model.addAttribute("pgProductList",productList);
        return "productList";
    }
}
