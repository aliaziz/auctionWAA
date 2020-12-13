//package com.teams_mars.admin_module.controller;
//
//import com.teams_mars.admin_module.domain.Category;
//import com.teams_mars.admin_module.impl.CategoryService;
//import com.teams_mars.seller_module.domain.Product;
//import com.teams_mars.seller_module.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class CategoryController {
//    @Autowired
//    CategoryService categoryService;
//    @Autowired
//    ProductService productService;
//
//    @RequestMapping("/category_show")
//    public String showCategories(Map<String,Object> map){
//        List<Category> categories = categoryService.findAll();
//        // 如果自己 try-catch 捕获了异常，没有继续往外抛时，则不会再走 @ExceptionHandler 方法捕获异常。
//        System.out.println(categories);
//        map.put("categories",categories);
//        return "category_show";
//    }
//    @RequestMapping(value = "/category_add",method = RequestMethod.GET)
//    public String toEdit(Map<String,Object> map){
//        map.put("category", new Category());
//        return "category_add";
//    }
//
//    @RequestMapping(value = "/category_save",method = RequestMethod.POST)
//    public String save(@Valid Category category, BindingResult result,Map<String,Object> map){
////以下处理需要刷新页面，如果用ajax不会刷新页面？
//        //重复只显示重复的，不会显示不合格的输入信息
//        if (result.hasErrors()) {
//            map.put("msg", "Category name or description is not correct!");
//            return "category_add";
//            //return "redirect:/login";//redirect不会有错误信息提示
//        }
//        //List<Category> allExample = categoryService.findAllExample(category);
//        boolean find = categoryService.findOne(category);
//        if(find){
//            map.put("msg", "The name of category has alreay in database!");
//            return "category_add";
//        }
//        categoryService.save(category);
//       return "redirect:/category_show";//必须用redirect
//    }
//
//    @RequestMapping(value="/category_update/{categoryid}", method=RequestMethod.GET)//超链接请求只能是get
//    public String toUpdate(@PathVariable("categoryid") Long id, Map<String, Object> map) {
//        Category categoryById = categoryService.findCategoryById(id);
//        map.put("category", categoryById);
//        return "category_update";
//    }
//
//    @RequestMapping(value="/category_update", method=RequestMethod.POST)//超链接请求只能是get
//    public String Update(Category category) {
//        categoryService.save(category);
//        return "redirect:/category_show";
//    }
//    //删除
//    @RequestMapping(value="/category_delete/{categoryid}", method=RequestMethod.GET)//超链接请求只能是get
//    public String delete(@PathVariable("categoryid") Long id, Model modle) {
//        Category categoryById = categoryService.findCategoryById(id);
//        if(!categoryById.getProductList().isEmpty()){
//            modle.addAttribute("msg", "The category is still used by some Product!");
//            List<Category> allCategory = categoryService.findAll();
//            modle.addAttribute("categories", allCategory);
//            return "category_show";
//        }
//        categoryService.deleteCategoryById(id);
//        return "redirect:/category_show";
//    }
//    @RequestMapping(value = "/category_showProductList/{categoryId}",method = RequestMethod.GET)
//    public String showProductList(@PathVariable ("categoryId") Long categoryId,Model model){
//
//       /* List<Product> allProducts = productService.getAllProducts();
//        List<Product> allProductsByCotegoryId = new ArrayList<>();
//        for(Product p : allProducts){
//            List<Category> category = p.getCategory();
//            for(Category c : category){
//                Long category_id = c.getCategory_id();
//                if(category_id.longValue() == Long.valueOf(categoryId)){
//                    allProductsByCotegoryId.add(p);
//                }
//
//            }
//        }*/
//        List<Product> productsByCategoryId2 = categoryService.findProductsByCategoryId2(categoryId);
//        model.addAttribute("allProductsByCotegoryId",productsByCategoryId2);
//        return "category_showProductList";
//    }
//
//    @RequestMapping(value = "/category_product_infinitescroll/{categoryid}",method = RequestMethod.GET)
//    public String showProductInfinitescroll(@PathVariable ("categoryId") Long categoryId,Model model){
//        List<Product> productsByCategoryId2 = categoryService.findProductsByCategoryId2(categoryId);
//        model.addAttribute("allProductsByCotegoryId",productsByCategoryId2);
//        return "infinite_scroll.html";
//    }
//    @ResponseBody
//    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
//    public List<Product> getProducts(@RequestParam("categoryId") Long categoryId) {
//        List<Product> productsByCategoryId2 = categoryService.findProductsByCategoryId2(categoryId);
//        return productsByCategoryId2;
//    }
//
//}
