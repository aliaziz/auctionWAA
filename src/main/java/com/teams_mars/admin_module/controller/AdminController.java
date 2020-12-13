//package com.teams_mars.admin_module.controller;
//
//import com.teams_mars._global_domain.User;
//import com.teams_mars.customer_module.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@SessionAttributes(value={"admin"})
//@Controller("/admin")
//public class AdminController {
//
//    @Autowired
//    UserService userService;
//    @RequestMapping("/admin")
//    public String getAdminIndex() {
//        return "admin";
//    }
//
//    @RequestMapping({"/adminHome", "/adminLogin"})
//    public String login(User user, Map<String,Object> map) {
//        map.put("user",user);
//        return "login";
//    }
//
//    @RequestMapping(value = "/admin",method = RequestMethod.POST)
//    //@Valid
//    //ModelAttribute在方法上，方法前先执行，以下是方法执行前到model或者session里找
//    //RequestAttribute
//    public String admin(@Valid @ModelAttribute("user") User admin, BindingResult result, Map<String, Object> map) {
//        if (result.hasErrors()) {
//            map.put("msg", "user name or password is not correct!");//带回到login page里
//            return "login";
//        } /*else if (admin.getUserName().equals("admin") && admin.getPassWord().equals("123")) {
//            return "admin";
//        }*/
//        return "register";
//    }
//    @RequestMapping("/show_user")
//    public String showUser(Model model){
//        List<User> allUsers = userService.findAll();
//        //还没有判断user是不是需要active, user.isProfileVerified现在没有默认值
//        List<User> allUsersVerify = new ArrayList<>();
//        for(User user: allUsers){
//            if(!user.isProfileVerified()){
//                allUsersVerify.add(user);
//            }
//        }
//        model.addAttribute("allUsersVerify",allUsersVerify);
//        return "show_user";
//    }
//    @RequestMapping(value="/activeUser/{userId}", method=RequestMethod.GET)
//    public String activeUser(@PathVariable("userId") Integer userId){
//        User user = userService.getUserById(userId);
//        if(user.getLicenseNumber() != null){
//            user.setProfileVerified(true);//需要save才能更新数据库中的，否则直接调用数据库方法
//        }
//        userService.save(user);
//        return "redirect:/show_user";
//    }
//    @RequestMapping(value = "/infinite_scroll")
//    public String infiniteScroll(){
//        return "infinite_scroll";
//    }
//
//
//}
