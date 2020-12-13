package com.teams_mars.registration_login_module.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @GetMapping(value = "/access-denied")
    public String accessDenied(Model model){
        model.addAttribute("message","Access Denied............");
        return "user/accessErrorPage";
    }
    @GetMapping(value = "/wrongVerification")
    public String wrongVerification(Model model){
        model.addAttribute("message", "wrong Verification Code To change Password");
        return "user/accessErrorPage";
    }
}