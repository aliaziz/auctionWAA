package com.teams_mars._config;

import com.teams_mars.seller_module.controller.ProductController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@ControllerAdvice
@SessionAttributes("userId")
public class CoreControllerAdvice {

    @ModelAttribute("userId")
    public int getUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId != null) return (int) userId;
        return 0;
    }

    @ModelAttribute("isSeller")
    public boolean isSeller() {
        return ProductController.isSeller();
    }
}
