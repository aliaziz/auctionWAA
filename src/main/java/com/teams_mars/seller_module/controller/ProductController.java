package com.teams_mars.seller_module.controller;

import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.admin_module.impl.CategoryService;
import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {
    //    @Autowired
//    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    public static String uploadFolder = System.getProperty("user.dir")+"/uploads";
//    File file = new File(uploadFolder);

    @GetMapping("/home")
    public String home(Model model){
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        model.addAttribute("seller", "6");
        return "product/product_list";
    }

    @GetMapping("/product/add")
    public String inputProduct(@ModelAttribute("product") Product product, Model model){
        List<Category> category = categoryService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        return "product/product_form";
    }

    @PostMapping("/product/save")
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

    @GetMapping("/myProducts")
    public String myProducts(Model model){
        List<Product> productList = productService.getAllSellerProducts(6);
        model.addAttribute("productList", productList);
        return "product/seller_product_list";
    }

    @GetMapping("/product/{productId}")
    public String viewProduct(@PathVariable int productId, Model model){
        System.out.print(productId);
        //query DB for product with ID id
        Product product = productService.getProduct(productId).orElseThrow();
        System.out.println(product.getCategory());
        for(Category cat:product.getCategory()){
            System.out.println(cat.getName());
        }
        //add product to model
        model.addAttribute("product", product);
        return "product/seller_product_details";
    }

    @GetMapping("/product/update/{productId}")
    public String updateProductForm(@PathVariable int productId, Model model){
        Product product = productService.getProduct(productId).orElseThrow();
        model.addAttribute("product", product);
        return "product/updateProduct_form";
    }

    @PostMapping("/product/update/{productId}")
    public String updateProduct(@PathVariable int productId){
        return "0";
    }

    @PostMapping("/product/delete/{productId}")
    public String deleteProduct(@PathVariable int productId){
        return "0";
    }



}
