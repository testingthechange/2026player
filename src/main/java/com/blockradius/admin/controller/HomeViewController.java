package com.blockradius.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    // Render src/main/resources/templates/home.html
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
