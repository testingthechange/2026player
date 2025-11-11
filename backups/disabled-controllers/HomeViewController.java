package com.blockradius.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    /**
     * Root ("/") after login.
     * Shows the main dashboard page.
     */
    @GetMapping("/")
    public String root() {
        // This uses src/main/resources/templates/dashboard.html
        return "dashboard";
    }

    /**
     * Extra dev check URL so we can quickly confirm things without touching "/".
     */
    @GetMapping("/dev-check")
    public String devCheck() {
        return "dashboard";
    }
}
