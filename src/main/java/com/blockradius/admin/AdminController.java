package com.blockradius.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String home() {
        // Root URL redirects to Producer for now
        return "redirect:/producer";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/producer")
    public String producer() {
        return "producer";
    }

    @GetMapping("/pending")
    public String pending() {
        return "pending";
    }

    @GetMapping("/meta")
    public String meta() {
        return "meta";
    }

    @GetMapping("/export")
    public String export() {
        return "export";
    }

    @GetMapping("/accounts")
    public String accounts() {
        return "accounts";
    }
}
