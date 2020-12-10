package com.teams_mars.seller_module.controller;

import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.domain.ProductImage;
import com.teams_mars.seller_module.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SellerController {
    @Autowired
    ProductRepository productRepository;

    public static String uploadFolder = System.getProperty("user.dir")+"/uploads";
//    File file = new File(uploadFolder);
    @GetMapping("/product_add")
    public String inputProduct(@ModelAttribute("product") Product product){
        return "ProductForm";
    }

    @PostMapping("/product_save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
                               Model model) throws IOException {
        System.out.println(product);
        if (bindingResult.hasErrors()){
            return "ProductForm";
        }
        Path path = Paths.get(uploadFolder, product.getDescription());
        Files.createDirectory(path);
        String[] suppressedFields = bindingResult.getSuppressedFields();
        if (suppressedFields.length > 0){
            throw new RuntimeException("Attempt to bind fields that haven't been allowed " +
                    "in initBinder(): " + StringUtils.addStringToArray(suppressedFields, ", "));
        }
        //save product
        for (MultipartFile file: product.getMultipartFiles()){
            Path fileNameAndPath = Paths.get(path.toString(), file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
        }
        product.setImagePath(path.toString());
        productRepository.save(product);
        model.addAttribute("product", product);
        return "allProducts";
    }

    @GetMapping("/product/{id}")
    public String viewProduct(Integer id, Model model){
        //query DB for product with ID id
        //add product to model
        System.out.print(id);
        return "productDetails";
    }

    @PostMapping("/product_edit")
    public String editProduct(){
        return "0";
    }

//    @InitBinder
//    public void Binder(WebDataBinder binder) {
//        binder.setDisallowedFields("multipartFile");
//    }

}
