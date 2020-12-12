package com.teams_mars.seller_module.controller;

import com.teams_mars.biding_module.service.BidService;
import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BidService bidService;

    @RequestMapping("/{productId}")
    public String viewProductDetails(@PathVariable int productId, Model model) {
        int customerId = 1;
        boolean isVerified = customerService.isCustomerVerified(customerId);
        boolean hasMadeDeposit = bidService.hasMadeDeposit(customerId, productId);
        Product product = productService.getProduct(productId).orElseThrow();
        model.addAttribute("isVerified", isVerified);
        model.addAttribute("hasMadeDeposit", hasMadeDeposit);
        model.addAttribute("product", product);
        model.addAttribute("imageList", productService.getProductImages(productId));
        model.addAttribute("highestBidPrice", bidService.getHighestBidPrice(productId));
        return "product/product_details";
    }

    @RequestMapping
    public String showProductLists(Model model) {

        List<Product> productList = customerService.viewPagedProductList(0,"startingPrice",false);
        model.addAttribute("productList", productList);
        model.addAttribute("isDesc", false);
        return "product/product_list";
    }

    @RequestMapping("/{pageNum}/{attr}/{isDesc}")
    public String showPagedProductLists(@PathVariable int pageNum,
                                          @PathVariable String attr,
                                          @PathVariable Boolean isDesc,
                                          Model model) {

        List<Product> productList = customerService.viewPagedProductList(pageNum,attr,isDesc);
        model.addAttribute("isDesc", isDesc);
        model.addAttribute("productList", productList);
        return "product/product_list";
    }

    @RequestMapping("/search")
    public String searchProducts(@RequestParam String query, Model model) {

        List<Product> productList = customerService.searchPagedProducts(0, query);
        model.addAttribute("productList", productList);
        return "product/product_list";
    }

    @ResponseBody
    @RequestMapping("/productReceived/{productId}")
    public boolean productReceived(@PathVariable int productId) {
        bidService.productReceived(productId);
        return true;
    }
}
