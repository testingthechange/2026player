package com.maven.album;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlbumController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/songs")
    public String songs() {
        return "songs";
    }

    @GetMapping("/meta")
    public String meta() {
        return "meta";
    }

    @GetMapping("/shuffle")
    public String shuffle() {
        return "shuffle";
    }
}
