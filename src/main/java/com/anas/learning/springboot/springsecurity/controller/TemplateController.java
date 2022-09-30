package com.anas.learning.springboot.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/09/2022
 */
@Controller
@RequestMapping("/")
public class TemplateController {
    @GetMapping("login")
    public String getLoginPage() {
        return "login"; // must be the same name as the template file but without the extension (login.html)
    }

    @GetMapping("courses")
    public String getCoursesPage() {
        return "courses";
    }

    @GetMapping("logout")
    public String getLogoutPage() {
        return "logout";
    }
}

