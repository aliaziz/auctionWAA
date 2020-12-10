package com.teams_mars.admin_module;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/admin")
    public String getAdminIndex() {
        return "admin";
    }
}
