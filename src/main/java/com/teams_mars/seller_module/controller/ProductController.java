package com.teams_mars.seller_module.controller;

import com.teams_mars._global_domain.User;
import com.teams_mars.admin_module.domain.Category;
import com.teams_mars.admin_module.impl.CategoryService;
import com.teams_mars.biding_module.service.BidService;
import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BidService bidService;

    @Autowired
    CategoryService categoryService;

    public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

    @RequestMapping("/{productId}")
    public String viewProductDetails(@PathVariable int productId,
                                     Model model) {
        int customerId = (int) model.getAttribute("userId");
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

    @RequestMapping(value = {"/", "/home"})
    public String showProductLists(Model model) {

        List<Product> productList = productService.getAllProductsByPage(0, "startingPrice", false);
        model.addAttribute("isSeller", isSeller());
        model.addAttribute("productList", productList);
        model.addAttribute("isDesc", false);
        return "product/product_list";
    }

    @RequestMapping("/{pageNum}/{attr}/{isDesc}")
    public String showPagedProductLists(@PathVariable int pageNum,
                                        @PathVariable String attr,
                                        @PathVariable Boolean isDesc,
                                        Model model) {

        List<Product> productList = productService.getAllProductsByPage(pageNum, attr, isDesc);
        model.addAttribute("isDesc", isDesc);
        model.addAttribute("productList", productList);
        return "product/product_list";
    }

    @RequestMapping("/search")
    public String searchProducts(@RequestParam String query, Model model) {

        List<Product> productList = productService.searchProductsByName(0, query);
        model.addAttribute("productList", productList);
        return "product/product_list";
    }

    @ResponseBody
    @RequestMapping("/productReceived/{productId}")
    public boolean productReceived(@PathVariable int productId) {
        bidService.productReceived(productId);
        return true;
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Model model) throws IOException {
        if (bindingResult.hasErrors()){
            model.addAttribute("product", product);
            model.addAttribute("category", categoryService.findAll());
            return "product/product_form";
        }
        List<Product> productList = productService.getAllProducts();
        int i =1;
        for(Product prodt: productList){
            if(prodt.getName().equals(product.getName())){
                product.setName(prodt.getName()+ i);
                i++;
            }
        }
        Path path = Paths.get(uploadDirectory, product.getName());
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
        Integer userId = (Integer) model.getAttribute("userId");
        User owner = customerService.getCustomer(userId).orElseThrow();
        product.setOwner(owner);
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("msg", "Product Successfully Added!!.");
        return "redirect:/product/myProducts";
    }

    @GetMapping("/myProducts")
    public String myProducts(Model model) {
        List<Product> productList = productService.getAllSellerProducts((int) model.getAttribute("userId"));
        model.addAttribute("productList", productList);
        return "product/seller_product_list";
    }

    @GetMapping("/myProducts/{productId}")
    public String viewProduct(@PathVariable int productId, Model model) {
        System.out.print(productId);
        //query DB for product with ID id
        Product product = productService.getProduct(productId).orElseThrow();
        System.out.println(product.getCategory());
        for (Category cat : product.getCategory()) {
            System.out.println(cat.getName());
        }
        //add product to model
        model.addAttribute("product", product);
        return "product/seller_product_details";
    }

    @GetMapping("/update/{productId}")
    public String updateProductForm(@PathVariable int productId, Model model) {
        Product product = productService.getProduct(productId).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("category", categoryService.findAll());
        return "product/updateProduct_form";
    }

    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable int productId,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) throws IOException {
        if (bindingResult.hasErrors()){
            model.addAttribute("product", product);
            model.addAttribute("category", categoryService.findAll());
            return "product/updateProduct_form";
        }
        Path path = Paths.get(uploadDirectory, product.getName());
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
        Product product2 = productService.getProduct(productId).orElseThrow();
        product2.setName(product.getName());

        return "0";
    }

    @PostMapping("/delete/{product_id}")
    public String deleteProduct(@PathVariable int product_id) {
        return "0";
    }

    private boolean isSeller() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("SELLER"));
    }

    @GetMapping("/add")
    public String inputProduct(@ModelAttribute("product") Product product, Model model){
        List<Category> category = categoryService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        return "product/product_form";
    }
}
