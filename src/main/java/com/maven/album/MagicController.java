package com.maven.album;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MagicController {

    @GetMapping("/magic/{uuid}")
    public String magicLink(@PathVariable String uuid, Model model) {
        model.addAttribute("uuid", uuid);
        return "admin/magic";
    }
}
