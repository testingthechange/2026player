package com.blockradius.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/player")
public class PlayerController {

    // Player home (Render-style album / NFT / bridges landing)
    @GetMapping("/home")
    public String playerHome() {
        // Uses src/main/resources/templates/player-home.html
        return "player-home";
    }

    // Song worksheet (bridges) - expects producerId/projectIndex/track
    @GetMapping("/song")
    public String songWorksheet(@RequestParam(name = "producerId", required = false, defaultValue = "0") Integer producerId,
                                @RequestParam(name = "projectIndex", required = false, defaultValue = "0") Integer projectIndex,
                                @RequestParam(name = "track", required = false, defaultValue = "1") Integer track,
                                Model model) {

        model.addAttribute("producerId", producerId);
        model.addAttribute("projectIndex", projectIndex);
        model.addAttribute("track", track);

        // src/main/resources/templates/song.html
        return "song";
    }

    // Dummy POST handler for saving bridges (no real persistence yet)
    @PostMapping("/song/bridges")
    public String saveSongBridges(@RequestParam Integer producerId,
                                  @RequestParam Integer projectIndex,
                                  @RequestParam Integer track) {
        // Later: persist bridges, push to Masters.
        return "redirect:/player/song?producerId=" + producerId +
                "&projectIndex=" + projectIndex +
                "&track=" + track;
    }

    // Meta / credits worksheet for a specific song
    @GetMapping("/meta")
    public String metaWorksheet(@RequestParam(name = "producerId", required = false, defaultValue = "0") Integer producerId,
                                @RequestParam(name = "projectIndex", required = false, defaultValue = "0") Integer projectIndex,
                                @RequestParam(name = "track", required = false, defaultValue = "1") Integer track,
                                Model model) {

        model.addAttribute("producerId", producerId);
        model.addAttribute("projectIndex", projectIndex);
        model.addAttribute("track", track);

        // src/main/resources/templates/meta.html
        return "meta";
    }

    // Dummy POST for meta (credits, BPM, genre, shared-with-fans)
    @PostMapping("/meta/save")
    public String saveMetaDummy(@RequestParam Integer producerId,
                                @RequestParam Integer projectIndex,
                                @RequestParam Integer track) {
        // Later: persist meta & push into Masters.
        return "redirect:/player/meta?producerId=" + producerId +
                "&projectIndex=" + projectIndex +
                "&track=" + track;
    }
}
