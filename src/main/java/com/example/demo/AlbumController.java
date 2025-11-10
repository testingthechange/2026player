package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AlbumController {

    @GetMapping("/home")
    public String home() { 
        return "home"; 
    }

    @GetMapping("/meta")
    public String meta() { 
        return "meta"; 
    }

    @GetMapping("/songs")
    public String songs() { 
        return "songs"; 
    }

    @GetMapping("/shuffle")
    public String shuffle() { 
        return "shuffle"; 
    }

    // ✅ Dynamic mapping for all 9 songs
    @GetMapping("/song/{id}")
    public String song(@PathVariable int id, Model model) {
        model.addAttribute("songId", id);
        return "song"; // loads song.html
    }
}
