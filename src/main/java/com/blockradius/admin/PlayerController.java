package com.blockradius.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class PlayerController {

    private final AdminController adminController;

    public PlayerController(AdminController adminController) {
        this.adminController = adminController;
    }

    // Main player shell used by Magic link
    @GetMapping("/player")
    public String playerHome(@RequestParam(required = false) Integer producerId,
                             @RequestParam(required = false) Integer projectIndex,
                             Model model) {

        ProducerAccount producer = null;
        ProducerProject project = null;

        List<ProducerAccount> producers = adminController.getProducers();

        if (producerId != null &&
            projectIndex != null &&
            producerId >= 0 &&
            producerId < producers.size()) {

            producer = producers.get(producerId);
            if (projectIndex >= 0 && projectIndex < producer.getProjects().size()) {
                project = producer.getProjects().get(projectIndex);
            }
        }

        model.addAttribute("producer", producer);
        model.addAttribute("project", project);
        model.addAttribute("producerId", producerId);
        model.addAttribute("projectIndex", projectIndex);

        return "magic-link";
    }

    // Player-side Meta page (project-level meta)
    @GetMapping("/player/meta")
    public String playerMeta(@RequestParam(required = false) Integer producerId,
                             @RequestParam(required = false) Integer projectIndex,
                             Model model) {

        ProducerAccount producer = null;
        ProducerProject project = null;

        List<ProducerAccount> producers = adminController.getProducers();

        if (producerId != null &&
            projectIndex != null &&
            producerId >= 0 &&
            producerId < producers.size()) {

            producer = producers.get(producerId);
            if (projectIndex >= 0 && projectIndex < producer.getProjects().size()) {
                project = producer.getProjects().get(projectIndex);
            }
        }

        model.addAttribute("producer", producer);
        model.addAttribute("project", project);
        model.addAttribute("producerId", producerId);
        model.addAttribute("projectIndex", projectIndex);

        return "meta";
    }

    // Song page (dummy version) with inline meta section
    @GetMapping("/player/song")
    public String songPage(@RequestParam Integer producerId,
                           @RequestParam Integer projectIndex,
                           @RequestParam Integer track,
                           Model model) {

        ProducerAccount producer = null;
        ProducerProject project = null;

        List<ProducerAccount> producers = adminController.getProducers();

        if (producerId != null &&
            projectIndex != null &&
            producerId >= 0 &&
            producerId < producers.size()) {

            producer = producers.get(producerId);
            if (projectIndex >= 0 && projectIndex < producer.getProjects().size()) {
                project = producer.getProjects().get(projectIndex);
            }
        }

        // Basic guard: if out of range, just show a simple page
        if (producer == null || project == null) {
            model.addAttribute("producer", null);
            model.addAttribute("project", null);
        } else {
            model.addAttribute("producer", producer);
            model.addAttribute("project", project);
        }

        model.addAttribute("producerId", producerId);
        model.addAttribute("projectIndex", projectIndex);
        model.addAttribute("track", track);

        return "song";
    }

    // Dummy POST to accept song meta for now (no real persistence yet)
    @PostMapping("/player/song/meta")
    public String saveSongMetaDummy(@RequestParam Integer producerId,
                                    @RequestParam Integer projectIndex,
                                    @RequestParam Integer track) {
        // Later: persist song meta and push into Masters.
        // For now, just bounce back to the same song page.
        return "redirect:/player/song?producerId=" + producerId +
               "&projectIndex=" + projectIndex +
               "&track=" + track;
    }
}
