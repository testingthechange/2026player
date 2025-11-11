package com.blockradius.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayerController {

    /**
     * Test mapping for /player/home.
     * For now, just render the existing dashboard.html template
     * so we can confirm the URL + controller are wired correctly.
     */
    @GetMapping("/player/home")
    public String playerHome() {
        return "dashboard"; // uses src/main/resources/templates/dashboard.html
    }
}
