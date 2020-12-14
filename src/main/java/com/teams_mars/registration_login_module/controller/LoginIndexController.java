package com.teams_mars.registration_login_module.controller;


import com.teams_mars._global_domain.User;
import com.teams_mars.customer_module.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginIndexController {

    UserService userService;
    public LoginIndexController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email=auth.getName();
        User user = userService.findUserByEmail(email);

        if (null != user && (!user.isVerificationCodeVerified())) {
            User user1 = userService.findUserByEmail(email);
            model.addAttribute("unverifiedUser",user1);
            return "user/verificationPage";
        }
        return "user/login";
    }


    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "user/login";
    }

}