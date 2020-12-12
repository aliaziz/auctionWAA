package com.teams_mars.seller_module.controller;

import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.repository.ProductRepository;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SellerController {
    //    @Autowired
//    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    public static String uploadFolder = System.getProperty("user.dir")+"/uploads";
//    File file = new File(uploadFolder);

    @GetMapping("/home")
    public String home(Model model){
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        return "product/product_list";
    }

    @GetMapping("/product_add")
    public String inputProduct(@ModelAttribute("product") Product product){

        return "product/product_form";
    }

    @PostMapping("/product_save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        System.out.println(product);
        if (bindingResult.hasErrors()){
            return "product/product_form";
        }
        Path path = Paths.get(uploadFolder, product.getName());
        try {
            Files.createDirectory(path);
        }
        catch (Exception e){
            System.out.print("Directory exists, we will just proceed " +
                    "to add in pics tho this is not the desired functionality");
        }
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
//        productRepository.save(product);
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("msg", "Product Successfully Added!!.");
        return "redirect:/home";
    }

    @GetMapping("/product/{id}")
    public String viewProduct(Integer id, Model model){
        //query DB for product with ID id
        //add product to model
        System.out.print(id);
        return "product/product_details";
    }

    @PostMapping("/product_edit")
    public String editProduct(){
        return "0";
    }


}
