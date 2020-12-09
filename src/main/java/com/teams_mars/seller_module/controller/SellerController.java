package com.teams_mars.seller_module.controller;

import com.teams_mars.seller_module.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class SellerController {
    @RequestMapping(value = "/product_add")
    public String inputProduct(@ModelAttribute("product") Product product){
        return "ProductForm";
    }

    @RequestMapping(value = {"product_save"})
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
                               Model model){
        System.out.println(product);
        if (bindingResult.hasErrors()){
            return "ProductForm";
        }
        String[] suppressedFields = bindingResult.getSuppressedFields();
        if (suppressedFields.length > 0){
            throw new RuntimeException("Attempt to bind fields that haven't been allowed in initBinder(): " +
                    StringUtils.addStringToArray(suppressedFields, ", "));
        }
        //save product
        model.addAttribute("product", product);

        return "ProductDetails";
    }

}
